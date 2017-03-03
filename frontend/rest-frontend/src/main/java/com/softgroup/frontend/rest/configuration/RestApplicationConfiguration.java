package com.softgroup.frontend.rest.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

/**
 * Created by alex on 16.02.17.
 */

@Configuration
@ComponentScan(basePackages = "com.softgroup.frontend.rest",
    excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class)
)
@Import({com.softgroup.common.datamapper.configuration.DataMapperConfig.class,
        com.softgroup.common.router.configuration.CommonRouterConfig.class,
        com.softgroup.authorization.impl.configuration.AuthorizationImplConfig.class,
        com.softgroup.profile.impl.configuration.ProfileImplConfig.class
})
public class RestApplicationConfiguration {
}
