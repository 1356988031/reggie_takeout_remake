package com.wushubin.reggie_takeout_remake.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wushubin.reggie_takeout_remake.entity.User;
import com.wushubin.reggie_takeout_remake.mapper.UserMapper;
import com.wushubin.reggie_takeout_remake.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
