package com.dosion.model.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * @author cdw
 * 用户
 */
@Data
@ApiModel(value = "用户")
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class User extends Model<User> {
    @TableId
    @ApiModelProperty(value = "主键ID")
    private Integer id;

    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id")
    private Integer roleId;

    /**
     * 角色
     */
    @ApiModelProperty(value = "角色")
    @TableField(exist = false)
    private Role role;

    /**
     * 登录名
     */
    @ApiModelProperty(value = "登录名")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "角色")
    private String password;
    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;
    /**
     * 性別
     */
    @ApiModelProperty(value = "性別")
    private String sex;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;
    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    private String phone;

    /**
     * 最后登陆ip
     */
    @ApiModelProperty(value = "最后登陆ip")
    private String loginIp;
    /**
     * 最后登陆时间
     */
    @ApiModelProperty(value = "最后登陆时间")
    private Date loginDate;
    /**
     * 是否可登陆
     */
    @ApiModelProperty(value = "是否可登陆")
    private Boolean loginFlag;

    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像")
    private String avatar;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickname;

    /**
     * 0-正常，1-删除
     */
    @TableLogic
    @ApiModelProperty(value = "删除标记,1:已删除,0:正常")
    private String delFlag;

    /**
     * 权限标识集合
     */
    @ApiModelProperty(value = "权限标识集合")
    @TableField(exist = false)
    private String[] permissions;

}
