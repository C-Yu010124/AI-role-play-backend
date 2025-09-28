package com.niuniu.airoleplaybackend.controller;

import com.niuniu.airoleplaybackend.dto.req.ChatRequestDTO;
import com.niuniu.airoleplaybackend.result.Result;
import com.niuniu.airoleplaybackend.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 对话控制层
 * <p>
 * 作者：
 */
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    public Result startChat(@RequestBody ChatRequestDTO req) {
        return null;
    }
}
