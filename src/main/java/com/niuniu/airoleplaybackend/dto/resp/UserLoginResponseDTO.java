package com.niuniu.airoleplaybackend.dto.resp;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserLoginResponseDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * 用户ID
     */
    private Long id;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 登录凭证
     */
    private String token;
}