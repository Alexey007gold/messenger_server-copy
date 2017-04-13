package com.alexkoveckiy.frontend.rest.configuration;

import com.alexkoveckiy.authorization.impl.configuration.AuthorizationImplConfig;
import com.alexkoveckiy.common.dao.configuration.PersistentJPAConfig;
import com.alexkoveckiy.common.datamapper.configuration.DataMapperConfig;
import com.alexkoveckiy.common.isonline.configuration.IsOnlineConfig;
import com.alexkoveckiy.common.modelmapper.configuration.CommonModelMapperConfig;
import com.alexkoveckiy.common.protocol.configuration.ProtocolConfig;
import com.alexkoveckiy.common.router.configuration.CommonRouterConfig;
import com.alexkoveckiy.common.token.configuration.TokenConfig;
import com.alexkoveckiy.common.wssession.configuration.WSSessionServiceConfig;
import com.alexkoveckiy.frontend.rest.security.configuration.CustomSecurityConfigurerAdapter;
import com.alexkoveckiy.messenger.impl.configuration.MessengerImplConfig;
import com.alexkoveckiy.profile.impl.configuration.ProfileImplConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

/**
 * Created by alex on 16.02.17.
 */

@Configuration
@ComponentScan(basePackages = "com.alexkoveckiy.frontend.rest",
    excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class)
)
@Import({DataMapperConfig.class,
        CommonRouterConfig.class,
        AuthorizationImplConfig.class,
        ProfileImplConfig.class,
        MessengerImplConfig.class,
        PersistentJPAConfig.class,
        TokenConfig.class,
        CustomSecurityConfigurerAdapter.class,
        CommonModelMapperConfig.class,
        WSSessionServiceConfig.class,
        IsOnlineConfig.class,
        ProtocolConfig.class
})
public class RestApplicationConfiguration {
}
