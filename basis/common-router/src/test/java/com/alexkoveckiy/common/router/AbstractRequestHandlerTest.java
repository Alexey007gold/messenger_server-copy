package com.alexkoveckiy.common.router;

import com.alexkoveckiy.common.datamapper.DataMapper;
import com.alexkoveckiy.common.datamapper.JacksonDataMapper;
import com.alexkoveckiy.common.protocol.ActionHeader;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.router.api.handler.AbstractRequestHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by alex on 21.03.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class AbstractRequestHandlerTest {

    @InjectMocks
    private AbstractRequestHandlerMock abstractRequestHandler = Mockito.mock(AbstractRequestHandlerMock.class, Mockito.CALLS_REAL_METHODS);

    @Spy
    private DataMapper dataMapper = new JacksonDataMapper();

    private Request<?> request1;
    private Request<?> request2;

    @Test
    public void handleTest() {
        request1 = new Request<>();
        ActionHeader header1 = new ActionHeader();
        header1.setType("handler_one");
        request1.setHeader(header1);
        request2 = new Request<>();
        ActionHeader header2 = new ActionHeader();
        header2.setType("handler_two");
        request2.setHeader(header2);

        abstractRequestHandler.handle(request1);
        verify(abstractRequestHandler).process(any());

        abstractRequestHandler.handle(request2);
        verify(abstractRequestHandler, times(2)).handle(any());
    }

    private abstract class AbstractRequestHandlerMock extends AbstractRequestHandler {
        @Override
        protected Response process(Request msg) {
            return null;
        }
    }
}
