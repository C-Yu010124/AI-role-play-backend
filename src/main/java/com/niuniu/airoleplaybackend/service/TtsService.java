package com.niuniu.airoleplaybackend.service;

/**
 * 语音合成服务接口
 */
public interface TtsService {
    
    /**
     * 将文本合成为语音
     * 
     * @param text 要合成的文本
     * @return 合成后的语音URL
     */
    String synthesizeSpeech(String text);
}