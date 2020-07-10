package com.dosion.model.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dosion.model.system.entity.UploadSetting;
import com.dosion.model.system.mapper.UploadSettingMapper;
import com.dosion.model.system.service.UploadSettingService;
import org.springframework.stereotype.Service;

@Service
public class UploadSettingServiceImpl extends ServiceImpl<UploadSettingMapper, UploadSetting> implements UploadSettingService {
}
