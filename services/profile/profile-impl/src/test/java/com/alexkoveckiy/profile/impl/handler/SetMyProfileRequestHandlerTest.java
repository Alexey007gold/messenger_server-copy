package com.alexkoveckiy.profile.impl.handler;

import com.alexkoveckiy.common.dao.entities.ProfileEntity;
import com.alexkoveckiy.common.dao.service.ProfileService;
import com.alexkoveckiy.common.datamapper.DataMapper;
import com.alexkoveckiy.common.datamapper.JacksonDataMapper;
import com.alexkoveckiy.common.modelmapper.ModelMapperService;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.RoutingData;
import com.alexkoveckiy.profile.api.message.SetMyProfileRequest;
import com.alexkoveckiy.profile.api.message.SetMyProfileResponse;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by alex on 12.04.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class SetMyProfileRequestHandlerTest {

    @InjectMocks
    private SetMyProfileRequestHandler handler = new SetMyProfileRequestHandler();

    @Spy
    private ProfileService profileService = Mockito.mock(ProfileService.class);

    @Spy
    private ModelMapperService modelMapperService = Mockito.mock(ModelMapperService.class);

    private DataMapper dataMapper = new JacksonDataMapper();

    @Before
    public void init() {
        when(profileService.findOne(any())).thenReturn(new ProfileEntity());
        when(profileService.save(any(ProfileEntity.class))).thenReturn(null);
        when(modelMapperService.map(any(), any())).thenReturn(null);
    }

    @Test
    public void processTest() {
        String req = "{\n" +
                "\t\"header\": {\n" +
                "\t\t\"uuid\": \"uu\",\n" +
                "\t\t\"type\": \"profile\",\n" +
                "\t\t\"command\": \"set_my_profile\"\n" +
                "\t},\n" +
                "\t\"data\": {\"profile\":{\n" +
                "\t\t\t\"phone_number\":\"+380631234567\",\n" +
                "\t\t\t\"create_date_time\":1490182589477,\n" +
                "\t\t\t\"update_date_time\":1490182589477,\n" +
                "\t\t\t\"avatar_uri\":\"\",\n" +
                "\t\t\t\"name\":\"Alex\",\n" +
                "\t\t\t\"status\":\"Nice status!\"}}\n" +
                "}";
        Request<SetMyProfileRequest> request = dataMapper.mapData(req, new TypeReference<Request<SetMyProfileRequest>>() {});
        request.setRoutingData(new RoutingData("prof", "dev"));

        Response<SetMyProfileResponse> response = handler.process(request);
        verify(profileService).save(any(ProfileEntity.class));

        assertThat(response.getStatus().getCode(), is(200));
        assertThat(response.getHeader().getType(), is("profile"));
        assertThat(response.getHeader().getCommand(), is("set_my_profile"));
        assertThat(response.getHeader().getOriginUuid(), is("uu"));
        assertThat(response.getHeader().getUuid().length(), is(36));
    }
}
