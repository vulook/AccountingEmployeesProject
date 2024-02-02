package edu.cbsystematics.com.accountingemployeesproject.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspectAllMethods {
    private static final Logger logger = LogManager.getLogger(LoggingAspectAllMethods.class);

    @Pointcut("execution(public * edu.cbsystematics.com.accountingemployeesproject..*.*(..))")
    public void publicMethods() {
    }

    @Around("publicMethods()")
    public Object logMethodEntryAndExit(ProceedingJoinPoint joinPoint) throws Throwable {

        // Log method entry and arguments
        Object[] args = joinPoint.getArgs();

        if (logger.isDebugEnabled()) {
            logger.debug("===> Entering method {} with arguments: {}", joinPoint.getSignature().getName(), Arrays.toString(args));
        }

        // Obtain the package name of the target class
        Class<?> targetClass = joinPoint.getTarget().getClass();
        String packageName = extractLastPackageName(targetClass.getPackage().getName());

        // // Initialize a stopwatch to measure method execution time
        final StopWatch stopWatch = new StopWatch();

        // Measure method execution time
        stopWatch.start();
        Object result = joinPoint.proceed();
        stopWatch.stop();

        //Log method execution time
        long executionTime = stopWatch.getTotalTimeMillis();

        // Method name
        String methodName = joinPoint.getSignature().getName();

        // Log method exit and return value
        logger.debug("===> Package: [{}], Exiting method: [{}] with result {} in ms: {}", packageName, methodName, result, executionTime);
        return result;
    }

    // Method, which takes the full package name and returns only the last part of it
    private String extractLastPackageName(String fullPackageName) {
        // Split
        String[] parts = fullPackageName.split("\\.");
        return (parts.length > 0) ? parts[parts.length - 1] : fullPackageName;
    }


}