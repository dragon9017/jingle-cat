package com.dosion.model.system.entity;

import com.dosion.base.BaseEntity;
import lombok.Data;
/**
 * @author cdw
 * 管理员
 * */
@Data
public class FileImg extends BaseEntity<FileImg> {
	

    /** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1914536603586217322L;

    public FileImg() {
    }

    public FileImg(Integer id) {
        this.setId(id);
    }

	private String url;

    private String name;

    private String ext;
    /**
     * 所属文件夹
     */
    private Folder folder;

    /**
     * 缩略图
     */
    private String thumbnail;
    /**
     * 文件大小
     */
    private Integer size;

    /**
     * 文件类型 0=图片，1=音频，2=视频，3=附件，4=系统文件
     */
    private String type;
}