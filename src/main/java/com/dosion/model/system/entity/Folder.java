package com.dosion.model.system.entity;

import com.dosion.base.BaseEntity;
import lombok.Data;

/**
 * 文件夹实体类
 * 数据库表：e_folder
 *
 * @author cdw
 * @date 2019-01-03 18:19:43
 */
@Data
public class Folder extends BaseEntity<Folder> {

    /**
     * 类型 0=图片 1=音频 2=视频 3=附件 3=系统文件
     */
    private Integer type;

    /**
     * 文件夹名称
     */
    private String name;

    /**
     * 父级id
     */
    private String pid;
}
