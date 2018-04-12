package com.alexkoveckiy.common.router;

import com.alexkoveckiy.common.router.api.factory.ByCommandHandlerFactory;
import com.alexkoveckiy.common.router.api.factory.HandlerFactory;
import com.alexkoveckiy.common.router.api.handler.ByCommandRouterHandler;
import com.alexkoveckiy.common.router.impl.FirstByTypeRouter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by alex on 02.03.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class FirstByTypeRouterTest {

    @InjectMocks
    private FirstByTypeRouterMock firstRouter = Mockito.mock(FirstByTypeRouterMock.class, Mockito.CALLS_REAL_METHODS);

    @Spy
    private ByCommandHandlerFactory handlerFactory = Mockito.mock(ByCommandHandlerFactory.class);


    @Test
    public void testGetName() {
        assertThat(firstRouter.getName(), is("first_router"));
    }

    @Test
    public void testGetHandlerFactory() {
        assertThat(firstRouter.getHandlerFactory(), is(handlerFactory));
    }

    private class FirstByTypeRouterMock extends FirstByTypeRouter {
        @Override
        protected HandlerFactory<ByCommandRouterHandler> getHandlerFactory() {
            return super.getHandlerFactory();
        }
    }
}
