package com.dosion.model.cat.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel(value = "省市区")
@EqualsAndHashCode(callSuper = true)
@TableName("i_device")
public class Device extends Model<Device> {


    public Device() {
    }

    @TableId
    public Integer id;


    @ApiModelProperty(value = "区域主键")
    public String number;

}
