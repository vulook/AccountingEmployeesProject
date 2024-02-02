package edu.cbsystematics.com.accountingemployeesproject.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspectPaging {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspectPaging.class);

    @Pointcut("execution(public * edu.cbsystematics.com.accountingemployeesproject.controller.PagingController.*(..))")
    public void publicMethods() {}

    @Around("publicMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        logger.info("===> Method [{}] executed in {} ms", joinPoint.getSignature(), executionTime);
        return result;
    }

}