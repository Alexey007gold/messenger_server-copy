package com.alexkoveckiy.authorization.impl.handler;

import com.alexkoveckiy.authorization.api.message.RegisterRequest;
import com.alexkoveckiy.authorization.api.message.RegisterResponse;
import com.alexkoveckiy.authorization.impl.model.RegSessions;
import com.alexkoveckiy.common.protocol.ActionHeader;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by alex on 21.03.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class RegisterRequestHandlerTest {

    @InjectMocks
    private RegisterRequestHandler registerRequestHandler = Mockito.mock(RegisterRequestHandler.class, Mockito.CALLS_REAL_METHODS);

    @Spy
    private RegSessions regSessions = new RegSessions();

    @Test
    public void processTest() {
        Request<RegisterRequest> request = new Request<>();
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPhoneNumber("+380631234567");
        registerRequest.setLocaleCode("uk_UA");
        registerRequest.setDeviceID("dev_id");
        request.setHeader(new ActionHeader("uuid1", null, "register", "authorization", ""));
        request.setData(registerRequest);

        Response<RegisterResponse> response = registerRequestHandler.process(request);
        assertThat(response.getData().getRegistrationTimeoutSec(), is(notNullValue(Integer.class)));
        assertThat(response.getData().getRegistrationRequestUuid(), is(notNullValue(String.class)));
        assertThat(response.getData().getAuthCode(), is(notNullValue(Integer.class)));

        assertThat(response.getStatus().getCode(), is(200));
        assertThat(response.getHeader().getUuid(), is(notNullValue(String.class)));
        assertThat(response.getHeader().getOriginUuid(), is("uuid1"));
        assertThat(response.getHeader().getType(), is("authorization"));
        assertThat(response.getHeader().getCommand(), is("register"));
    }
}
