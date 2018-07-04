package bus.route.challenge.service.impl;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class DefaultRouteServiceTest {
    private static final int RID = 10;
    @InjectMocks
    private DefaultRouteService unit;

    @Test
    public void shouldBeDirectLink() {
        List<Integer> sids = ImmutableList.of(33, 66, 567, 345, 77);

        unit.addRoute(RID, sids);

        boolean actual = unit.isDirectLink(66, 345);
        assertTrue(actual);
    }

    @Test
    public void shouldBeDirectLinkIgnoreDirection() {
        List<Integer> sids = ImmutableList.of(33, 66, 567, 345, 77);

        unit.addRoute(RID, sids);

        boolean actual = unit.isDirectLink(345, 66);
        assertTrue(actual);
    }

    @Test
    public void shouldNotBeDirectLink() {
        List<Integer> sids = ImmutableList.of(33, 66, 567, 345, 77);

        unit.addRoute(RID, sids);

        boolean actual = unit.isDirectLink(66, 3);
        assertFalse(actual);
    }

    @Test
    public void shouldClearRoutes() {
        List<Integer> sids = ImmutableList.of(33, 66);

        unit.addRoute(RID, sids);

        assertEquals(sids, unit.getRoutesStorage().get(RID));
        assertEquals(1, unit.getRoutesStorage().size());

        unit.clearRoutes();

        assertEquals(0, unit.getRoutesStorage().size());
    }

    @Test
    public void shouldAddRoute() {
        List<Integer> sids = ImmutableList.of(33, 66);

        unit.addRoute(RID, sids);

        assertEquals(sids, unit.getRoutesStorage().get(RID));
        assertEquals(1, unit.getRoutesStorage().size());
    }
}