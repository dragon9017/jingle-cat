package com.dosion.model.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件上传设置
 */
@Data
@ApiModel(value = "文件上传设置")
@EqualsAndHashCode(callSuper = true)
@TableName("sys_upload_setting")
public class UploadSetting extends Model<UploadSetting> {
    @TableId
    @ApiModelProperty(value = "主键ID")
    private Integer id;

    @ApiModelProperty(value = "允许上传的文件类型")
    private String fileType;

    @ApiModelProperty(value = "允许上传的视频类型")
    private String videoType;

    @ApiModelProperty(value = "文件上传的最大值，单位kb，0不限制")
    private Integer fileSize;

    @ApiModelProperty(value = "视频上传的最大值，单位kb，0不限制")
    private Integer videoSize;

    @ApiModelProperty(value = "图片上传的最大值，单位kb，0不限制")
    private Integer imgSize;

    @ApiModelProperty(value = "图片最大宽度单位px，0不限制")
    private Integer imgAreaX;

    @ApiModelProperty(value = "图片最大高度单位px，0不限制")
    private Integer imgAreaY;

    @ApiModelProperty(value = "缩略图宽度，单位px,0不生成缩略图")
    private Integer thumbnailX;

    @ApiModelProperty(value = "缩略图高度，单位px,0不生成缩略图")
    private Integer thumbnailY;

    @ApiModelProperty(value = "水印类型")
    private Integer watermarkType;

    @ApiModelProperty(value = "水印位置")
    private Integer watermarkPosition;

    @ApiModelProperty(value = "水印图片")
    private String watermarkFile;

    @ApiModelProperty(value = "水印透明度（1-10）")
    private Integer watermarkTransparent;

    @ApiModelProperty(value = "水印文字")
    private String watermarkText;

    @ApiModelProperty(value = "水印文字大小,单位px")
    private Integer watermarkTextSize;
}
