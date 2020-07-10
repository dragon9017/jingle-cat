package com.dosion.model.system.mapper;

import com.dosion.base.BaseDao;
import com.dosion.model.system.entity.Dict;
import org.apache.ibatis.annotations.Mapper;

/**
 * 字典 Dao
 * @author 陈登文
 */
@Mapper
public interface DictDao extends BaseDao<Dict> {


  Dict getDict(Dict dict);
}
