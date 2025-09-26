package com.niuniu.airoleplaybackend.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.niuniu.airoleplaybackend.service.TtsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class TtsServiceImpl implements TtsService {

    @Value("${tts.api.url:https://api.example.com/tts}")
    private String ttsApiUrl;
    
    @Value("${tts.api.key:defaultApiKey}")
    private String ttsApiKey;
    
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String synthesizeSpeech(String text) {
        try {
            log.info("调用语音合成服务，文本内容: {}", text);
            
            // 准备请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + ttsApiKey);
            
            // 准备请求体
            JSONObject requestBody = new JSONObject();
            requestBody.put("text", text);
            requestBody.put("voice", "zh-CN-XiaoxiaoNeural");
            requestBody.put("output_format", "mp3");
            
            // 发送请求
            HttpEntity<String> request = new HttpEntity<>(requestBody.toJSONString(), headers);
            String response = restTemplate.postForObject(ttsApiUrl, request, String.class);
            
            // 解析响应
            JSONObject responseJson = JSON.parseObject(response);
            if (responseJson.containsKey("audio_url") && responseJson.getString("audio_url") != null) {
                String audioUrl = responseJson.getString("audio_url");
                log.info("语音合成成功: {}", audioUrl);
                return audioUrl;
            } else {
                log.warn("语音合成返回空结果: {}", response);
                return "";
            }
        } catch (Exception e) {
            log.error("调用语音合成服务时出错: {}", e.getMessage(), e);
            return "";
        }
    }
}