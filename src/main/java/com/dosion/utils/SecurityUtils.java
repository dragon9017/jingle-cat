

package com.dosion.utils;


import cn.hutool.extra.spring.SpringUtil;
import com.dosion.model.system.entity.User;
import com.dosion.model.system.service.SessionService;
import lombok.experimental.UtilityClass;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 安全工具类
 *
 * @author L.cm
 */
@UtilityClass
public class SecurityUtils {

    /**
     * 获取用户
     */
    public User getUser() {
        //获取 bean
        SessionService<User> sessionService = SpringUtil.getBean("sessionService");
        //获取 request
        HttpServletRequest request = ((ServletRequestAttributes) Objects
                .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        User manager = sessionService.getSession(request);
        if (manager == null) {
            return null;
        }
        return manager;
    }

    /**
     * 设置用户
     */
    public String setUser(User user) {
        //获取 bean
        SessionService<User> sessionService = SpringUtil.getBean("sessionService");
        String token = sessionService.setSession(user);
        return token;
    }

}
