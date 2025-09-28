package com.niuniu.airoleplaybackend.dto.req;

import lombok.Data;

/**
 * 创建对话
 * <p>
 * 作者：
 */
@Data
public class ChatRequestDTO {
    private String userId;
    private String templateId;
}
