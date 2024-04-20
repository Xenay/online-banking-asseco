package asseco.praksa.OnlineBank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    // Handle subscription requests
    @MessageMapping("/subscribe")
    public void subscribe(SimpMessageHeaderAccessor headerAccessor, @Payload String topic) {
        // Here you can add logic to handle subscription requests if needed
        System.out.println("Subscribed to " + topic);
    }

    // Handle message sending to a specific topic
    @MessageMapping("/send")
    @SendTo
    public void sendMessage(@Payload String message, SimpMessageHeaderAccessor headerAccessor) {
        String destination = headerAccessor.getSessionAttributes().get("destination").toString();
        simpMessagingTemplate.convertAndSend(destination, message);
    }

    // This mapping handles notifications as per your original code
    @MessageMapping("/notify")
    @SendTo("/topic/notifications")
    public Message<?> sendNotification(final Message<?> message) {
        return message;
    }

    // Additional handlers can be added here for other STOMP commands like DISCONNECT
}
