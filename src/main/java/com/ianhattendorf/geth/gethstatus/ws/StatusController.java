package com.ianhattendorf.geth.gethstatus.ws;

import com.ianhattendorf.geth.gethstatus.domain.GethStatus;
import com.ianhattendorf.geth.gethstatus.service.GethService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
public class StatusController {

    private final GethService gethService;
    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public StatusController(GethService gethService, SimpMessageSendingOperations messagingTemplate) {
        this.gethService = gethService;
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
        GethStatus message = new GethStatus(gethService);
        messagingTemplate.convertAndSendToUser(id, "/queue/init", message, headerAccessor.getMessageHeaders());
    }
}
