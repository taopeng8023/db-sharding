package com.tp.sharding.sharding.aspectj;

import com.tp.sharding.sharding.annotation.HintMasterRoute;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.hint.HintManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.Method;

/**
 * @author taopeng
 * @version 1.0
 * @Description
 * @date 2024/07/02 18:00:13
 */
@Slf4j
public class HintMasterRouteAspect extends AbstractShardingAspectSupport {
    public HintMasterRouteAspect() {
    }

    @Pointcut("@annotation(com.tp.sharding.sharding.annotation.HintMasterRoute)")
    public void masterRouteAnnotationPointcut() {
    }

    @Around("masterRouteAnnotationPointcut()")
    public Object invokeMasterRoute(ProceedingJoinPoint pjp) throws Throwable {
        Method originMethod = this.resolveMethod(pjp);
        HintMasterRoute annotation = (HintMasterRoute)originMethod.getAnnotation(HintMasterRoute.class);
        if (annotation == null) {
            throw new IllegalStateException("Wrong state for HintMasterRoute annotation");
        } else if (!annotation.isMasterOnly()) {
            return pjp.proceed();
        } else {
            log.info("hint route master datasource.");
            HintManager.clear();
            HintManager hintManager = HintManager.getInstance();
            Throwable var5 = null;

            Object var6;
            try {
                hintManager.setMasterRouteOnly();
                var6 = pjp.proceed();
            } catch (Throwable var15) {
                var5 = var15;
                throw var15;
            } finally {
                if (hintManager != null) {
                    if (var5 != null) {
                        try {
                            hintManager.close();
                        } catch (Throwable var14) {
                            var5.addSuppressed(var14);
                        }
                    } else {
                        hintManager.close();
                    }
                }

            }

            return var6;
        }
    }
}
