package com.dehe.service.impl;

import com.dehe.domain.User;
import com.dehe.mapper.UserMapper;
import com.dehe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectuser(String name) {
        return userMapper.selectuser(name);
    }

    @Override
    public Integer selectiptable(String ipaddress) {
       return userMapper.selectiptable(ipaddress);
    }
}
