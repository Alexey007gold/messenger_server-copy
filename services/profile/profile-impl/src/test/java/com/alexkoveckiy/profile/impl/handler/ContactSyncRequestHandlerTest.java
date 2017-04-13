package com.alexkoveckiy.profile.impl.handler;

import com.alexkoveckiy.common.dao.entities.ContactEntity;
import com.alexkoveckiy.common.dao.service.ContactService;
import com.alexkoveckiy.common.datamapper.DataMapper;
import com.alexkoveckiy.common.datamapper.JacksonDataMapper;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.RoutingData;
import com.alexkoveckiy.profile.api.message.ContactSyncRequest;
import com.alexkoveckiy.profile.api.message.ContactSyncResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by alex on 13.04.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class ContactSyncRequestHandlerTest {

    @InjectMocks
    private ContactSyncRequestHandler handler = new ContactSyncRequestHandler();

    @Spy
    private ContactService contactService = Mockito.mock(ContactService.class);

    private DataMapper dataMapper = new JacksonDataMapper();

    @Test
    public void processTest() {
        String req = "{\n" +
                "\t\"header\": {\n" +
                "\t\t\"uuid\": \"uu\",\n" +
                "\t\t\"type\": \"profile\",\n" +
                "\t\t\"command\": \"contacts_sync\"\n" +
                "\t},\n" +
                "\"data\": {\n" +
                "\t\t\"added_contacts\": [\n" +
                "\t\t\t{\"name\": \"Joe Doe\", \"numbers\": [\"+380123456789\",\"+380123456739\",\"+380523456789\"]},\n" +
                "\t\t\t{\"name\": \"Bill Gates\", \"numbers\": [\"+380987654321\",\"+380123453789\"]},\n" +
                "\t\t\t{\"name\": \"Stieve Jobs\", \"numbers\": [\"+380123459876\",\"+380123456780\",\"+380123494789\"]}\n" +
                "\t\t],\n" +
                "\t\t\"removed_contacts\": [{\"name\": \"Stieve Jobs\", \"numbers\": [\"+380123459876\"]}]\n" +
                "\t}" +
                "}";
        Request<ContactSyncRequest> request = dataMapper.mapData(req, new TypeReference<Request<ContactSyncRequest>>() {});
        request.setRoutingData(new RoutingData("prof", "dev"));

        Response<ContactSyncResponse> response = handler.process(request);
        verify(contactService, times(3)).save(any(ContactEntity.class));

        assertThat(response.getStatus().getCode(), is(200));
        assertThat(response.getHeader().getType(), is("profile"));
        assertThat(response.getHeader().getCommand(), is("contacts_sync"));
        assertThat(response.getHeader().getOriginUuid(), is("uu"));
        assertThat(response.getHeader().getUuid().length(), is(36));
    }
}
