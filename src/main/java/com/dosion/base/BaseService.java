package com.dosion.base;

import com.dosion.model.system.entity.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Service公共方法
 *
 * @author 陈登文
 */
public abstract class BaseService<D extends BaseDao<T>, T extends BaseEntity<T>> {

  @Autowired
  protected D dao;

  /**
   * 获取一条数据
   *
   * @param model
   * @return
   */
  public T get(T model) {
    return dao.get(model);
  }

  /**
   * 根据条件查询对象
   *
   * @param model
   * @return
   */
  public T getByWhere(T model) {
    return dao.getByWhere(model);
  }

  /**
   * 查询所有
   *
   * @param model
   * @return
   */
  public List<T> findAll(T model) {
    return dao.findAll(model);
  }

  /**
   * 删除
   *
   * @param model
   * @throws Exception
   */
  public void delete(T model) {
    dao.delete(model);
  }


  /**
   * 查询返回个数
   *
   * @param model
   * @return
   */
  public Integer findCount(T model) {
    return dao.findCount(model);
  }

  /**
   * 添加或修改
   *
   * @param model
   */
  public void save(T model) {
    if (model.getId() == null) {
      dao.insert(model);
    } else {
      dao.update(model);
    }
  }

  /**
   * 添加或修改(带人员)
   *
   * @param model
   * @param manager
   */
  public void save(T model, User manager) {
    if (model.getId() == null) {
      model.setCreateBy(manager);
      model.setUpdateBy(manager);
      dao.insert(model);
    } else {
      model.setUpdateBy(manager);
      model.setUpdateDate(new Date());
      dao.update(model);
    }
  }

  /**
   * 分页查询
   *
   * @param appDO
   * @param page
   * @param limit
   * @return
   */
  public PageInfo<T> findByPage(T appDO, Integer page, Integer limit) {
    PageHelper.startPage(page, limit);
    List<T> byPage = dao.findByPage(appDO);
    PageInfo<T> appDOPageInfo = new PageInfo<T>(byPage);
    return appDOPageInfo;
  }

}
