package com.alexkoveckiy.frontend.ws.configuration;

import com.alexkoveckiy.authorization.impl.configuration.AuthorizationImplConfig;
import com.alexkoveckiy.common.dao.configuration.PersistentJPAConfig;
import com.alexkoveckiy.common.datamapper.configuration.DataMapperConfig;
import com.alexkoveckiy.common.isonline.configuration.IsOnlineConfig;
import com.alexkoveckiy.common.modelmapper.configuration.CommonModelMapperConfig;
import com.alexkoveckiy.common.router.configuration.CommonRouterConfig;
import com.alexkoveckiy.common.token.configuration.TokenConfig;
import com.alexkoveckiy.common.wssession.configuration.WSSessionServiceConfig;
import com.alexkoveckiy.frontend.ws.TokenCheckingHandshakeInterceptor;
import com.alexkoveckiy.frontend.ws.WebSocketMessageHandler;
import com.alexkoveckiy.messenger.impl.configuration.MessengerImplConfig;
import com.alexkoveckiy.profile.impl.configuration.ProfileImplConfig;
import org.springframework.context.annotation.*;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Created by alex on 02.04.17.
 */
@Configuration
@EnableWebSocket
@ComponentScan(basePackages = "com.alexkoveckiy.frontend.ws",
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class)
)
@Import({DataMapperConfig.class,
        CommonRouterConfig.class,
        AuthorizationImplConfig.class,
        ProfileImplConfig.class,
        MessengerImplConfig.class,
        PersistentJPAConfig.class,
        TokenConfig.class,
        CommonModelMapperConfig.class,
        WSSessionServiceConfig.class,
        IsOnlineConfig.class
})
public class WSConfiguration implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(webSocketMessageHandler(), "/server/public/ws")
                .setAllowedOrigins("*")
                .addInterceptors(tokenCheckingHandshakeInterceptor());
    }

    @Bean
    public WebSocketMessageHandler webSocketMessageHandler() {
        return new WebSocketMessageHandler();
    }

    @Bean
    public TokenCheckingHandshakeInterceptor tokenCheckingHandshakeInterceptor() {
        return new TokenCheckingHandshakeInterceptor();
    }
}
