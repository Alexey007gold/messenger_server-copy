package com.softgroup.frontend.rest.configuration;

import com.softgroup.authorization.impl.configuration.AuthorizationImplConfig;
import com.softgroup.common.datamapper.configuration.DataMapperConfig;
import com.softgroup.common.router.configuration.CommonRouterConfig;
import com.softgroup.profile.impl.configuration.ProfileImplConfig;
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
@Import({DataMapperConfig.class,
        CommonRouterConfig.class,
        AuthorizationImplConfig.class,
        ProfileImplConfig.class
})
public class RestApplicationConfiguration {
}
