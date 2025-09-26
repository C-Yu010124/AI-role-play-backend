package com.niuniu.airoleplaybackend.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.niuniu.airoleplaybackend.service.AudioProcessService;
import com.niuniu.airoleplaybackend.service.OssService;
import com.niuniu.airoleplaybackend.websocket.AudioSessionData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class AudioProcessServiceImpl implements AudioProcessService {

    @Autowired
    private OssService ossService;
    
    @Autowired
    private AsrService asrService;
    
    @Autowired
    private TtsService ttsService;
    
    @Autowired
    private ChatService chatService;

    @Override
    public void processAudio(String base64Audio, WebSocketSession session, AudioSessionData sessionData) {
        CompletableFuture.runAsync(() -> {
            try {
                // 1. 解码Base64音频数据
                byte[] audioData = decodeBase64Audio(base64Audio);
                
                // 2. 保存为临时MP3文件
                File tempFile = saveTempMp3File(audioData);
                
                // 3. 上传到OSS
                String ossUrl = uploadToOss(tempFile);
                sessionData.setOssUrl(ossUrl);
                
                // 4. 发送上传成功消息
                sendUploadSuccessMessage(session, ossUrl);
                
                // 5. 调用语音识别服务
                String recognizedText = asrService.recognizeAudio(ossUrl);
                sessionData.setRecognizedText(recognizedText);
                
                // 6. 发送识别结果
                sendRecognitionResultMessage(session, recognizedText);
                
                // 7. 调用AI对话服务
                String aiResponse = generateAiResponse(recognizedText);
                sessionData.setAiResponse(aiResponse);
                
                // 8. 调用语音合成服务
                String synthesizedAudioUrl = ttsService.synthesizeSpeech(aiResponse);
                
                // 9. 发送AI回复和合成语音URL
                sendAiResponseMessage(session, aiResponse, synthesizedAudioUrl);
                
                // 10. 清理临时文件
                cleanupTempFile(tempFile);
                
            } catch (Exception e) {
                log.error("处理音频时出错: {}", e.getMessage(), e);
                sendErrorMessage(session, "处理音频时出错: " + e.getMessage());
            }
        });
    }
    
    private byte[] decodeBase64Audio(String base64Audio) {
        try {
            // 移除可能的Base64前缀（如data:audio/mp3;base64,）
            if (base64Audio.contains(",")) {
                base64Audio = base64Audio.split(",")[1];
            }
            return Base64.getDecoder().decode(base64Audio);
        } catch (Exception e) {
            log.error("解码Base64音频数据时出错: {}", e.getMessage(), e);
            throw new RuntimeException("解码Base64音频数据时出错", e);
        }
    }
    
    private File saveTempMp3File(byte[] audioData) {
        try {
            // 创建临时目录（如果不存在）
            Path tempDir = Path.of(System.getProperty("java.io.tmpdir"), "audio-chat");
            Files.createDirectories(tempDir);
            
            // 创建临时文件
            String fileName = UUID.randomUUID().toString() + ".mp3";
            File tempFile = new File(tempDir.toFile(), fileName);
            
            // 写入音频数据
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(audioData);
            }
            
            log.info("临时MP3文件已保存: {}", tempFile.getAbsolutePath());
            return tempFile;
        } catch (IOException e) {
            log.error("保存临时MP3文件时出错: {}", e.getMessage(), e);
            throw new RuntimeException("保存临时MP3文件时出错", e);
        }
    }
    
    private String uploadToOss(File file) {
        try {
            // 调用OSS服务上传文件
            return ossService.uploadFile(file.getName(), new ByteArrayInputStream(Files.readAllBytes(file.toPath())));
        } catch (Exception e) {
            log.error("上传文件到OSS时出错: {}", e.getMessage(), e);
            throw new RuntimeException("上传文件到OSS时出错", e);
        }
    }
    
    private void sendUploadSuccessMessage(WebSocketSession session, String ossUrl) {
        JSONObject message = new JSONObject();
        message.put("type", "upload_success");
        message.put("ossUrl", ossUrl);
        sendMessage(session, message.toJSONString());
    }
    
    private void sendRecognitionResultMessage(WebSocketSession session, String recognizedText) {
        JSONObject message = new JSONObject();
        message.put("type", "recognition_result");
        message.put("text", recognizedText);
        sendMessage(session, message.toJSONString());
    }
    
    private String generateAiResponse(String userText) {
        // 调用AI对话服务
        log.info("生成AI回复，用户输入: {}", userText);
        return chatService.processUserInput(userText);
    }
    
    private void sendAiResponseMessage(WebSocketSession session, String aiResponse, String audioUrl) {
        JSONObject message = new JSONObject();
        message.put("type", "ai_response");
        message.put("text", aiResponse);
        message.put("audioUrl", audioUrl);
        sendMessage(session, message.toJSONString());
    }
    
    private void cleanupTempFile(File file) {
        try {
            if (file != null && file.exists()) {
                Files.delete(file.toPath());
                log.info("临时文件已删除: {}", file.getAbsolutePath());
            }
        } catch (IOException e) {
            log.warn("删除临时文件时出错: {}", e.getMessage());
        }
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
}