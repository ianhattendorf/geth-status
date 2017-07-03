package com.ianhattendorf.geth.gethstatus.ws;

import com.ianhattendorf.geth.gethstatus.domain.GethStatus;
import com.ianhattendorf.geth.gethstatus.service.GethStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
public class InitController {

    private final GethStatusService gethStatusService;
    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public InitController(GethStatusService gethStatusService, SimpMessageSendingOperations messagingTemplate) {
        this.gethStatusService = gethStatusService;
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Send the initial status to a connecting client.
     * @param headerAccessor Injected {@link SimpMessageHeaderAccessor}
     */
    @MessageMapping("/init")
    public void status(SimpMessageHeaderAccessor headerAccessor) {
        String id = headerAccessor.getSessionId();
        headerAccessor.setLeaveMutable(true);
        GethStatus message = gethStatusService.getGethStatus();
        messagingTemplate.convertAndSendToUser(id, "/queue/init", message, headerAccessor.getMessageHeaders());
    }
}
