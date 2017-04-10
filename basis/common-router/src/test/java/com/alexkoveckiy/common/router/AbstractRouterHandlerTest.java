package com.alexkoveckiy.common.router;

import com.alexkoveckiy.common.protocol.ActionHeader;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.router.api.handler.AbstractRouterHandler;
import com.alexkoveckiy.common.router.api.handler.Handler;
import com.alexkoveckiy.common.router.api.factory.HandlerFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


/**
 * Created by alex on 02.03.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class AbstractRouterHandlerTest {

    @InjectMocks
    private AbstractRouterHandlerMock abstractRouterHandler =
            Mockito.mock(AbstractRouterHandlerMock.class, Mockito.CALLS_REAL_METHODS);

    @Mock
    private HandlerFactory handlerFactory;

    private Handler handler1;
    private Handler handler2;

    private Request<?> request1;
    private Request<?> request2;

    @Before
    public void init() {
        handler1 = Mockito.mock(Handler.class);
        handler2 = Mockito.mock(Handler.class);

        request1 = new Request<>();
        ActionHeader header1 = new ActionHeader();
        header1.setType("handler_one");
        request1.setHeader(header1);
        request2 = new Request<>();
        ActionHeader header2 = new ActionHeader();
        header2.setType("handler_two");
        request2.setHeader(header2);

        when(abstractRouterHandler.getHandlerFactory()).thenReturn(handlerFactory);

        when(handlerFactory.getHandler(request1)).thenReturn(handler1);
        when(handlerFactory.getHandler(request2)).thenReturn(handler2);
    }

    @Test
    public void testHandle() {
        abstractRouterHandler.handle(request1);

        verify(handler1).handle(request1);
        verify(handler2, never()).handle(any());

        abstractRouterHandler.handle(request2);

        verify(handler1).handle(request1);
        verify(handler2).handle(request2);
    }

    private abstract class AbstractRouterHandlerMock extends AbstractRouterHandler {

        @Override
        protected abstract HandlerFactory getHandlerFactory();
    }
}
