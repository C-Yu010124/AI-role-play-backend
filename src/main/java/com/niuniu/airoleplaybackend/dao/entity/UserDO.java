package com.niuniu.airoleplaybackend.dao.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
public class UserDO implements Serializable, UserDetails {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * ID
     */
    private Long id;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 角色，多个角色用逗号分隔，如：ROLE_USER,ROLE_ADMIN
     */
    private String roles;
    
    /**
     * 账号是否未过期
     */
    private Boolean accountNonExpired = true;
    
    /**
     * 账号是否未锁定
     */
    private Boolean accountNonLocked = true;
    
    /**
     * 凭证是否未过期
     */
    private Boolean credentialsNonExpired = true;
    
    /**
     * 账号是否启用
     */
    private Boolean enabled = true;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (roles != null && !roles.isEmpty()) {
            String[] roleArray = roles.split(",");
            for (String role : roleArray) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
        }
        return authorities;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }
    
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}