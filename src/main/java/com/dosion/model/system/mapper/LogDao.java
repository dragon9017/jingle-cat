package com.dosion.model.system.mapper;

import com.dosion.base.BaseDao;
import com.dosion.model.system.entity.SysLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * 日志 Dao
 * @author 陈登文
 */
@Mapper
@Component
public interface LogDao extends BaseDao<SysLog> {

}