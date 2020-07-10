package com.dosion.base;

import java.util.List;

/**
 * 公共dao
 * @author 陈登文
 */
public interface BaseDao<T> {
	
	/**
	 * 获得对象
	 * @param model
	 * @return
	 */
	T get(T model);
	
	/**
	 * 根据条件查询对象
	 * @param model
	 * @return
	 */
	T getByWhere(T model);

	/**
	 * 查询所有
	 * @param model
	 * @return
	 */
	List<T> findAll(T model);

	/**
	 * 分页查询
	 * @param model
	 * @return
	 */
	List<T> findByPage(T model);

	/**
	 * 查询返回个数
	 * @param model
	 * @return
	 */
	Integer findCount(T model);

	/**
	 * 插入数据
	 * @param model
	 */
	void insert(T model);
	
	/**
	 * 更新数据
	 * @param model
	 */
	void update(T model);

	/**
	 * 删除数据
	 * @param model
	 */
	void delete(T model);
}
