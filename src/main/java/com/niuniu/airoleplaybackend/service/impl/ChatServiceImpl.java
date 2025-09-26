package com.niuniu.airoleplaybackend.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.niuniu.airoleplaybackend.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class ChatServiceImpl implements ChatService {

    @Value("${ai.api.url:https://api.example.com/chat}")
    private String aiApiUrl;
    
    @Value("${ai.api.key:defaultApiKey}")
    private String aiApiKey;
    
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String processUserInput(String userInput) {
        try {
            log.info("处理用户输入: {}", userInput);
            
            // 准备请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + aiApiKey);
            
            // 准备请求体
            JSONObject requestBody = new JSONObject();
            requestBody.put("message", userInput);
            requestBody.put("language", "zh-CN");
            
            // 发送请求
            HttpEntity<String> request = new HttpEntity<>(requestBody.toJSONString(), headers);
            String response = restTemplate.postForObject(aiApiUrl, request, String.class);
            
            // 解析响应
            JSONObject responseJson = JSON.parseObject(response);
            if (responseJson.containsKey("response") && responseJson.getString("response") != null) {
                String aiResponse = responseJson.getString("response");
                log.info("AI回复: {}", aiResponse);
                return aiResponse;
            } else {
                log.warn("AI对话返回空结果: {}", response);
                return "抱歉，我现在无法回答您的问题，请稍后再试。";
            }
        } catch (Exception e) {
            log.error("调用AI对话服务时出错: {}", e.getMessage(), e);
            return "抱歉，系统出现了问题，请稍后再试。";
        }
    }
}