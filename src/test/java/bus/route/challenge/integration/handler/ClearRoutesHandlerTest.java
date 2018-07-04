package bus.route.challenge.integration.handler;

import bus.route.challenge.service.RouteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class ClearRoutesHandlerTest {
    @Mock
    private RouteService routeService;
    @InjectMocks
    private ClearRoutesHandler unit;

    @Test
    public void shouldClearRoutes() {
        File file = mock(File.class);

        Object actual = unit.handle(file, Collections.emptyMap());

        verifyZeroInteractions(file);
        verify(routeService).clearRoutes();
        assertEquals(file, actual);
    }
}