package com.dosion.model.system.service;

import com.dosion.base.BaseService;
import com.dosion.model.system.mapper.LogDao;
import com.dosion.model.system.entity.SysLog;
import org.springframework.stereotype.Service;

/**
 * 日志 Service
 * @author 陈登文
 */
@Service
public class LogService extends BaseService<LogDao, SysLog> {

}
