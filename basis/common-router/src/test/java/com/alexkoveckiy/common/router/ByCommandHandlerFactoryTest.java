package com.alexkoveckiy.common.router;

import com.alexkoveckiy.common.protocol.ActionHeader;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.router.api.factory.ByCommandHandlerFactory;
import com.alexkoveckiy.common.router.api.handler.ByCommandRouterHandler;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by alex on 21.03.17.
 */
public class ByCommandHandlerFactoryTest {

    @InjectMocks
    private ByCommandHandlerFactoryMock handlerFactory = Mockito.mock(ByCommandHandlerFactoryMock.class, Mockito.CALLS_REAL_METHODS);

    @Test
    public void getRouteKeyTest() {
        Request<?> request1 = new Request<>();
        ActionHeader header1 = new ActionHeader();
        header1.setType("handler_one");
        request1.setHeader(header1);
        Request<?> request2 = new Request<>();
        ActionHeader header2 = new ActionHeader();
        header2.setType("handler_two");
        request2.setHeader(header2);

        assertThat(handlerFactory.getRouteKey(request1), is("handler_one"));
        assertThat(handlerFactory.getRouteKey(request2), is("handler_two"));
    }

    private class ByCommandHandlerFactoryMock extends ByCommandHandlerFactory {
        @Override
        protected String getRouteKey(Request msg) {
            return super.getRouteKey(msg);
        }
    }
}
