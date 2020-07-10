package com.dosion.model.cat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dosion.model.cat.entity.Device;
import com.dosion.model.cat.mapper.DeviceMapper;
import com.dosion.model.cat.service.DeviceService;
import org.springframework.stereotype.Service;


@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements DeviceService {

}
