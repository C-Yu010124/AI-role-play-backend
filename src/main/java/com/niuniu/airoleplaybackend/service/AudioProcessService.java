package com.niuniu.airoleplaybackend.service;

import com.niuniu.airoleplaybackend.websocket.AudioSessionData;
import org.springframework.web.socket.WebSocketSession;

public interface AudioProcessService {
    
    /**
     * 处理音频数据
     * 
     * @param base64Audio Base64编码的完整音频数据
     * @param session WebSocket会话
     * @param sessionData 会话数据
     */
    void processAudio(String base64Audio, WebSocketSession session, AudioSessionData sessionData);
}