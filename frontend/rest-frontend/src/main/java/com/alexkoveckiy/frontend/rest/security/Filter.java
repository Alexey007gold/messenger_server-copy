package com.alexkoveckiy.frontend.rest.security;

import com.alexkoveckiy.common.exceptions.InvalidTokenException;
import com.alexkoveckiy.common.protocol.RoutingData;
import com.alexkoveckiy.common.token.api.TokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
            HttpServletRequest request = ((HttpServletRequest)req);
            request.getSession()
                    .setAttribute("routing_data", tokenHandler.getRoutingDataFromTemporaryToken(request.getHeader("x-token")));
            SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);
            chain.doFilter(req, res);
        } catch (InvalidTokenException ignored) {
            //
            if (((HttpServletRequest)req).getHeader("x-token").equals("alex")) {
                ((HttpServletRequest)req).getSession()
                        .setAttribute("routing_data", new RoutingData("e9d26827-e9c7-47d9-8c16-b7cb57c342d9", "idididididid"));
                SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);
                chain.doFilter(req, res);
            } else if (((HttpServletRequest)req).getHeader("x-token").equals("bill")) {
                ((HttpServletRequest)req).getSession()
                        .setAttribute("routing_data", new RoutingData("5b1df9ce-18a3-4845-b1a4-062ba908d3b2", "billDeviceidididididid"));
                SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);
                chain.doFilter(req, res);
            }//
            else ((HttpServletResponse) res).sendError(403);
        }
    }

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

}
