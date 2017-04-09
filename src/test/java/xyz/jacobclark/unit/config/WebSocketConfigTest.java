package xyz.jacobclark.unit.config;

import org.junit.Test;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.SockJsServiceRegistration;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.StompWebSocketEndpointRegistration;

import static org.mockito.Mockito.*;

public class WebSocketConfigTest {

    @Test
    public void enablesInMemoryBroker() throws Exception {
        WebSocketConfig webSocketConfig = new WebSocketConfig();
        MessageBrokerRegistry mockMessageBrokerRegistry = mock(MessageBrokerRegistry.class);

        webSocketConfig.configureMessageBroker(mockMessageBrokerRegistry);

        verify(mockMessageBrokerRegistry, times(1)).enableSimpleBroker("/topic");
    }

    @Test
    public void setApplicationDestinationPrefixes() throws Exception {
        WebSocketConfig webSocketConfig = new WebSocketConfig();
        MessageBrokerRegistry mockMessageBrokerRegistry = mock(MessageBrokerRegistry.class);

        webSocketConfig.configureMessageBroker(mockMessageBrokerRegistry);

        verify(mockMessageBrokerRegistry, times(1)).setApplicationDestinationPrefixes("/app");
    }

    @Test
    public void registerStompEndpoints_ToWs() throws Exception {
        WebSocketConfig webSocketConfig = new WebSocketConfig();
        StompEndpointRegistry mockStompEndpointRegistry = mock(StompEndpointRegistry.class);
        StompWebSocketEndpointRegistration mockStompWebSocketEndpointRegistration = mock(StompWebSocketEndpointRegistration.class);

        when(mockStompEndpointRegistry.addEndpoint(any())).thenReturn(mockStompWebSocketEndpointRegistration);
        when(mockStompWebSocketEndpointRegistration.setAllowedOrigins(any())).thenReturn(mockStompWebSocketEndpointRegistration);

        webSocketConfig.registerStompEndpoints(mockStompEndpointRegistry);

        verify(mockStompEndpointRegistry, times(1)).addEndpoint("/ws");
    }

    @Test
    public void registerAllowedOriginWildcard() throws Exception {
        WebSocketConfig webSocketConfig = new WebSocketConfig();
        StompEndpointRegistry mockStompEndpointRegistry = mock(StompEndpointRegistry.class);
        StompWebSocketEndpointRegistration mockStompWebSocketEndpointRegistration = mock(StompWebSocketEndpointRegistration.class);

        when(mockStompEndpointRegistry.addEndpoint(any())).thenReturn(mockStompWebSocketEndpointRegistration);
        when(mockStompWebSocketEndpointRegistration.setAllowedOrigins(any())).thenReturn(mockStompWebSocketEndpointRegistration);

        webSocketConfig.registerStompEndpoints(mockStompEndpointRegistry);

        verify(mockStompWebSocketEndpointRegistration, times(1)).setAllowedOrigins("*");
    }

    @Test
    public void registerWithSocksJS() throws Exception {
        WebSocketConfig webSocketConfig = new WebSocketConfig();
        StompEndpointRegistry mockStompEndpointRegistry = mock(StompEndpointRegistry.class);
        StompWebSocketEndpointRegistration mockStompWebSocketEndpointRegistration = mock(StompWebSocketEndpointRegistration.class);
        SockJsServiceRegistration mockSockJsServiceRegistration = mock(SockJsServiceRegistration.class);

        when(mockStompEndpointRegistry.addEndpoint(any())).thenReturn(mockStompWebSocketEndpointRegistration);
        when(mockStompWebSocketEndpointRegistration.setAllowedOrigins(any())).thenReturn(mockStompWebSocketEndpointRegistration);
        when(mockStompWebSocketEndpointRegistration.withSockJS()).thenReturn(mockSockJsServiceRegistration);

        webSocketConfig.registerStompEndpoints(mockStompEndpointRegistry);

        verify(mockStompWebSocketEndpointRegistration, times(1)).withSockJS();
    }

}