package com.prulloac.territoriesbase.utils.aop;

import org.springframework.stereotype.Component;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Prulloac
 */
@Component
@Aspect
@Slf4j
public class LogExecutionTimeAspect {

	@Around("@annotation(LogExecutionTime)")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();
		Object proceed = joinPoint.proceed();
		long end = System.currentTimeMillis();
		Signature methodSignature = joinPoint.getSignature();
		String callerClassName = methodSignature.getDeclaringTypeName();
		String callerMethodName = methodSignature.getName();
		log.info("Execution time of {}.{}: {}ms", callerClassName, callerMethodName, end - start);
		return proceed;
	}

}
