package com.alexkoveckiy.frontend.rest.configuration;

import com.alexkoveckiy.authorization.impl.configuration.AuthorizationImplConfig;
import com.alexkoveckiy.common.dao.configuration.PersistentJPAConfig;
import com.alexkoveckiy.common.datamapper.configuration.DataMapperConfig;
import com.alexkoveckiy.common.router.configuration.CommonRouterConfig;
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
        PersistentJPAConfig.class
})
public class RestApplicationConfiguration {
}
