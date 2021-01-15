理解和总结：
1、为什么需要AOP？
答：最初是 想要在 执行a函数时，每次去自动执行某个函数就好了。
    实际场景 就是 想要执行类中的每个方法时，都记录到日志中。而要是人工去写，这可要费大力气了，并且不好维护（难道每次更改都要去修改和一个一个的函数去替换，查看。有点lombok的味道了）。

2、如何使用AOP？
步骤1： 新建一个类（该类是要在执行a函数时，自动要执行的逻辑。即要植入的逻辑），这个类和普通的类不一样，需要告知spring，我是一个“切面”类。
        在该类中，开始编写 “切入点 @Pointcut” 即：指明 我要在那些函数、类、包下通通执行前要使用我的这个方法，其中包含了：
        @Before: 前置通知, 在方法执行之前执行
        @After: 后置通知, 在方法执行之后执行 。
        @AfterRunning: 返回通知, 在方法返回结果之后执行
        @AfterThrowing: 异常通知, 在方法抛出异常之后
        @Around: 环绕通知, 围绕着方法执行。
        
        切入点@Pointcut的匹配格式：（像正则表达式，匹配成功则执行我的这个方法，匹配不成功就不会去执行），所以匹配的写法至关重要的：
         execution：一般是匹配某个包下的 某些具备某些"特征"函数就算匹配成功了：如：   @Pointcut("execution(public * com.example.demo..*.*(..))") 匹配 修饰符为public 返回值为空的 且在com.example.demo下的任何函数
         within：  //within()函数定义的连接点是针对目标类而言的,里面的值可以是 自定义的注解类，也可以包名前缀 如：  @Pointcut("@within( com.example.demo.log.ControllerWebLogOfClass )")  ，其中，ControllerWebLogOfClass是自定义的注解类，表示只要这个自定义类添加到类名头上就会被”匹配成功“的注解
步骤2： 自定义注解。
        我们可以规定 该注解 是作用在“类”上，还是作用在“方法”上。@Target(ElementType.TYPE)可以控制，TYPE表示作用在类、接口上。METHON代表左右在方法上，还有许多值可以查
        
步骤3： 在使用的地方加上自定义的注解，（作用是标识，可以作为 切入点的一个判断条件）




注意事项：
1、要在类名前加：@Aspect        @Component
2、切入点切入方法的时候是可以传参的，切入类的时候能够传参数吗？好像不能


AOP其他人的观点理解：
    观点1：
    springboot：AOP的理解 把每个类对应的整个流程（从pojo类到数据处理层再到服务到control层）比作一个竖向过程，那么许许多多如此的类 就会形成无数条的竖线，那么AOP就相当于一横截面，形成了一个横向的大阀门，所有的这些竖向过程在某一个阶段就会遇到 该阀门，阀门根据需要就可以设置一个统一行为，这样所有经过阀门的这些流程就都要执行，比如日志，当我们将切面 放在control层时（比如以方法横截），他们就都会自动根据阀门的规范来去执行（比如在执行方法前做出指定行为，在方法中访问参数（可以自己构造实现方法） ，在方法后再执行规范，然后就成功经过了这个横截面，AOP不必再去关心我是否要分别为每个过程都要分别配备，因为在定义了横切面后，这个 横切面覆盖了所有竖向过程。
    
    观点2：
    Aspect Oriented Programming：面向切面编程
OOP（面向对象编程）针对业务处理过程的实体及其属性和行为进行抽象封装，以获得更加清晰高效的逻辑单元划分。 
而AOP则是针对业务处理过程中的切面进行提取，它所面对的是处理过程中的某个步骤或阶段，以获得逻辑过程中各部分之间低耦合性的隔离效果。这两种设计思想在目标上有着本质的差异。AOP的出现是对OOP的一个很好的补充。面向对象编程中主要支持的思想就是面向接口编程，层与层之间不依赖具体，只依赖抽象。面向对象是纵向结构的，它能够使系统的逻辑单元更加清晰。而面向切面是横切结构的，它针对业务逻辑层的切面进行提取。比如面向切面完成一个功能，这一功能却在每个模块中都有涉及，他就像刀切豆腐一样切进系统一样，能够完成对系统完成同一控制。如果目标类是面向接口编程，则通JDK动态过代理实现。如果目标类没有实现接口，则通过CJLIB动态代理实现，在测试时候可以Debug观察Spring最终交给我们的目标对象的地址，JDK代理与CCJLIB代理在地址上会有明显区别。

    观点3：
    面向切面编程(Aspect-oriented Programming，俗称AOP)提供了一种面向对象编程(Object-oriented Programming，俗称OOP)的补充，面向对象编程最核心的单元是类(class)，然而面向切面编程最核心的单元是切面(Aspects)。与面向对象的顺序流程不同，AOP采用的是横向切面的方式，注入与主业务流程无关的功能，例如事务管理和日志管理。
    纵向抽取：
    把相同的代码抽取出来成为公共的方法，降低耦合性。这种提取代码的方式是纵向抽取，纵向抽取的代码之间的关联关系非常密切。
    横向抽取：
    那么使用 AOP 之后，你就可以横向抽取重复代码，什么叫横向抽取呢？横向抽取也是代码提取的一种方式，不过这种方式不会修改主要业务逻辑代码，只是在此基础上添加一些与主要的业务逻辑无关的功能，AOP 采取横向抽取机制，补充了传统纵向继承体系(OOP)无法解决的重复性 代码优化(性能监视、事务管理、安全检查、缓存)，将业务逻辑和系统处理的代码(关闭连接、事务管理、操作日志记录)解耦。
    切入点表达式的理解：
    再切面中使用@Pointcut定义的一个规则表达式，用来定义连接点的拦截规则。当匹配到的方法执行时，就会按相关顺序执行相关的一些通知，来增强应用到目标对象。
    通知分类：
    •	前置通知(Before Advice): 在目标方法被调用前调用通知功能；相关的类org.springframework.aop.MethodBeforeAdvice
    •	后置通知(After Advice): 在目标方法被调用之后调用通知功能；相关的类org.springframework.aop.AfterReturningAdvice
    •	返回通知(After-returning): 在目标方法成功执行之后调用通知功能；
    •	异常通知(After-throwing): 在目标方法抛出异常之后调用通知功能；相关的类org.springframework.aop.ThrowsAdvice
    •	环绕通知(Around): 把整个目标方法包裹起来，在被调用前和调用之后分别调用通知功能相关的类org.aopalliance.intercept.MethodInterceptor
    注意事项：
        Spring Boot 的版本号不一样，通知的执行顺序也不一样。经过自己实验后发现如下：
        <version>2.4.1</version>
        2.4.1 AOP执行顺序：@Around->@Before->目标方法->@AfterReturning->@Around
        <version>2.1.9.RELEASE</version>
        2.1.9.RELEASE AOP执行顺序：@Around->@Before->目标方法->@Around->@AfterReturning

    观点4:
    一. 横切面中切点表达式的使用
    1.1 由于Spring切面粒度最小是达到方法级别， 而execution表达式可以用于明确指定方法返回类型，类名，方法名和参数名等与方法相关的部件， 并且在Spring中，大部分需要使用AOP的业务场景也只需要达到方法级别即可， 因而execution表达式的使用是最为广泛的。如下是execution表达式的语法：
    execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern) throws-pattern?)
    这里问号表示当前项可以有也可以没有，其中各项的语义如下：
    •	modifiers-pattern：方法的可见性，如public，protected；
    •	ret-type-pattern：方法的返回值类型，如int，void等；
    •	declaring-type-pattern：方法所在类的全路径名，如com.zjrcu.demo.controller；
    •	name-pattern：方法名类型，如TestController()；
    •	param-pattern：方法的参数类型，如java.lang.String；
    •	throws-pattern：方法抛出的异常类型，如java.lang.Exception； 如下是一个使用execution表达式的例子：
    execution(public * com.zjrcu.demo.controller.*.*(..))
    上述切点表达式将会匹配使用public修饰，返回值为任意类型，并且是com.zjrcu.demo.controller包中，任意控制器类中的任意方法，方法可以有任意个参数。 上述示例中我们使用了..通配符和*通配符，关于通配符的类型，主要有两种：
    •	*通配符，该通配符主要用于匹配单个单词，或者是以某个词为前缀或后缀的单词。
    如下示例表示返回值为任意类型，在com.zjrcu.demo.controller.TestController类中，并且参数个数为零的方法：
    execution(* com.zjrcu.demo.controller.TestController.*())
    下述示例表示返回值为任意类型，在com.zjrcu.demo.controller包中，以Test为前缀的类，并且是类中参数个数为零方法：
    execution(* com.zjrcu.demo.controller.Test*.*())
    •	..通配符，该通配符表示0个或多个项，主要用于declaring-type-pattern和param-pattern中， 如果用于declaring-type-pattern中，则表示匹配当前包及其子包，如果用于param-pattern中，则表示匹配0个或多个参数。 如下示例表示匹配返回值为任意类型，并且是com.zjrcu.demo.controller包下的任意类的任意方法， 而且该方法可以有任意参数：
    execution(* com.zjrcu.demo.controller.*.*(..))
    如下示例第一个..先表示com.zjrcu.demo包和其任意子包，第二个..表示任意个数的参数的示例，需要注意，表示参数的时候可以在括号中事先指定某些类型的参数，而其余的参数则由..进行匹配：
    execution(* com.zjrcu.demo..*.*(java.lang.String,..))
    二、使用不同的@Order测试执行顺序
    •	下面使用 @Order(10) 执行的代码片段
    @Aspect
    @Component
    @Order(10)
    public class TestOrderAnnotationAspect {

        private final static Logger logger = LoggerFactory.getLogger(TestOrderAnnotationAspect.class);

        /**
         * 横切点
         */
        @Pointcut("execution(public * com.zjrcu.demo.controller.*.*(..))")
        public void orderLog(){}

        /**
         * 横切点方法执行之前执行
         * @param joinPoint
         */
        @Before(value = "orderLog()")
        public void doBefore(JoinPoint joinPoint){
            logger.info("Order(10) doBefore 方法执行 ...");
        }
    }
    •	下面使用 @Order(100) 执行的代码片段
    /**
     * web 访问切面
     */
    @Aspect
    @Component
    @Order(100)
    public class WebLogAspect {

        private final static Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

        private ThreadLocal<Map<String, Object>> threadLocal  = new ThreadLocal<>();


        /**
         * 横切点
         */
        @Pointcut("execution(* com.zjrcu.demo..*.*(..))")
        public void webLog(){}

        @Before(value = "webLog()&& @annotation(controllerWebLog)")
        public void doBefore(JoinPoint joinPoint,ControllerWebLog controllerWebLog) {

            logger.info("Order(100) doBefore 方法执行 ...");



