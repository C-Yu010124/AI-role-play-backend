package com.niuniu.airoleplaybackend.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 对话角色
 * <p>
 * 作者：
 */
@AllArgsConstructor
public enum Role {
    USER("user"),
    ASSISTANT("assistant");
    @Getter
    private final String role;
}
