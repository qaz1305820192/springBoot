package com.example.demo.log;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 实现切面逻辑
 */

@Aspect     //@Aspect:切面的意思。作用是把当前类标识为一个切面供容器读取  告诉spring 说 这个类 是 执行其他方法要 “加强的内容”
@Component
@Order(100) //定义Spring IOC容器中Bean的执行顺序的优先级，而不是定义Bean的加载顺序
public class WebLogAspectOfMethod {

    private static final Logger logger = LoggerFactory.getLogger(WebLogAspectOfMethod.class);

    private ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

    /**
     * execution：用于匹配方法执行的连接点
     *
     * 横切点：     * pointCut 指定 通知到那个方法，说明在哪里干，指定了当前切面逻辑所要包裹的业务模块的范围大小；
     *  execution表达式可以用于明确指定方法返回类型，类名，方法名和参数名等与方法相关的部件，
     * 并且在Spring中，大部分需要使用AOP的业务场景也只需要达到方法级别即可。
     *
     *
     *  如下是一个使用execution表达式的例子：
     * execution(public * com.spring.service.BusinessObject.businessService(java.lang.String,..))
     * 上述切点表达式将会匹配使用public修饰，返回值为任意类型，并且是com.spring.BusinessObject类中名称为businessService的方法，方法可以有多个参数，但是第一个参数必须是java.lang.String类型的方法。上述示例中我们使用了..通配符，关于通配符的类型，主要有两种：
     *
     * *通配符，该通配符主要用于匹配单个单词，或者是以某个词为前缀或后缀的单词。
     **通配符，该通配符主要用于匹配单个单词，或者是以某个词为前缀或后缀的单词。
     *        如下示例表示返回值为任意类型，在com.spring.service.BusinessObject类中，并且参数个数为零的方法：
     * execution(* com.spring.service.BusinessObject.*())
     *        下述示例表示返回值为任意类型，在com.spring.service包中，以Business为前缀的类，并且是类中参数个数为零方法：
     *
     * execution(* com.spring.service.Business*.*())
     * ..通配符，该通配符表示0个或多个项，主要用于declaring-type-pattern和param-pattern中，如果用于declaring-type-pattern中，则表示匹配当前包及其子包，如果用于param-pattern中，则表示匹配0个或多个参数。
     *        如下示例表示匹配返回值为任意类型，并且是com.spring.service包及其子包下的任意类的名称为businessService的方法，而且该方法不能有任何参数：
     *
     * execution(* com.spring.service..*.businessService())
     *
     * @Pointcut("execution(public * com.example.demo..*.*(..))") 本次运行的 意思是 切入 那些 修饰符为public，返回值任意，在包com。example.demo下的任意函数
     */
    @Pointcut("execution(public * com.example.demo..*.*(..))")
    public void webLog() {
    }


    /**
     * 实现切面逻辑（续）
     * 接收请求，并记录数据
     *
     * @param joinPoint
     * @param ControllerWebLogOfMethod
     */

    /**
    一般值也是 execution表达式  这里是： webLog() 等价于 execution(public * com.example.demo..*.*(..))，
    并且 有标记了 ControllerWebLogOfMethod 注解的才算 匹配成功，才执行这个,如果去除 “@annotation(ControllerWebLogOfMethod)”，
     那么这个 doBefore函数 的  ControllerWebLogOfMethod参数就无法得到值，直接报错了
    */
    @Before(value = "webLog() && @annotation(ControllerWebLogOfMethod)")
    public void doBefore(JoinPoint joinPoint, ControllerWebLogOfMethod ControllerWebLogOfMethod) {
        logger.info("开始执行 @@Before---------------- ");
        // 接收到请求
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        // 记录请求内容，threadInfo存储所有内容
        Map<String, Object> threadInfo = new HashMap<>();
        logger.info("URL : " + request.getRequestURL());
        threadInfo.put("url", request.getRequestURL());
        logger.info("URI : " + request.getRequestURI());
        threadInfo.put("uri", request.getRequestURI());
        logger.info("HTTP_METHOD : " + request.getMethod());
        threadInfo.put("httpMethod", request.getMethod());
        logger.info("REMOTE_ADDR : " + request.getRemoteAddr());
        threadInfo.put("ip", request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName());
        threadInfo.put("classMethod",
                joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
        threadInfo.put("args", Arrays.toString(joinPoint.getArgs()));
        logger.info("USER_AGENT" + request.getHeader("User-Agent"));
        threadInfo.put("userAgent", request.getHeader("User-Agent"));
        logger.info("执行方法：" + ControllerWebLogOfMethod.name());
        threadInfo.put("methodName", ControllerWebLogOfMethod.name());
        threadLocal.set(threadInfo);
        logger.info("执行 -----------@Before---------------- ");
    }


    /**
     * 实现切面逻辑
     * <p>
     * 执行成功后处理
     *
     * @param ControllerWebLogOfMethod
     * @param ret
     * @throws Throwable
     */
    @AfterReturning(value = "webLog()&& @annotation(ControllerWebLogOfMethod)", returning = "ret")
    public void doAfterReturning(ControllerWebLogOfMethod ControllerWebLogOfMethod, Object ret) throws Throwable {
        logger.info("开始执行 @AfterReturning ---------- ");
        Map<String, Object> threadInfo = threadLocal.get();
        threadInfo.put("result", ret);
        if (ControllerWebLogOfMethod.intoDb()) {
            //插入数据库操作
            //insertResult(threadInfo);
        }
        // 处理完请求，返回内容
        logger.info("RESPONSE : " + ret);
        logger.info("执行结束 -------------@AfterReturning ---------- ");
    }


    /**
     * 实现切面逻辑
     * <p>
     * 异常处理
     *
     * @param throwable
     */
    @AfterThrowing(value = "webLog()", throwing = "throwable")
    public void doAfterThrowing(Throwable throwable) {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();

        ServletRequestAttributes sra = (ServletRequestAttributes) ra;

        HttpServletRequest request = sra.getRequest();
        // 异常信息
        logger.error("{}接口调用异常，异常信息{}", request.getRequestURI(), throwable);
    }


}

