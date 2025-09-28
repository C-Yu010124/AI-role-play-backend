package com.niuniu.airoleplaybackend.controller;

import com.niuniu.airoleplaybackend.dto.resp.VoiceListResponseDTO;
import com.niuniu.airoleplaybackend.result.Result;
import com.niuniu.airoleplaybackend.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色扮演Agent类
 * <p>
 * 作者：
 */
@RestController
@RequestMapping("/api/agent")
public class AgentController {

    @Autowired
    private AgentService agentService;

    @RequestMapping("/voiceList")
    public Result<VoiceListResponseDTO> getVoiceList() {
        return agentService.getVoiceList();
    }
}
