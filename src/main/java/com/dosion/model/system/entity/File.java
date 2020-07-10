package com.dosion.model.system.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel(value = "文件")
@EqualsAndHashCode(callSuper = true)
@TableName("sys_file")
public class File extends Model<File> {
    @TableId
    @ApiModelProperty(value = "主键ID")
    private Integer id;

    @ApiModelProperty(value = "文件类型 0=图片，1=音频，2=视频，3=附件，4=系统文件")
    private String type;

    @ApiModelProperty(value = "上传路径")
    private String url;

    @ApiModelProperty(value = "文件名称")
    private String name;

    @ApiModelProperty(value = "扩展名")
    private String ext;

}
