package com.ianhattendorf.geth.gethstatus.task;

import com.ianhattendorf.geth.gethstatus.domain.geth.GethStatus;
import com.ianhattendorf.geth.gethstatus.service.GethStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class UpdateClients {

    private final GethStatusService gethStatusService;
    private final SimpMessagingTemplate template;

    // number of clients connected, not necessarily subscribed
    private AtomicInteger numConnected = new AtomicInteger();
    private GethStatus gethStatus;

    @Autowired
    public UpdateClients(GethStatusService gethStatusService, SimpMessagingTemplate template) {
        this.gethStatusService = gethStatusService;
        this.template = template;
    }

    @Scheduled(fixedDelayString = "${geth.stomp.updateCheckDelay}")
    public void sendUpdate() {
        if (numConnected.get() == 0) {
            log.trace("no clients connected, not sending update");
            return;
        }

        GethStatus newGethStatus = gethStatusService.getGethStatus();
        if (newGethStatus.equals(gethStatus)) {
            // only send update if gethStatus changed
            log.trace("retreived statuses are the same, not sending update");
            return;
        }
        gethStatus = newGethStatus;

        template.convertAndSend("/topic/status", gethStatus);
        log.trace("sent update, {} connected client(s)", numConnected);
    }

    @EventListener
    public void handleSessionConnectEvent(SessionConnectEvent event) {
        int clientNumber = numConnected.incrementAndGet();
        log.debug("Client connected, {} currently connected", clientNumber);
    }

    @EventListener
    public void handleSessionDisconnectEvent(SessionDisconnectEvent event) {
        int clientNumber = numConnected.decrementAndGet();
        log.debug("Client disconnected, {} remaining", clientNumber);
    }
}
