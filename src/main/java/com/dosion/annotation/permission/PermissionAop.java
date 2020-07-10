package com.dosion.annotation.permission;

import cn.hutool.core.util.StrUtil;
import com.dosion.back.R;
import com.dosion.model.system.entity.User;
import com.dosion.model.system.service.SessionService;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 环绕通知
 * 拦截权限标识方法 ，权限验证
 *
 * @author 陈登文
 */
@Aspect
@Component
@AllArgsConstructor
public class PermissionAop {
    private final SessionService<User> sessionService;


    @Around("@annotation(com.dosion.annotation.permission.Permission)")
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
        // 判断是否存在@Permission注解
        if (method.isAnnotationPresent(Permission.class)) {
            Permission annotation = method.getAnnotation(Permission.class);
            //获取参数列表
            Object[] args = point.getArgs();
            HttpServletRequest request = null;
            for (Object arg : args) {
                if (arg instanceof HttpServletRequest) {
                    request = (HttpServletRequest) arg;
                    break;
                }
            }
            //参数列表中无HttpServletRequest抛出异常
            if (request == null) {
                throw new Exception(className + "." + methodName + "缺少HttpServletRequest参数");
            }
            //获取用户信息
            User model = sessionService.getSession(request);
            if (model == null) {
                return new R().noLogin();
            }
            if (model.getRole().getLevel() != 1) {
                //   boolean flag = false;
                boolean flag = true;//暂时屏蔽 2019-7-10

                for (String e : model.getPermissions()) {
                    String[] split = annotation.value().split(",");
                    for (String permission : split) {
                        if (StrUtil.isNotBlank(e) && e.equals(permission)) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        break;
                    }
                }
                if (!flag) {
                    return new R().msg("您没有权限进行该操作");
                }
            }
            // 获取当前执行的方法
            ProceedingJoinPoint function = (ProceedingJoinPoint) point;
            //执行方法
            returnVal = function.proceed();
        }
        return returnVal;

    }
}
