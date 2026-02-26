package com.sujan.spring_boot_rest.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect /// Aspect
public class LoggingAspect {

    private static  final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    ///These are Advice
    @Before("execution(* com.sujan.spring_boot_rest.service.JobService.getJob(..))") ///  Inside Parenthesis is a Point cut
    public void function(JoinPoint jp){  /// Joint Point
        LOGGER.info("Method name " + jp.getSignature().getName());
    }

    @After("execution(* com.sujan.spring_boot_rest.service.JobService.getJob(..))")
    public void functionAfter(JoinPoint jp){
        LOGGER.info("Method name  after called " + jp.getSignature().getName());
    }

    /**
     * This gonna called when there is an error it call after "Before" then call "After"
     * @param jp
     */
    @AfterThrowing("execution(* com.sujan.spring_boot_rest.service.JobService.*(..))")
    public void functionAfterThrowing(JoinPoint jp){
        LOGGER.info("Method has some issues " + jp.getSignature().getName());
    }

    /**
     * When method return successfully it's gonna call
     * @param jp
     */
    @AfterReturning("execution(* com.sujan.spring_boot_rest.service.JobService.*(..))")
    public void functionAfterReturning(JoinPoint jp){
        LOGGER.info("Method return successfully " + jp.getSignature().getName());
    }


}