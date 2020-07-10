package com.dosion.model.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dosion.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库表：sys_role
 *
 * @author Shaco
 * @create 2018-06-29
 */
@Data
@ApiModel(value = "角色")
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role")
public class Role extends BaseEntity<Role> {
    /**
     * 角色名称
     */
    @TableId
    @ApiModelProperty(value = "主键ID")
    private Integer id;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 英文名称
     */
    @ApiModelProperty(value = "英文名称")
    private String enName;

    /**
     * 等级
     */
    @ApiModelProperty(value = "等级")
    private Integer level;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String remarks;

    /**
     * 0-正常，1-删除
     */
    @TableLogic
    @ApiModelProperty(value = "删除标记,1:已删除,0:正常")
    private String delFlag;
}