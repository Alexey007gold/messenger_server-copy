package com.alexkoveckiy.profile.impl.handler;

import com.alexkoveckiy.common.dao.entities.ProfileSettingsEntity;
import com.alexkoveckiy.common.dao.service.ProfileSettingsService;
import com.alexkoveckiy.common.datamapper.DataMapper;
import com.alexkoveckiy.common.datamapper.JacksonDataMapper;
import com.alexkoveckiy.common.isonline.IsOnlineService;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.RoutingData;
import com.alexkoveckiy.profile.api.message.GetLastTimeOnlineRequest;
import com.alexkoveckiy.profile.api.message.GetLastTimeOnlineResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by alex on 13.04.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetLastTimeOnlineRequestHandlerTest {

    @InjectMocks
    private GetLastTimeOnlineRequestHandler handler = new GetLastTimeOnlineRequestHandler();

    @Spy
    private ProfileSettingsService profileSettingsService = Mockito.mock(ProfileSettingsService.class);

    @Spy
    private IsOnlineService isOnlineService = Mockito.mock(IsOnlineService.class);

    private DataMapper dataMapper = new JacksonDataMapper();

    @Before
    public void init() {
        when(profileSettingsService.findByProfileId(any())).thenReturn(new ProfileSettingsEntity("", true, true));
        when(isOnlineService.isOnline(any())).thenReturn(true);
        when(isOnlineService.getLastTimeOnline(any())).thenReturn(null);
    }

    @Test
    public void processTest() {
        String req = "{\n" +
                "\t\"header\": {\n" +
                "\t\t\"uuid\": \"uu\",\n" +
                "\t\t\"type\": \"profile\",\n" +
                "\t\t\"command\": \"get_last_time_online\"\n" +
                "\t},\n" +
                "\"data\": {\n" +
                "\t\"profiles\":[\n" +
                "\t\t\"0e7ee2de-7016-4c92-80fd-7402122c6ecc\",\n" +
                "\t\t\"33d4c901-5867-4e98-b500-24a70f3a287f\"\n" +
                "\t\t]\n" +
                "}" +
                "}";
        Request<GetLastTimeOnlineRequest> request = dataMapper.mapData(req, new TypeReference<Request<GetLastTimeOnlineRequest>>() {});
        request.setRoutingData(new RoutingData("prof", "dev"));

        Response<GetLastTimeOnlineResponse> response = handler.process(request);
        verify(profileSettingsService, times(2)).findByProfileId(any());

        assertThat(response.getStatus().getCode(), is(200));
        assertThat(response.getHeader().getType(), is("profile"));
        assertThat(response.getHeader().getCommand(), is("get_last_time_online"));
        assertThat(response.getHeader().getOriginUuid(), is("uu"));
        assertThat(response.getHeader().getUuid().length(), is(36));

        assertThat(response.getData().getProfiles().size(), is(2));
        assertThat(response.getData().getProfiles().get(0).isOnline(), is(true));
        assertThat(response.getData().getProfiles().get(1).isOnline(), is(true));
    }
}
