package com.alexkoveckiy.common.router;

import com.alexkoveckiy.common.protocol.ActionHeader;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.router.api.factory.HandlerFactory;
import com.alexkoveckiy.common.router.api.handler.Handler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by alex on 21.03.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class HandlerFactoryTest {

    @InjectMocks
    private HandlerFactoryMock handlerFactory = Mockito.mock(HandlerFactoryMock.class, Mockito.CALLS_REAL_METHODS);

    @Spy
    private Map<String, Handler> handlerMap = new HashMap<>();

    @Test
    public void getHandlerTest() {
        Handler handler1 = Mockito.mock(Handler.class);
        Handler handler2 = Mockito.mock(Handler.class);

        handlerMap.put("handler_one", handler1);
        handlerMap.put("handler_two", handler2);

        Request<?> request1 = new Request<>();
        ActionHeader header1 = new ActionHeader();
        header1.setType("handler_one");
        request1.setHeader(header1);
        Request<?> request2 = new Request<>();
        ActionHeader header2 = new ActionHeader();
        header2.setType("handler_two");
        request2.setHeader(header2);

        when(handlerFactory.getRouteKey(request1)).thenReturn("handler_one");
        when(handlerFactory.getRouteKey(request2)).thenReturn("handler_two");

        assertThat(handlerFactory.getHandler(request1), is(handler1));
        assertThat(handlerFactory.getHandler(request2), is(handler2));
    }

    private class HandlerFactoryMock extends HandlerFactory {
        @Override
        protected String getRouteKey(Request msg) {
            return null;
        }
    }
}
