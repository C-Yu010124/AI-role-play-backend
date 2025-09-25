package com.niuniu.airoleplaybackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niuniu.airoleplaybackend.dao.entity.UserDO;
import com.niuniu.airoleplaybackend.dao.mapper.UserMapper;
import com.niuniu.airoleplaybackend.dto.req.UserLoginRequestDTO;
import com.niuniu.airoleplaybackend.dto.resp.UserLoginResponseDTO;
import com.niuniu.airoleplaybackend.dto.req.UserRegisterRequestDTO;
import com.niuniu.airoleplaybackend.exception.ServiceException;
import com.niuniu.airoleplaybackend.result.Result;
import com.niuniu.airoleplaybackend.service.IUserService;
import com.niuniu.airoleplaybackend.util.JwtTokenUtil;
import com.niuniu.airoleplaybackend.web.Results;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public Result<Void> register(UserRegisterRequestDTO registerDTO) {
        // 参数校验
        if (!StringUtils.hasText(registerDTO.getUsername()) || !StringUtils.hasText(registerDTO.getPassword())) {
            throw new ServiceException("用户名或密码不能为空");
        }
        
        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            throw new ServiceException("两次输入的密码不一致");
        }
        
        // 检查用户名是否已存在
        UserDO existUserDO = userMapper.selectOne(new QueryWrapper<UserDO>().eq("username", registerDTO.getUsername()));
        if (existUserDO != null) {
            throw new ServiceException("用户名已存在");
        }
        
        // 创建新用户
        UserDO userDO = new UserDO();
        userDO.setUsername(registerDTO.getUsername());
        userDO.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        userDO.setPhone(registerDTO.getPhone());
        userDO.setRoles("ROLE_USER"); // 默认角色
        userDO.setCreateTime(LocalDateTime.now());
        userDO.setUpdateTime(LocalDateTime.now());
        
        // 保存用户
        userMapper.insert(userDO);
        
        return Results.success();
    }

    @Override
    public Result<UserLoginResponseDTO> login(UserLoginRequestDTO loginDTO) {
        // 参数校验
        if (!StringUtils.hasText(loginDTO.getUsername()) || !StringUtils.hasText(loginDTO.getPassword())) {
            throw new ServiceException("用户名或密码不能为空");
        }
        
        try {
            // 认证
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
            );
            
            // 保存认证信息
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // 生成token
            UserDO userDO = (UserDO) authentication.getPrincipal();
            String token = jwtTokenUtil.generateToken(userDO);
            
            // 构建返回结果
            UserLoginResponseDTO responseDTO = new UserLoginResponseDTO();
            responseDTO.setId(userDO.getId());
            responseDTO.setUsername(userDO.getUsername());
            responseDTO.setPhone(userDO.getPhone());
            responseDTO.setToken(token);
            
            return Results.success(responseDTO);
        } catch (Exception e) {
            throw new ServiceException("用户名或密码错误");
        }
    }

    @Override
    public UserDO findByUsername(String username) {
        return userMapper.selectOne(new QueryWrapper<UserDO>().eq("username", username));
    }
}