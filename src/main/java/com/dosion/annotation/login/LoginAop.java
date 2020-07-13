package com.dosion.annotation.login;

import com.dosion.enums.ResultStatusEnum;
import com.dosion.exception.CustomException;
import com.dosion.model.system.entity.User;
import com.dosion.model.system.service.SessionService;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 环绕通知
 * 登录拦截
 *
 * @author 陈登文
 */
@Aspect
@Component
@AllArgsConstructor
public class LoginAop {
    private final SessionService<User> sessionService;


    @Around("@annotation(com.dosion.annotation.login.Login)")
    public Object beforeSwitchDS(JoinPoint point) throws Throwable {

        //获得当前访问的class
        Class<?> className = point.getTarget().getClass();

        //获得访问的方法名
        String methodName = point.getSignature().getName();
        //得到方法的参数的类型
        Class[] argClass = ((MethodSignature) point.getSignature()).getParameterTypes();
        Object returnVal = null;

        // 得到访问的方法对象
        Method method = className.getMethod(methodName, argClass);
        // 判断是否存在@Login注解
        if (method.isAnnotationPresent(Login.class)) {
            //获取参数列表
            //获取 request
            HttpServletRequest request = ((ServletRequestAttributes) Objects
                    .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            //参数列表中无HttpServletRequest抛出异常
            if (request == null) {
                throw new Exception(className + "." + methodName + "缺少HttpServletRequest参数");
            }
            //获取用户信息
            User model = sessionService.getSession(request);
            if (model == null) {
                throw new CustomException(ResultStatusEnum.NOT_LOGIN);
            }
            // 获取当前执行的方法
            ProceedingJoinPoint function = (ProceedingJoinPoint) point;
            //执行方法
            returnVal = function.proceed();

        }

        return returnVal;
    }
}
