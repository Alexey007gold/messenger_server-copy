package com.alexkoveckiy.frontend.rest;

import com.alexkoveckiy.authorization.impl.router.AuthorizationRouter;
import com.alexkoveckiy.common.dao.entities.ProfileStatusEntity;
import com.alexkoveckiy.common.dao.service.ProfileStatusService;
import com.alexkoveckiy.common.protocol.ActionHeader;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.RoutingData;
import com.alexkoveckiy.common.router.impl.FirstRouter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpSession;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by alex on 21.03.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class MyRestControllerTest {

    @InjectMocks
    private MyRestController restController;

    @Spy
    private FirstRouter firstRouter = new FirstRouter();

    @Spy
    private AuthorizationRouter authorizationRouter = new AuthorizationRouter();

    @Spy
    private ProfileStatusService profileStatusService = Mockito.mock(ProfileStatusService.class);

    @InjectMocks
    private HttpSession httpSession = Mockito.mock(HttpSession.class);

    private Request<?> request1;
    private Request<?> request2;

    private RoutingData routingData = new RoutingData("user", "device");

    @Before
    public void init() {
        request1 = new Request<>();
        ActionHeader header1 = new ActionHeader();
        header1.setType("handler_one");
        request1.setHeader(header1);
        request2 = new Request<>();
        ActionHeader header2 = new ActionHeader();
        header2.setType("handler_two");
        request2.setHeader(header2);
        when(httpSession.getAttribute("routing_data")).thenReturn(routingData);
    }

    @Test
    public void processPublicRequestTest() {
        restController.processPublicRequest(request1);
        verify(authorizationRouter).handle(request1);
        verify(firstRouter, never()).handle(any());

        restController.processPublicRequest(request2);
        verify(authorizationRouter).handle(request2);
        verify(firstRouter, never()).handle(any());
    }

    @Test
    public void processPrivateRequestTest() {
        when(profileStatusService.findByProfileId(any(String.class))).thenReturn(Mockito.mock(ProfileStatusEntity.class));
        restController.processPrivateRequest(request1, httpSession);
        verify(firstRouter).handle(request1);
        verify(authorizationRouter, never()).handle(any());

        restController.processPrivateRequest(request2, httpSession);
        verify(firstRouter).handle(request2);
        verify(authorizationRouter, never()).handle(any());
    }
}
