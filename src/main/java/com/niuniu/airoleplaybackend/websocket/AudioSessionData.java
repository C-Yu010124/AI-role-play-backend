package com.niuniu.airoleplaybackend.websocket;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.TreeMap;


@Data
@Slf4j
public class AudioSessionData {
    
    private final String sessionId;
    private final Map<Integer, String> audioChunks = new TreeMap<>();
    private String ossUrl;
    private String recognizedText;
    private String aiResponse;
    
    public AudioSessionData(String sessionId) {
        this.sessionId = sessionId;
    }
    
    /**
     * 添加音频分片
     * 
     * @param chunkIndex 分片索引
     * @param base64Data Base64编码的音频数据
     */
    public void addAudioChunk(int chunkIndex, String base64Data) {
        audioChunks.put(chunkIndex, base64Data);
    }
    
    /**
     * 获取完整的音频数据
     * 
     * @return 合并后的Base64编码音频数据
     */
    public String getCompleteAudioData() {
        StringBuilder completeAudio = new StringBuilder();
        
        // 按索引顺序合并所有分片
        for (Map.Entry<Integer, String> entry : audioChunks.entrySet()) {
            completeAudio.append(entry.getValue());
        }
        
        return completeAudio.toString();
    }
    
    /**
     * 清理音频分片数据
     */
    public void clearAudioChunks() {
        audioChunks.clear();
    }
}