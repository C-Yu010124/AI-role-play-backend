package com.niuniu.airoleplaybackend.service;

/**
 * 语音识别服务接口
 */
public interface AsrService {
    
    /**
     * 识别音频文件
     * 
     * @param audioUrl 音频文件URL
     * @return 识别结果文本
     */
    String recognizeAudio(String audioUrl);

}