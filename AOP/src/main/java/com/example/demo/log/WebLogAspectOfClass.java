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
public class WebLogAspectOfClass {

    private static final Logger logger = LoggerFactory.getLogger(WebLogAspectOfClass.class);

    private ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();
    //within()函数定义的连接点是针对目标类而言的,里面的值可以是 自定义的注解类，也可以包名前缀
    @Pointcut("@within( com.example.demo.log.ControllerWebLogOfClass )")
    public void webLog() {
    }


    /**
     * 实现切面逻辑（续）
     * 接收请求，并记录数据
     *
     * @param joinPoint
     * @param ControllerWebLogOfClass
     */

    /**
    一般值也是 execution表达式  这里是： webLog() 等价于 execution(public * com.example.demo..*.*(..))，
    并且 有标记了 ControllerWebLogOfClass 注解的才算 匹配成功，才执行这个,如果去除 “@annotation(ControllerWebLogOfClass)”，
     那么这个 doBefore函数 的  ControllerWebLogOfClass参数就无法得到值，直接报错了
    */
    @Before(value = "webLog() ")
    public void doBefore(JoinPoint joinPoint) {
        logger.info("开始执行 ------@Before---------------- ");
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

        threadLocal.set(threadInfo);
        logger.info("执行 -----------@Before----------------\n\n ");
    }


    /**
     * 实现切面逻辑
     * <p>
     * 执行成功后处理
     *
     *
     * @param ret
     * @throws Throwable
     */
    @AfterReturning(value = "webLog() )", returning = "ret")
    public void doAfterReturning( Object ret) throws Throwable {
        logger.info("开始执行 ---@AfterReturning ---------- ");
        Map<String, Object> threadInfo = threadLocal.get();
        threadInfo.put("result", ret);

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

