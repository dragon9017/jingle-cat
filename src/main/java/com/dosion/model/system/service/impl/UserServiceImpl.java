package com.dosion.model.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dosion.model.system.entity.User;
import com.dosion.model.system.mapper.FileMapper;
import com.dosion.model.system.mapper.UserMapper;
import com.dosion.model.system.service.UserService;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
