package bus.route.challenge.service.impl;

import bus.route.challenge.service.RouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.collections4.CollectionUtils.intersection;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

public class InverseRouteService implements RouteService {
    private static final Logger LOGGER = LoggerFactory.getLogger(InverseRouteService.class);
    private static final String MIN_ROUTES_SIZE_MSG = "Route must contain at least 2 stations.";
    private static final String ROUTE_MANDATORY_MSG = "Route id cannot be null";
    private final Map<Integer, Set<Integer>> stationStorage;

    public InverseRouteService() {
        this.stationStorage = new ConcurrentHashMap<>();
    }

    @Override
    public boolean isDirectLink(int departure, int arrival) {
        Set<Integer> departureRoutes = stationStorage.get(departure);
        Set<Integer> arrivalRoutes = stationStorage.get(arrival);
        return departureRoutes != null &&
                arrivalRoutes != null &&
                isNotEmpty(intersection(departureRoutes, arrivalRoutes));
    }

    @Override
    public void clearRoutes() {
        LOGGER.debug("Clearing routes");
        stationStorage.clear();
    }

    @Override
    public void addRoute(Integer rid, List<Integer> sids) {
        checkNotNull(rid, ROUTE_MANDATORY_MSG);
        checkArgument(sids.size() >= 2, MIN_ROUTES_SIZE_MSG);
        LOGGER.debug("Putting route {} with stations {}", rid, sids);

        sids.forEach(key -> stationStorage.computeIfAbsent(key, k -> new HashSet<>()).add(rid));
    }

    public Map<Integer, Set<Integer>> getStationStorage() {
        return Collections.unmodifiableMap(stationStorage);
    }
}
