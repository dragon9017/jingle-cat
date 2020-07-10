package com.dosion.base;

import com.dosion.model.system.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体公共属性
 *
 * @author 陈登文
 */
@Data
public abstract class BaseEntity<T> implements Serializable {

    private static final long serialVersionUID = -562749554251997781L;

    @ApiModelProperty(value = "主键ID")
    private Integer id;

    @ApiModelProperty(value = "创建人")
    private User createBy;

    @ApiModelProperty(value = "修改人")
    private User updateBy;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @ApiModelProperty(value = "修改时间")
    private Date updateDate;

    @ApiModelProperty(value = "删除标记")
    private String delFlag;

    public BaseEntity(Integer id) {
        super();
        this.id = id;
    }

    public BaseEntity() {
        super();
        this.delFlag = DEL_FLAG_NORMAL;
    }

    /**
     * 删除标记（0：正常；1：删除；2：审核；）
     */
    public static final String DEL_FLAG_NORMAL = "0";
    public static final String DEL_FLAG_DELETE = "1";
    public static final String DEL_FLAG_AUDIT = "2";

}
