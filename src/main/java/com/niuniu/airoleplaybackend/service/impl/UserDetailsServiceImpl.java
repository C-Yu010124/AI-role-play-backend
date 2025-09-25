package com.niuniu.airoleplaybackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niuniu.airoleplaybackend.dao.entity.UserDO;
import com.niuniu.airoleplaybackend.dao.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDO userDO = userMapper.selectOne(new QueryWrapper<UserDO>().eq("username", username));
        if (userDO == null) {
            throw new UsernameNotFoundException("用户不存在: " + username);
        }
        return userDO;
    }
}