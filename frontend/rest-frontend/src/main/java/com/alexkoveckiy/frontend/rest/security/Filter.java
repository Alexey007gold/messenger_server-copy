package com.alexkoveckiy.frontend.rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.alexkoveckiy.common.token.api.TokenHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by alex on 17.03.17.
 */
@Component
public class Filter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private TokenHandler tokenHandler;

    private TokenAuthentication tokenAuthentication;

    public Filter() {
        this.tokenAuthentication = new TokenAuthentication();
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        try {
            ((HttpServletRequest)req).getSession()
                    .setAttribute("routing_data", tokenHandler.getRoutingDataFromTemporaryToken(req.getParameter("x-token")));
            SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);
            chain.doFilter(req, res);
        } catch (InvalidTokenException ignored) {}
    }

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }
}
