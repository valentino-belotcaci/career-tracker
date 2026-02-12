package VaLocaProject.Sockets;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    // To define the base endpoint used to connect to the webSocket server
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("*");
        // Some browsers do not support webSockets, so SockJS is a fallback option that emulates webSockets using HTTP long polling technique.
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
    }

    @Override
    // 
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // This line configures the broker, so the destination to broadcast messages to clients
        registry.enableSimpleBroker("/all", "/specific");
        // This line instead configures the prefix for messages sent from clients to the server
        registry.setApplicationDestinationPrefixes("/app");
    }
    
}