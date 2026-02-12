package VaLocaProject.Sockets;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {
    

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public ChatMessage handleMessage(ChatMessage message) {
        return message;
    }
}
