package com.dosion.model.system.entity;

import com.dosion.base.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * 字典实体
 * @author 陈登文
 */
@Data
public class Dict extends BaseEntity<Dict> {

	/**
	 * 数据值
	 */
	private String value;
	/**
	 * 标签名
	 */
	private String label;
	/**
	 * 类型(英文别名)
	 */
	private String type;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 排序(升序)
	 */
	private Integer sort;
	/**
	 * 父级id
	 */
	private Integer pid;

	/**
	 * 类型列表
	 */
	private List<String> typeList;

  /**
   * 备注信息
   */
  private String remarks;
}
