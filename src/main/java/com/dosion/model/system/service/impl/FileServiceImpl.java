package com.dosion.model.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dosion.model.system.entity.File;
import com.dosion.model.system.mapper.FileMapper;
import com.dosion.model.system.service.FileService;
import org.springframework.stereotype.Service;


@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {

}
