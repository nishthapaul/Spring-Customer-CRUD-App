package springdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	
	// setup Pointcut declarations
	@Pointcut("execution(* springdemo.controller.*.*(..))")
	private void forControllerPack() {}
	
	@Pointcut("execution(* springdemo.dao.*.*(..))")
	private void forDaoPack() {}
	
	@Pointcut("execution(* springdemo.service.*.*(..))")
	private void forServicePack() {}
	
	@Pointcut("forControllerPack() || forDaoPack() || forServicePack()")
	private void forAppFlow() {}
	
	// Add @Before Advice
	@Before("forAppFlow()")
	public void before(JoinPoint joinPoint) {
		System.out.println("=====>> in @Before calling method::: " + joinPoint.getSignature().toShortString());
		
		Object[] args = joinPoint.getArgs();
		for(Object arg : args) {
			System.out.println("====>> argument::: " + arg);
		}
	}
	
	// Add @AfterReturning Advice
		@AfterReturning(pointcut = "forAppFlow()", returning = "result")
		public void afterReturning(JoinPoint joinPoint, Object result) {
			System.out.println("=====>> in @AfterReturning calling method::: " + joinPoint.getSignature().toShortString());
			
			System.out.println("====>> result::: " + result);
		}
	
}
