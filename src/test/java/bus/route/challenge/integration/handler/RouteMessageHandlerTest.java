package bus.route.challenge.integration.handler;

import bus.route.challenge.service.RouteService;
import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RouteMessageHandlerTest {
    private static final String PAYLOAD = "10 133 444 555";
    private static final String PAYLOAD_WITH_SPACES = " 10   133    444    555   ";
    private static final Integer RID = 10;
    private static final List<Integer> SIDS = ImmutableList.of(133, 444, 555);
    @Mock
    private RouteService routeService;
    @InjectMocks
    private RouteMessageHandler unit;

    @Test
    public void shouldPutRoute() {
        Object actual = unit.handle(PAYLOAD, Collections.emptyMap());

        verify(routeService).addRoute(RID, SIDS);
        assertNull(actual);
    }

    @Test
    public void shouldPutRouteOmittingWhitespaces() {
        Object actual = unit.handle(PAYLOAD_WITH_SPACES, Collections.emptyMap());

        verify(routeService).addRoute(RID, SIDS);
        assertNull(actual);
    }
}