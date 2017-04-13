package com.alexkoveckiy.profile.impl.handler;

import com.alexkoveckiy.common.dao.entities.ContactEntity;
import com.alexkoveckiy.common.dao.entities.NumberEntity;
import com.alexkoveckiy.common.dao.entities.ProfileEntity;
import com.alexkoveckiy.common.dao.service.ContactService;
import com.alexkoveckiy.common.dao.service.ProfileService;
import com.alexkoveckiy.common.datamapper.DataMapper;
import com.alexkoveckiy.common.datamapper.JacksonDataMapper;
import com.alexkoveckiy.common.modelmapper.ModelMapperService;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.RoutingData;
import com.alexkoveckiy.profile.api.message.GetContactProfilesRequest;
import com.alexkoveckiy.profile.api.message.GetContactProfilesResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by alex on 13.04.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetContactProfilesRequestHandlerTest {

    @InjectMocks
    private GetContactProfilesRequestHandler handler = new GetContactProfilesRequestHandler();

    @Spy
    private ContactService contactService = Mockito.mock(ContactService.class);

    @Spy
    private ProfileService profileService = Mockito.mock(ProfileService.class);

    @Spy
    private ModelMapperService modelMapperService = Mockito.mock(ModelMapperService.class);

    private DataMapper dataMapper = new JacksonDataMapper();

    @Before
    public void init() {
        List<ContactEntity> entities = new ArrayList<>();
        Set<NumberEntity> numberEntities = new HashSet<>();
        numberEntities.add(new NumberEntity("id", "num"));
        entities.add(new ContactEntity("prof", "name", numberEntities));
        when(contactService.findByProfileId(any())).thenReturn(entities);
        when(profileService.findByPhoneNumber(any())).thenReturn(new ProfileEntity());
    }

    @Test
    public void processTest() {
        String req = "{\n" +
                "\t\"header\": {\n" +
                "\t\t\"uuid\": \"uu\",\n" +
                "\t\t\"type\": \"profile\",\n" +
                "\t\t\"command\": \"get_contact_profiles\"\n" +
                "\t},\n" +
                "\"data\": {}" +
                "}";
        Request<GetContactProfilesRequest> request = dataMapper.mapData(req, new TypeReference<Request<GetContactProfilesRequest>>() {});
        request.setRoutingData(new RoutingData("prof", "dev"));

        Response<GetContactProfilesResponse> response = handler.process(request);
        verify(contactService).findByProfileId(any());

        assertThat(response.getStatus().getCode(), is(200));
        assertThat(response.getHeader().getType(), is("profile"));
        assertThat(response.getHeader().getCommand(), is("get_contact_profiles"));
        assertThat(response.getHeader().getOriginUuid(), is("uu"));
        assertThat(response.getHeader().getUuid().length(), is(36));

        assertThat(response.getData().getProfiles().size(), is(1));
    }
}
