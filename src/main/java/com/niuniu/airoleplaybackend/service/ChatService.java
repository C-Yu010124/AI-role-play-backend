package com.niuniu.airoleplaybackend.service;

/**
 * AI对话服务接口
 */
public interface ChatService {
    
    /**
     * 处理用户输入并生成AI回复
     * 
     * @param userInput 用户输入文本
     * @return AI回复文本
     */
    String processUserInput(String userInput);
}