package com.niuniu.airoleplaybackend.config;

import com.niuniu.airoleplaybackend.common.context.UserContext;
import com.niuniu.airoleplaybackend.common.context.UserInfoDTO;
import com.niuniu.airoleplaybackend.dao.entity.UserDO;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 用户上下文传递配置
 * <p>
 * 作者：
 */
@Configuration
public class UserContextConfiguration implements WebMvcConfigurer {

    @Bean
    public UserContextInterceptor userContextInterceptor() {
        return new UserContextInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userContextInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/logout", "/error"); // 排除不需要用户信息的路径
    }

    /**
     * 用户上下文传递拦截器
     * 从 Spring Security 的 SecurityContext 中获取用户信息，并放入 ThreadLocal
     */
    static class UserContextInterceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(@Nullable HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable Object handler) throws Exception {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.isAuthenticated() &&
                !(authentication.getPrincipal() instanceof String && "anonymousUser".equals(authentication.getPrincipal()))) {
                Object principal = authentication.getPrincipal();
                UserInfoDTO userInfoDTO = convertToUserInfoDTO(principal);

                if (userInfoDTO != null) {
                    UserContext.setUser(userInfoDTO);
                }
            }
            
            return true;
        }

        @Override
        public void afterCompletion(@Nullable HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable Object handler, Exception ex) throws Exception {
            // 6. 请求处理完成后，清理 ThreadLocal，防止内存泄漏
            UserContext.removeUser();
        }

        /**
         * 将 Spring Security 的 Principal 对象转换为业务的 UserInfoDTO
         * @param principal 从 SecurityContext 中获取的 principal 对象
         * @return UserInfoDTO 或 null
         */
    private UserInfoDTO convertToUserInfoDTO(Object principal) {
        if (principal instanceof UserDO) {
            UserDO userDO = (UserDO) principal;
            return UserInfoDTO.builder()
                    .userId(String.valueOf(userDO.getId()))
                    .userName(userDO.getUsername())
                    // .roles(userDO.getRoles())
                    .build();
        }

        return null;
}

    }
}