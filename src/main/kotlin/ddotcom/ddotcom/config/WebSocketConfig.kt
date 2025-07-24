package ddotcom.ddotcom.config

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig() : WebSocketMessageBrokerConfigurer {

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.enableSimpleBroker("/topic")
        registry.setApplicationDestinationPrefixes("/app")
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/ws-chat")
            .setAllowedOriginPatterns("*")
            .withSockJS()
    }

//    // STOMP 메시지를 처리하기 전에 StompHandler를 거치도록 인터셉터를 등록합니다.
//    override fun configureClientInboundChannel(registration: ChannelRegistration) {
//        registration.interceptors(stompHandler)
//    }
}

//@Configuration
//@EnableWebSocketMessageBroker
//class WebSocketConfig(private val stompHandler: StompHandler) : WebSocketMessageBrokerConfigurer {
//
//    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
//        registry.enableSimpleBroker("/topic")
//        registry.setApplicationDestinationPrefixes("/app")
//    }
//
//    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
//        registry.addEndpoint("/ws-chat")
//            .setAllowedOriginPatterns("*")
//            .withSockJS()
//    }
//
//    // STOMP 메시지를 처리하기 전에 StompHandler를 거치도록 인터셉터를 등록합니다.
//    override fun configureClientInboundChannel(registration: ChannelRegistration) {
//        registration.interceptors(stompHandler)
//    }
//}
