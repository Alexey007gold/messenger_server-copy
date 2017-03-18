package com.alexkoveckiy.frontend.rest.security.configuration;

import com.alexkoveckiy.frontend.rest.security.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * Created by alex on 17.03.17.
 */
@Configuration
@EnableWebSecurity
public class CustomSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    private Filter filter;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/server/public/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().anyRequest().authenticated();
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
