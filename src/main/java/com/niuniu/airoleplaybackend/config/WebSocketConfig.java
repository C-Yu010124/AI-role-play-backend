package com.niuniu.airoleplaybackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import com.niuniu.airoleplaybackend.websocket.AudioChatV2Handler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final AudioChatV2Handler audioChatHandler;

    public WebSocketConfig(AudioChatV2Handler audioChatHandler) {
        this.audioChatHandler = audioChatHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(audioChatHandler, "/ws/audio-chat")
                .setAllowedOrigins("*"); // 在生产环境中应该限制允许的源
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        // 设置消息缓冲区大小，用于处理大型音频数据
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        // 设置空闲超时时间（毫秒）
        container.setMaxSessionIdleTimeout(60000L);
        return container;
    }
}