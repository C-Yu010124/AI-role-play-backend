package com.niuniu.airoleplaybackend.dao.entity;

import io.swagger.v3.core.util.Json;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 智能体实例
 * <p>
 * 作者：
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "agent_instance")
public class AgentInstanceDocument {
    /**
     * 智能体实例ID
     */
    private String id;
    /**
     * 智能体模板ID
     */
    private String templateId;

    /**
     * 对话者ID
     */
    private String userId;

    /**
     * 提示词
     */
    private String prompt;

    /**
     * 对话场景
     */
    private String scene;

    /**
     * 聊天记录
     */
    private Json chatHistory;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 聊天记录
     */
    private List<ChatMessage> chatMessages;

    @Data
    public static class ChatMessage {
        private String role; // "user" 或 "assistant"
        private String content; // 消息内容
        private LocalDateTime timestamp;
    }
}
