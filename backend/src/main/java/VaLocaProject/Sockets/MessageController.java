package VaLocaProject.Sockets;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    
    public MessageController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }


    // Controller to receive the messages going to "/app/public",
    // broadcast messages to all clients subscribed to the "/all/greetings" topic
    @MessageMapping("/public")
    @SendTo("/all/greetings")
    public ChatMessage handlePublicMessage(ChatMessage message) {
        return message;
    }

    // Receiving messages going to "/app/private" 
    // and sending them to the specific user subscribed to the "/specific" topic
    @MessageMapping("/private")
    public void handlePrivateMessage(ChatMessage message) {
        simpMessagingTemplate.convertAndSendToUser(message.getDestinationUser(), "/specific", message);
        System.out.println(message);
    }
}
