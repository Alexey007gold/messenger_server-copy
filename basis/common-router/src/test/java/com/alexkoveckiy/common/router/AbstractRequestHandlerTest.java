package com.alexkoveckiy.common.router;

import com.alexkoveckiy.common.protocol.*;
import com.alexkoveckiy.common.router.api.handler.AbstractRequestHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by alex on 21.03.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class AbstractRequestHandlerTest {

    @InjectMocks
    private AbstractRequestHandlerMock abstractRequestHandler = Mockito.mock(AbstractRequestHandlerMock.class, Mockito.CALLS_REAL_METHODS);

    @Spy
    private MessageFactory messageFactory = Mockito.mock(MessageFactory.class);

    @Before
    public void init() {
        when(messageFactory.getRequestWithConcreteData(any(), any())).thenReturn(null);
    }

    @Test
    public void handleTest() {
        Request<?> request1 = new Request<>();
        ActionHeader header1 = new ActionHeader();
        header1.setType("handler_one");
        request1.setHeader(header1);
        Request<?> request2 = new Request<>();
        ActionHeader header2 = new ActionHeader();
        header2.setType("handler_two");
        request2.setHeader(header2);

        abstractRequestHandler.handle(request1);
        verify(abstractRequestHandler).process(any());

        abstractRequestHandler.handle(request2);
        verify(abstractRequestHandler, times(2)).handle(any());
    }

    private abstract class AbstractRequestHandlerMock extends AbstractRequestHandler<RequestData, ResponseData> {
        @Override
        protected Response process(Request msg) {
            return null;
        }
    }
}
