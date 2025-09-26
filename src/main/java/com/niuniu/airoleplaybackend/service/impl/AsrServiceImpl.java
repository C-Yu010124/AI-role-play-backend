package com.niuniu.airoleplaybackend.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.niuniu.airoleplaybackend.config.QiniuConfiguration;
import com.niuniu.airoleplaybackend.service.AsrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class AsrServiceImpl implements AsrService {
    @Autowired
    private QiniuConfiguration qiniuConfiguration;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String recognizeAudio(String audioUrl) {
        try {
            log.info("调用语音识别服务，音频URL: {}", audioUrl);
            
            // 准备请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + qiniuConfiguration.getQiniuApiKey());
            
            // 准备请求体
            JSONObject requestBody = new JSONObject();
            requestBody.put("audio_url", audioUrl);
            requestBody.put("language", "zh-CN");
            requestBody.put("format", "mp3");
            
            // 发送请求
            HttpEntity<String> request = new HttpEntity<>(requestBody.toJSONString(), headers);
            String response = restTemplate.postForObject(qiniuConfiguration.getQiniuAsrUrl(), request, String.class);
            
            // 解析响应
            JSONObject responseJson = JSON.parseObject(response);
            if (responseJson.containsKey("result") && responseJson.getString("result") != null) {
                String recognizedText = responseJson.getString("result");
                log.info("语音识别成功: {}", recognizedText);
                return recognizedText;
            } else {
                log.warn("语音识别返回空结果: {}", response);
                return "";
            }
        } catch (Exception e) {
            log.error("调用语音识别服务时出错: {}", e.getMessage(), e);
            return "语音识别失败，请重试";
        }
    }
}