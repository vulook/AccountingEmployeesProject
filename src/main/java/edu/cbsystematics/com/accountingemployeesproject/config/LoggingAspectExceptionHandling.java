package edu.cbsystematics.com.accountingemployeesproject.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspectExceptionHandling {

    private static final Logger logger = LogManager.getLogger(LoggingAspectExceptionHandling.class);

    @AfterThrowing(pointcut = "execution(* edu.cbsystematics.com.accountingemployeesproject.service.*.*(..))", throwing = "throwable")
    public void handleException(JoinPoint joinPoint, Throwable throwable) {

        // Extracting method details from the intercepted join point
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        // Logging the exception detail
        logger.error("Exception caught: {} in {}.{}", throwable.getMessage(), className, methodName, throwable);
    }

}