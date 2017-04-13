package com.alexkoveckiy.authorization.impl.handler;

import com.alexkoveckiy.authorization.api.message.SmsConfirmRequest;
import com.alexkoveckiy.authorization.api.message.SmsConfirmResponse;
import com.alexkoveckiy.authorization.impl.model.RegSession;
import com.alexkoveckiy.authorization.impl.model.RegSessions;
import com.alexkoveckiy.common.dao.entities.DeviceEntity;
import com.alexkoveckiy.common.dao.entities.ProfileEntity;
import com.alexkoveckiy.common.dao.entities.ProfileSettingsEntity;
import com.alexkoveckiy.common.dao.entities.ProfileStatusEntity;
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

import java.lang.reflect.Field;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by alex on 21.03.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class SmsConfirmRequestHandlerTest {

    @InjectMocks
    private SmsConfirmRequestHandler smsConfirmRequestHandler = Mockito.mock(SmsConfirmRequestHandler.class, Mockito.CALLS_REAL_METHODS);

    @Spy
    private RegSessions regSessions = Mockito.mock(RegSessions.class);

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

    @Test
    public void processTest() throws Exception {
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setId("");
        when(profileService.save(any(ProfileEntity.class))).thenReturn(profileEntity);
        when(deviceService.save(any(DeviceEntity.class))).thenReturn(null);
        when(profileSettingsService.save(any(ProfileSettingsEntity.class))).thenReturn(null);
        when(profileStatusService.save(any(ProfileStatusEntity.class))).thenReturn(null);

        RegSession regSession = new RegSession("num", "dev", "loc");
        Field field = RegSession.class.getDeclaredField("authCode");
        field.setAccessible(true);
        field.setInt(regSession, 5555);
        when(regSessions.takeRegSession(any())).thenReturn(regSession);


        Request<SmsConfirmRequest> request2 = new Request<>();
        SmsConfirmRequest smsConfirmRequest = new SmsConfirmRequest();
        smsConfirmRequest.setAuthCode(5555);
        smsConfirmRequest.setRegistrationRequestUuid("reqUu");
        request2.setHeader(new ActionHeader("uuid", null, "sms_confirm", "authorization", ""));
        request2.setData(smsConfirmRequest);

        Response<SmsConfirmResponse> response2 = smsConfirmRequestHandler.process(request2);
        assertThat(response2.getData().getDeviceToken(), is(notNullValue(String.class)));

        assertThat(response2.getStatus().getCode(), is(200));
        assertThat(response2.getHeader().getUuid(), is(notNullValue(String.class)));
        assertThat(response2.getHeader().getOriginUuid(), is("uuid"));
        assertThat(response2.getHeader().getType(), is("authorization"));
        assertThat(response2.getHeader().getCommand(), is("sms_confirm"));
    }
}
