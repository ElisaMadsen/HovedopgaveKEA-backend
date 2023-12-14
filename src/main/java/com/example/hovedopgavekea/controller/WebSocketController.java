package com.example.hovedopgavekea.controller;

import com.example.hovedopgavekea.model.WebSocketMessageModel;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/send/message")
    @SendTo("/topic/messages")
    public WebSocketMessageModel sendMessage(WebSocketMessageModel message) {
        // Process the message, save it to the database, etc.
        return message;
    }
}
