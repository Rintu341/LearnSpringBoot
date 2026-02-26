package com.sujan.spring_boot_rest.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ValidatingAspect {

    private static  final Logger LOGGER = LoggerFactory.getLogger(ValidatingAspect.class);


    @Around("execution(* com.sujan.spring_boot_rest.service.JobService.getJob(..)) && args(postId)")
    public Object validatingNegative(ProceedingJoinPoint jp, int postId) throws Throwable {


        if(postId < 0){
            LOGGER.info("Negative value update it");
            postId = -postId;
        }

        Object obj = jp.proceed(new Object[]{postId});


        return obj;
    }

}
