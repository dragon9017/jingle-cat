package com.dosion.model.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dosion.model.system.entity.SysLog;
import com.dosion.model.system.mapper.LogMapper;
import com.dosion.model.system.service.LogService;
import org.springframework.stereotype.Service;


@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, SysLog> implements LogService {

}
