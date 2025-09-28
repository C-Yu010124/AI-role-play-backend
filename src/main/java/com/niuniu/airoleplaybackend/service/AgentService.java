package com.niuniu.airoleplaybackend.service;

import com.niuniu.airoleplaybackend.dto.resp.VoiceListResponseDTO;
import com.niuniu.airoleplaybackend.result.Result;

/**
 * <p>
 * 作者：
 */
public interface AgentService {

    /**
     * 返回声音列表
     */
    Result<VoiceListResponseDTO> getVoiceList();
}
