package pet.storage.storage.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* pet.storage.storage.service.*.*(..)) || " +
            "execution(* pet.storage.storage.controller.*.*(..))")
    public void applicationMethodsPointcut() {
    }

    @Around("applicationMethodsPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = signature.getName();
        String methodFullName = className.concat(".").concat(methodName).concat("()");

        Object[] args = joinPoint.getArgs();
        String argsString = Arrays.toString(args);

        logger.info("--> Entering: {} with args: {}", methodFullName, argsString);

        Object result = null;
        try {
            result = joinPoint.proceed();

            String resultToLog = (result instanceof ResponseEntity) ?
                    "ResponseEntity with status: " + ((ResponseEntity<?>) result).getStatusCode() :
                    String.valueOf(result);
            logger.info("<-- Exiting successfully: {} with result: {}", methodFullName, resultToLog);
            return result;
        } catch (Throwable ex) {

            logger.error("<-- Exiting with exception: {} Exception: {}", methodFullName, ex.getClass().getSimpleName(), ex);
            throw ex;
        }
    }
}
