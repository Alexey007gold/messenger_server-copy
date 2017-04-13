package com.alexkoveckiy.authorization.impl.handler;

import com.alexkoveckiy.authorization.api.message.LoginRequest;
import com.alexkoveckiy.authorization.api.message.LoginResponse;
import com.alexkoveckiy.common.dao.entities.DeviceEntity;
import com.alexkoveckiy.common.dao.service.DeviceService;
import com.alexkoveckiy.common.protocol.ActionHeader;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.token.api.TokenHandler;
import com.alexkoveckiy.common.token.impl.TokenHandlerImpl;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.NumericDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static com.alexkoveckiy.common.token.impl.TokenHandlerImpl.Claim.DEVICE_ID;
import static com.alexkoveckiy.common.token.impl.TokenHandlerImpl.Claim.PROFILE_ID;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by alex on 21.03.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginRequestHandlerTest {

    @InjectMocks
    private LoginRequestHandler loginRequestHandler;

    @Spy
    private TokenHandler tokenHandler = Mockito.mock(TokenHandlerImpl.class);

    @Spy
    private DeviceService deviceService = Mockito.mock(DeviceService.class);


    @Test
    public void processTest() throws Exception {
        JwtClaims claims = new JwtClaims();
        claims.setClaim(PROFILE_ID, "prof");
        claims.setClaim(DEVICE_ID, "dev");
        claims.setIssuedAt(NumericDate.fromMilliseconds(5000L));
        when(tokenHandler.getClaimsFromDeviceToken(any())).thenReturn(claims);
        when(tokenHandler.createTemporaryToken(any(), any())).thenReturn("temptok");
        DeviceEntity deviceEntity = new DeviceEntity();
        deviceEntity.setConfirmationTime(5000L);
        when(deviceService.findByProfileIdAndDeviceId(any(), any())).thenReturn(deviceEntity);

        Request<LoginRequest> request = new Request<>();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setDeviceToken("tok");
        request.setHeader(new ActionHeader("uuid1", null, "login", "authorization", ""));
        request.setData(loginRequest);

        Response<LoginResponse> response = loginRequestHandler.process(request);
        assertThat(response.getData().getToken(), is("temptok"));

        assertThat(response.getStatus().getCode(), is(200));
        assertThat(response.getHeader().getUuid(), is(notNullValue(String.class)));
        assertThat(response.getHeader().getOriginUuid(), is("uuid1"));
        assertThat(response.getHeader().getType(), is("authorization"));
        assertThat(response.getHeader().getCommand(), is("login"));
    }

}
