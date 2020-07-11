package com.dosion.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 自定义异常枚举
 */
@NoArgsConstructor
@AllArgsConstructor
public enum ResultStatusEnum {
    /**
     * 未授权，请登录
     */
    NOT_LOGIN(401, "未授权，请登录"),

    /**
     * 没有权限
     */
    NOT_AUTH(403, "拒绝访问");

    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private String message;
}