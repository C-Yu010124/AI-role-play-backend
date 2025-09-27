package com.niuniu.airoleplaybackend.controller;

import com.niuniu.airoleplaybackend.dto.req.UserLoginRequestDTO;
import com.niuniu.airoleplaybackend.dto.resp.UserLoginResponseDTO;
import com.niuniu.airoleplaybackend.dto.req.UserRegisterRequestDTO;
import com.niuniu.airoleplaybackend.result.Result;
import com.niuniu.airoleplaybackend.service.IUserService;
import com.niuniu.airoleplaybackend.web.Results;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制层
 * <p>
 * 作者：
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private IUserService userService;
    
    /**
     * 用户注册
     *
     * @param registerDTO 注册信息
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result<Void> register(@RequestBody UserRegisterRequestDTO registerDTO) {
        return userService.register(registerDTO);
    }
    
    /**
     * 用户登录
     *
     * @param loginDTO 登录信息
     * @return 登录结果
     */
    @PostMapping("/login")
    public Result<UserLoginResponseDTO> login(@RequestBody UserLoginRequestDTO loginDTO) {
        return userService.login(loginDTO);
    }
    
    /**
     * 获取当前用户信息（需要登录）
     *
     * @return 用户信息
     */
    @GetMapping("/info")
    @PreAuthorize("hasRole('USER')")
    public Result<String> getUserInfo() {
        return Results.success("当前用户已登录，可以访问此接口");
    }
    
    /**
     * 管理员专用接口（需要管理员权限）
     *
     * @return 结果
     */
     @GetMapping("/admin")
     @PreAuthorize("hasRole('ADMIN')")
     public Result<String> adminOnly() {
         return Results.success("当前用户拥有管理员权限，可以访问此接口");
     }
}
