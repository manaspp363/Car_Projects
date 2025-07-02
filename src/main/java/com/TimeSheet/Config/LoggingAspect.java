/*
package com.TimeSheet.Config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // Logs all methods inside controller package
    @Before("execution(* com.yourcompany.timesheet.controller..*(..))")
    public void logBeforeController(JoinPoint joinPoint) {
        logger.info("Controller => {} called with args: {}", joinPoint.getSignature(), Arrays.toString(joinPoint.getArgs()));
    }

    // Logs all methods inside service package
    @Before("execution(* com.yourcompany.timesheet.service..*(..))")
    public void logBeforeService(JoinPoint joinPoint) {
        logger.info("Service => {} called with args: {}", joinPoint.getSignature(), Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "execution(* com.yourcompany.timesheet..*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Method {} returned with result: {}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(pointcut = "execution(* com.yourcompany.timesheet..*(..))", throwing = "ex")
    public void logAfterException(JoinPoint joinPoint, Throwable ex) {
        logger.error("Exception in method {} => {}", joinPoint.getSignature(), ex.getMessage());
    }
}
*/
