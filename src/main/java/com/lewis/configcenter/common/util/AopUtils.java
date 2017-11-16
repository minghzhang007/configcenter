package com.lewis.configcenter.common.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

public class AopUtils {

    private AopUtils() {
    }

    public static Class getReturnTypeOfJoinPoint(ProceedingJoinPoint joinPoint) {
        Class returnType = null;
        Signature signature = joinPoint.getSignature();
        if (signature != null && signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) signature;
            returnType = methodSignature.getReturnType();
        }
        return returnType;
    }
}
