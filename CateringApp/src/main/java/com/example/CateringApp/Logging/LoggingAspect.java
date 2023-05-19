package com.example.CateringApp.Logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger= LoggerFactory.getLogger(LoggingAspect.class);
    @Around("execution(* com.example.CateringApp.*.*.*(..))")
    public Object logger(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("In the start of the method:"+pjp.getSignature());
        Object object=pjp.proceed();
        logger.info("End of the method with response:"+object);
        return object;
    }

}
