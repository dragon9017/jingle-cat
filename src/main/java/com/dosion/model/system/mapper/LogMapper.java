package com.dosion.model.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dosion.model.system.entity.SysLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * 日志 Dao
 *
 * @author 陈登文
 */
@Mapper
@Component
public interface LogMapper extends BaseMapper<SysLog> {

}