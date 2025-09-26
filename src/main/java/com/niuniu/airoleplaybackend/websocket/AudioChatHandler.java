package com.niuniu.airoleplaybackend.websocket;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.niuniu.airoleplaybackend.service.AudioProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class AudioChatHandler extends TextWebSocketHandler {

    private final Map<String, AudioSessionData> sessionDataMap = new ConcurrentHashMap<>();

    @Autowired
    private AudioProcessService audioProcessService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String sessionId = session.getId();
        log.info("WebSocket连接已建立: {}", sessionId);
        sessionDataMap.put(sessionId, new AudioSessionData(sessionId));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String sessionId = session.getId();
        String payload = message.getPayload();

        try {
            JSONObject jsonMessage = JSON.parseObject(payload);
            String type = jsonMessage.getString("type");

            switch (type) {
                case "audio_chunk":
                    handleAudioChunk(session, jsonMessage);
                    break;
                case "audio_end":
                    handleAudioEnd(session);
                    break;
                case "chat_message":
                    handleChatMessage(session, jsonMessage);
                    break;
                default:
                    log.warn("未知的消息类型: {}", type);
            }
        } catch (Exception e) {
            log.error("处理WebSocket消息时出错: {}", e.getMessage(), e);
            sendErrorMessage(session, "处理消息时出错: " + e.getMessage());
        }
    }

    private void handleAudioChunk(WebSocketSession session, JSONObject jsonMessage) {
        String sessionId = session.getId();
        AudioSessionData sessionData = sessionDataMap.get(sessionId);

        if (sessionData == null) {
            log.error("会话数据不存在: {}", sessionId);
            return;
        }

        String base64Chunk = jsonMessage.getString("data");
        int chunkIndex = jsonMessage.getIntValue("chunkIndex");
        int totalChunks = jsonMessage.getIntValue("totalChunks");

        // 存储音频分片
        sessionData.addAudioChunk(chunkIndex, base64Chunk);

        log.debug("接收到音频分片 {}/{} 来自会话: {}", chunkIndex, totalChunks, sessionId);

        // 发送确认消息
        JSONObject response = new JSONObject();
        response.put("type", "chunk_received");
        response.put("chunkIndex", chunkIndex);
        sendMessage(session, response.toJSONString());
    }

    private void handleAudioEnd(WebSocketSession session) {
        String sessionId = session.getId();
        AudioSessionData sessionData = sessionDataMap.get(sessionId);

        if (sessionData == null) {
            log.error("会话数据不存在: {}", sessionId);
            return;
        }

        try {
            // 通知客户端开始处理
            JSONObject processingMsg = new JSONObject();
            processingMsg.put("type", "processing");
            processingMsg.put("message", "正在处理音频...");
            sendMessage(session, processingMsg.toJSONString());

            // 处理完整音频
            String completeBase64Audio = sessionData.getCompleteAudioData();

            // 调用服务处理音频
            audioProcessService.processAudio(completeBase64Audio, session, sessionData);

            // 清理会话数据中的音频分片
            sessionData.clearAudioChunks();

        } catch (Exception e) {
            log.error("处理完整音频时出错: {}", e.getMessage(), e);
            sendErrorMessage(session, "处理音频时出错: " + e.getMessage());
        }
    }

    private void handleChatMessage(WebSocketSession session, JSONObject jsonMessage) {
        String sessionId = session.getId();
        String message = jsonMessage.getString("message");

        log.info("收到聊天消息 来自会话 {}: {}", sessionId, message);

        // 这里可以处理文本聊天消息，例如调用AI服务
        // 暂时简单回复
        JSONObject response = new JSONObject();
        response.put("type", "chat_response");
        response.put("message", "收到您的消息: " + message);
        sendMessage(session, response.toJSONString());
    }

    private void sendMessage(WebSocketSession session, String message) {
        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            log.error("发送WebSocket消息时出错: {}", e.getMessage(), e);
        }
    }

    private void sendErrorMessage(WebSocketSession session, String errorMessage) {
        JSONObject error = new JSONObject();
        error.put("type", "error");
        error.put("message", errorMessage);
        sendMessage(session, error.toJSONString());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String sessionId = session.getId();
        log.info("WebSocket连接已关闭: {}, 状态: {}", sessionId, status);

        // 清理会话数据
        sessionDataMap.remove(sessionId);
    }
}