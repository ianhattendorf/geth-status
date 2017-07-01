package com.ianhattendorf.geth.gethstatus.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ianhattendorf.geth.gethstatus.domain.GethStatus;
import com.ianhattendorf.geth.gethstatus.service.GethService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class UpdateClients {

    private static final Logger logger = LoggerFactory.getLogger(UpdateClients.class);

    private final GethService gethService;
    private final SimpMessagingTemplate template;
    private final ObjectMapper objectMapper;


    // number of clients connected, not necessarily subscribed
    private AtomicInteger numConnected = new AtomicInteger();

    @Autowired
    public UpdateClients(GethService gethService, SimpMessagingTemplate template,
                         MappingJackson2HttpMessageConverter springMvcJacksonConverter) {
        this.gethService = gethService;
        this.template = template;
        this.objectMapper = springMvcJacksonConverter.getObjectMapper();
    }

    @Scheduled(fixedRate = 5000)
    public void sendUpdate() {
        if (numConnected.get() == 0) {
            return;
        }
        GethStatus gethStatus = new GethStatus(gethService);
        try {
            template.convertAndSend("/topic/status", objectMapper.writeValueAsString(gethStatus));
            logger.trace("sent update, {} connected client(s)", numConnected);
        } catch (JsonProcessingException e) {
            logger.error("Exception writing gethStatus object to json", e);
        }
    }

    @EventListener
    public void handleSessionConnectEvent(SessionConnectEvent event) {
        int clientNumber = numConnected.incrementAndGet();
        logger.debug("Client connected, {} currently connected", clientNumber);
    }

    @EventListener
    public void handleSessionDisconnectEvent(SessionDisconnectEvent event) {
        int clientNumber = numConnected.decrementAndGet();
        logger.debug("Client disconnected, {} remaining", clientNumber);
    }
}
