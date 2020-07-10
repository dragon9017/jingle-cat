package com.dosion.model.system.service;


import com.dosion.constant.CacheConstants;
import com.github.pagehelper.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * session 工具类
 *
 * @author 陈登文
 */
@Slf4j
@Service
@AllArgsConstructor
public class SessionService<T> {

    private final RedisTemplate redisTemplate;

    /**
     * 获得管理员session
     *
     * @param request
     * @return
     */
    public T getSession(HttpServletRequest request) {
        try {
            String token = request.getHeader("token");
            if (StringUtil.isEmpty(token)) {
                log.info("未获取到token");
                return null;
            }
            Object object = redisTemplate.opsForValue().get(CacheConstants.USER_DETAILS + token);
            if (object == null) {
                return null;
            }
            return (T) object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获得管理员session
     *
     * @param token
     * @return
     */
    public T getSession(String token) {
        try {
            Object object = redisTemplate.opsForValue().get(CacheConstants.USER_DETAILS + token);
            if (object == null) {
                return null;
            }
            return (T) object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 设置session
     *
     * @param model
     */
    public String setSession(T model) {
        String token = UUID.randomUUID().toString();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.opsForValue().set(CacheConstants.USER_DETAILS + token, model
                , CacheConstants.USER_DETAILS_TIME, TimeUnit.HOURS);
        return token;
    }


    /**
     * 移除session
     *
     * @param request
     */
    public void removeSession(HttpServletRequest request) {
        String token = request.getHeader("token");
        redisTemplate.delete(CacheConstants.USER_DETAILS + token);
    }
}
