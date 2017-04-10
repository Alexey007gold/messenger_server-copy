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
public class AuthorizationTest {

    @InjectMocks
    private RegisterRequestHandler registerRequestHandler = Mockito.mock(RegisterRequestHandler.class, Mockito.CALLS_REAL_METHODS);

    @InjectMocks
    private SmsConfirmRequestHandler smsConfirmRequestHandler = Mockito.mock(SmsConfirmRequestHandler.class, Mockito.CALLS_REAL_METHODS);

    @InjectMocks
    private LoginRequestHandler loginRequestHandler;

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

    private Response<RegisterResponse> registerResponse;
    private Response<SmsConfirmResponse> smsConfirmResponse;
    private Response<LoginResponse> loginResponse;

    @Test
    public void test() throws Exception {
        registerProcessTest();
        smsConfirmProcessTest();
        loginProcessTest();
    }

    public void registerProcessTest() {
        Request<RegisterRequest> request = new Request<>();
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPhoneNumber("+380631234567");
        registerRequest.setLocaleCode("uk_UA");
        registerRequest.setDeviceID("dev_id");
        request.setHeader(new ActionHeader("uuid1", null, "register", "authorization", "HTTP/1.1"));
        request.setData(registerRequest);

        registerResponse = registerRequestHandler.process(request);
        assertThat(registerResponse.getData().getRegistrationTimeoutSec(), is(notNullValue(Integer.class)));
        assertThat(registerResponse.getData().getRegistrationRequestUuid(), is(notNullValue(String.class)));
        assertThat(registerResponse.getData().getAuthCode(), is(notNullValue(Integer.class)));

        assertThat(registerResponse.getHeader().getUuid(), is(notNullValue(String.class)));
        assertThat(registerResponse.getHeader().getOriginUuid(), is("uuid1"));
        assertThat(registerResponse.getHeader().getType(), is("authorization"));
        assertThat(registerResponse.getHeader().getVersion(), is("HTTP/1.1"));
        assertThat(registerResponse.getHeader().getCommand(), is("register"));
    }

    public void smsConfirmProcessTest() throws Exception {
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setId("");
        when(profileService.save(any(ProfileEntity.class))).thenReturn(profileEntity);

        Request<SmsConfirmRequest> request2 = new Request<>();
        SmsConfirmRequest smsConfirmRequest = new SmsConfirmRequest();
        smsConfirmRequest.setAuthCode(registerResponse.getData().getAuthCode());
        smsConfirmRequest.setRegistrationRequestUuid(registerResponse.getData().getRegistrationRequestUuid());
        request2.setHeader(new ActionHeader("uuid2", null, "sms_confirm", "authorization", "HTTP/1.1"));
        request2.setData(smsConfirmRequest);

        smsConfirmResponse = smsConfirmRequestHandler.process(request2);
        assertThat(smsConfirmResponse.getData().getDeviceToken(), is(notNullValue(String.class)));

        assertThat(smsConfirmResponse.getHeader().getUuid(), is(notNullValue(String.class)));
        assertThat(smsConfirmResponse.getHeader().getOriginUuid(), is("uuid2"));
        assertThat(smsConfirmResponse.getHeader().getType(), is("authorization"));
        assertThat(smsConfirmResponse.getHeader().getVersion(), is("HTTP/1.1"));
        assertThat(smsConfirmResponse.getHeader().getCommand(), is("sms_confirm"));
    }

    public void loginProcessTest() throws Exception {
        Request<LoginRequest> request = new Request<>();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setDeviceToken(smsConfirmResponse.getData().getDeviceToken());
        request.setHeader(new ActionHeader("uuid1", null, "login", "authorization", "HTTP/1.1"));
        request.setData(loginRequest);

        Long l = tokenHandler.getClaimsFromDeviceToken(smsConfirmResponse.getData().getDeviceToken()).getIssuedAt().getValueInMillis();
        when(deviceService.findByProfileIdAndDeviceId(any(String.class), any(String.class))).thenReturn(deviceEntity);
        when(deviceEntity.getConfirmationTime()).thenReturn(l);

        loginResponse = loginRequestHandler.process(request);
        assertThat(loginResponse.getData().getToken(), is(notNullValue(String.class)));

        assertThat(loginResponse.getHeader().getUuid(), is(notNullValue(String.class)));
        assertThat(loginResponse.getHeader().getOriginUuid(), is("uuid1"));
        assertThat(loginResponse.getHeader().getType(), is("authorization"));
        assertThat(loginResponse.getHeader().getVersion(), is("HTTP/1.1"));
        assertThat(loginResponse.getHeader().getCommand(), is("login"));
    }

}
