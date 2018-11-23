package com.lmx.blog.config;

import com.alibaba.druid.support.json.JSONUtils;
import com.lmx.blog.common.Response;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LogApsect {

    private static final Logger logger = LoggerFactory.getLogger(LogApsect.class);

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    // 第一个*代表返回类型不限
    // 第二个*代表所有类
    // 第三个*代表所有方法
    // (..) 代表参数不限
    @Pointcut("execution(public * com.lmx.blog.controller.*.*(..))")
    @Order(2)
    public void pointCut(){};

    @Pointcut("@annotation(com.lmx.blog.annotation.RedisCache)")
    @Order(1) // Order 代表优先级，数字越小优先级越高
    public void annoationPoint(){};

    @Before(value = "annoationPoint() || pointCut()")
    public void before(JoinPoint joinPoint){
        System.out.println("方法执行前执行......before");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        logger.info("<=====================================================");
        logger.info("请求来源： =》" + request.getRemoteAddr());
        logger.info("请求URL：" + request.getRequestURL().toString());
        logger.info("请求方式：" + request.getMethod());
        logger.info("响应方法：" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("请求参数：" + Arrays.toString(joinPoint.getArgs()));
        logger.info("------------------------------------------------------");
        startTime.set(System.currentTimeMillis());
    }

    // 定义需要匹配的切点表达式，同时需要匹配参数
    @Around("pointCut() && args(arg)")
    public Response around(ProceedingJoinPoint pjp,String arg) throws Throwable{
        System.out.println("name:" + arg);
        System.out.println("方法环绕start...around");
        String result = null;
        Object object = pjp.proceed();
        try{
            result = object.toString() + "aop String";
            System.out.println(result);
        }catch (Throwable e){
            e.printStackTrace();
        }
        System.out.println("方法环绕end...around");
        return (Response) object;
    }

    @After("within(com.lmx.blog.controller.*Controller)")
    public void after(){
        System.out.println("方法之后执行...after.");
    }

    @AfterReturning(pointcut="pointCut()",returning = "rst")
    public void afterRunning(Response rst){
        if(startTime.get() == null){
            startTime.set(System.currentTimeMillis());
        }
        System.out.println("方法执行完执行...afterRunning");
        logger.info("耗时（毫秒）：" +  (System.currentTimeMillis() - startTime.get()));
        logger.info("返回数据：{}", rst);
        logger.info("==========================================>");
    }

    @AfterThrowing("within(com.lmx.blog.controller.*Controller)")
    public void afterThrowing(){
        System.out.println("异常出现之后...afterThrowing");
    }


}
