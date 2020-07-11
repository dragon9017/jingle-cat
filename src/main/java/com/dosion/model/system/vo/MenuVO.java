

package com.dosion.model.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 菜单权限表
 * </p>
 *
 * @author cdw
 * @since 2017-11-08
 */
@Data
@ApiModel(value = "菜单展示对象")
public class MenuVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    @ApiModelProperty(value = "菜单id")
    private Integer id;
    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    private String title;
    /**
     * 菜单权限标识
     */
    @ApiModelProperty(value = "菜单权限标识")
    private String permission;
    /**
     * 父菜单ID
     */
    @ApiModelProperty(value = "父菜单id")
    private Integer parentId;
    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    private String icon;
    /**
     * 前端路由标识路径
     */
    @ApiModelProperty(value = "前端路由标识路径")
    private String path;
    /**
     * 排序值
     */
    @ApiModelProperty(value = "排序值")
    private Integer sort;
    /**
     * 菜单类型 （0菜单 1按钮）
     */
    @ApiModelProperty(value = "菜单类型,0:菜单 1:按钮")
    private String type;
    /**
     * 是否缓冲
     */
    @ApiModelProperty(value = "路由缓冲")
    private String keepAlive;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    /**
     * 0--正常 1--删除
     */
    @ApiModelProperty(value = "删除标记,1:已删除,0:正常")
    private String delFlag;


    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * menuId 相同则相同
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MenuVO) {
            Integer targetMenuId = ((MenuVO) obj).getId();
            return id.equals(targetMenuId);
        }
        return super.equals(obj);
    }
}
