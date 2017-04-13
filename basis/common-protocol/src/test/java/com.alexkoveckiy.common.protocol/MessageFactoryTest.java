package com.alexkoveckiy.common.protocol;

import com.alexkoveckiy.common.datamapper.DataMapper;
import com.alexkoveckiy.common.datamapper.JacksonDataMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by alex on 12.04.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class MessageFactoryTest {

    @InjectMocks
    private MessageFactory messageFactory = new MessageFactory();

    @Spy
    private DataMapper dataMapper = new JacksonDataMapper();

    @Test
    public void getRequestWithConcreteDataTest() {
        String req = "{\"header\": {\n" +
                "\t\"type\": \"authorization\",\n" +
                "\t\"command\": \"register\",\n" +
                "\t\"uuid\": \"sssasasd\"\n" +
                "\t},\n" +
                "\"data\": {\n" +
                "\t\"phone_number\": \"+380631234567\",\n" +
                "\t\"locale_code\": \"uk_UA\",\n" +
                "\t\"device_id\": \"idididididid\"\n" +
                "\t}\n" +
                "}";
        Request<?> request = dataMapper.mapData(req, new TypeReference<Request<?>>() {});
        request.setRoutingData(new RoutingData("prof", "dev"));

        Request<TestRequestData> result = messageFactory.getRequestWithConcreteData(request, TestRequestData.class);
        assertThat(result.getData().deviceId, is("idididididid"));
        assertThat(result.getData().phoneNumber, is("+380631234567"));
        assertThat(result.getData().localeCode, is("uk_UA"));
        assertThat(result.getHeader(), is(request.getHeader()));
        assertThat(result.getRoutingData(), is(request.getRoutingData()));
    }

    private static class TestRequestData implements RequestData {

        private static final long serialVersionUID = -9036882062364690103L;

        private String phoneNumber;
        private String localeCode;
        private String deviceId;
    }
}
