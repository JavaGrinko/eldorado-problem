package javagrinko.eldorado.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.StampedLock;

@Aspect
@Component
public class ThreadSafeAspect {

    private StampedLock stampedLock = new StampedLock();

    @Pointcut("execution(* javagrinko.eldorado.model.Statistics.get*())")
    public void readLockPointcut() {
    }

    @Pointcut("execution(* javagrinko.eldorado.model.Statistics.set*(*))")
    public void writeLockPointcut() {
    }

    @Around("readLockPointcut()")
    public Object readLock(ProceedingJoinPoint joinPoint) throws Throwable {
        long stamp = stampedLock.readLock();
        Object o;
        try {
            o = joinPoint.proceed();
        } finally {
            stampedLock.unlockRead(stamp);
        }
        return o;
    }

    @Around("writeLockPointcut()")
    public Object writeLock(ProceedingJoinPoint joinPoint) throws Throwable {
        long stamp = stampedLock.writeLock();
        Object o;
        try {
            o = joinPoint.proceed();
        } finally {
            stampedLock.unlockWrite(stamp);
        }
        return o;
    }
}
