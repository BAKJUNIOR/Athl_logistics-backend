package athl.logistics.athl_logistics.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // URL pour se connecter au WebSocket depuis le front
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*") // à restreindre en production
                .withSockJS(); // fallback pour les navigateurs sans support WS
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Préfixe pour les messages envoyés depuis le client
        registry.setApplicationDestinationPrefixes("/app");

        // Préfixe pour les topics sur lesquels les clients s'abonnent
        registry.enableSimpleBroker("/topic");
    }
}
