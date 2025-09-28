package com.niuniu.airoleplaybackend.service.impl;

import com.niuniu.airoleplaybackend.config.QiniuConfiguration;
import com.niuniu.airoleplaybackend.dto.resp.VoiceListResponseDTO;
import com.niuniu.airoleplaybackend.result.Result;
import com.niuniu.airoleplaybackend.service.AgentService;
import com.niuniu.airoleplaybackend.web.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

/**
 * <p>
 * 作者：
 */
@Service
@Slf4j
public class AgentServiceImpl implements AgentService {
    @Autowired
    private QiniuConfiguration qiniuConfiguration;

    @Override
    public Result<VoiceListResponseDTO> getVoiceList() {
        String voiceListUrl = qiniuConfiguration.getQiniuBaseUrl() + "/voice/list";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + qiniuConfiguration.getQiniuApiKey());
        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(
                    voiceListUrl,          // 请求URL
                    HttpMethod.GET,        // 请求方法
                    requestEntity,         // 包含请求头的HttpEntity
                    String.class  // 响应类型
            );
            log.info("七牛云API原始响应: {}", response.getBody());

        } catch (RestClientException e) {
            log.error("请求失败：{}", e.getMessage());
            throw new RuntimeException(e);
        }
        // 4. 处理响应
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
//            return Results.success(response.getBody());
        }
        return null;
    }
}