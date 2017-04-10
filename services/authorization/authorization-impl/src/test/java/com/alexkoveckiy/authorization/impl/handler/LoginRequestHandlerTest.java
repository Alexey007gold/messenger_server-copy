package com.alexkoveckiy.authorization.impl.handler;

import com.alexkoveckiy.authorization.api.message.*;
import com.alexkoveckiy.authorization.impl.model.RegSessions;
import com.alexkoveckiy.common.dao.entities.DeviceEntity;
import com.alexkoveckiy.common.dao.entities.ProfileEntity;
import com.alexkoveckiy.common.dao.service.DeviceService;
import com.alexkoveckiy.common.dao.service.ProfileService;
import com.alexkoveckiy.common.dao.service.ProfileSettingsService;
import com.alexkoveckiy.common.dao.service.ProfileStatusService;
import com.alexkoveckiy.common.protocol.ActionHeader;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.token.api.TokenHandler;
import com.alexkoveckiy.common.token.impl.TokenHandlerImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

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

    @InjectMocks
    private RegisterRequestHandler registerRequestHandler = Mockito.mock(RegisterRequestHandler.class, Mockito.CALLS_REAL_METHODS);

    @InjectMocks
    private SmsConfirmRequestHandler smsConfirmRequestHandler = Mockito.mock(SmsConfirmRequestHandler.class, Mockito.CALLS_REAL_METHODS);

    @Spy
    private RegSessions regSessions = new RegSessions();

    @Spy
    private TokenHandler tokenHandler = new TokenHandlerImpl();

    @Spy
    private ProfileService profileService = Mockito.mock(ProfileService.class);

    @Spy
    private DeviceService deviceService = Mockito.mock(DeviceService.class);

    @Spy
    private ProfileSettingsService profileSettingsService = Mockito.mock(ProfileSettingsService.class);

    @Spy
    private ProfileStatusService profileStatusService = Mockito.mock(ProfileStatusService.class);

    private DeviceEntity deviceEntity = Mockito.mock(DeviceEntity.class);

    @Test
    public void processTest() throws Exception {
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setId("");
        when(profileService.save(any(ProfileEntity.class))).thenReturn(profileEntity);

        Request<RegisterRequest> request1 = new Request<>();
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPhoneNumber("+380631234567");
        registerRequest.setLocaleCode("uk_UA");
        registerRequest.setDeviceID("dev_id");
        request1.setHeader(new ActionHeader("uuid1", null, "register", "authorization", "HTTP/1.1"));
        request1.setData(registerRequest);
        Response<RegisterResponse> response1 = registerRequestHandler.process(request1);
        Request<SmsConfirmRequest> request2 = new Request<>();
        SmsConfirmRequest smsConfirmRequest = new SmsConfirmRequest();
        smsConfirmRequest.setAuthCode(response1.getData().getAuthCode());
        smsConfirmRequest.setRegistrationRequestUuid(response1.getData().getRegistrationRequestUuid());
        request2.setHeader(new ActionHeader("uuid2", null, "sms_confirm", "authorization", "HTTP/1.1"));
        request2.setData(smsConfirmRequest);
        Response<SmsConfirmResponse> response2 = smsConfirmRequestHandler.process(request2);

        Request<LoginRequest> request = new Request<>();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setDeviceToken(response2.getData().getDeviceToken());
        request.setHeader(new ActionHeader("uuid1", null, "login", "authorization", "HTTP/1.1"));
        request.setData(loginRequest);

        Long l = tokenHandler.getClaimsFromDeviceToken(response2.getData().getDeviceToken()).getIssuedAt().getValueInMillis();
        when(deviceService.findByProfileIdAndDeviceId(any(String.class), any(String.class))).thenReturn(deviceEntity);
        when(deviceEntity.getConfirmationTime()).thenReturn(l);

        Response<LoginResponse> response = loginRequestHandler.process(request);
        assertThat(response.getData().getToken(), is(notNullValue(String.class)));

        assertThat(response.getHeader().getUuid(), is(notNullValue(String.class)));
        assertThat(response.getHeader().getOriginUuid(), is("uuid1"));
        assertThat(response.getHeader().getType(), is("authorization"));
        assertThat(response.getHeader().getVersion(), is("HTTP/1.1"));
        assertThat(response.getHeader().getCommand(), is("login"));
    }

}
