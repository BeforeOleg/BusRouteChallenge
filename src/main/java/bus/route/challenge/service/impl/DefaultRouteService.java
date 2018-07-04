package bus.route.challenge.service.impl;

import bus.route.challenge.service.RouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class DefaultRouteService implements RouteService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRouteService.class);
    private static final String MIN_ROUTES_SIZE_MSG = "Route must contain at least 2 stations.";
    private static final String ROUNT_MANDATORY_MSG = "Route id cannot be null";
    private final Map<Integer, List<Integer>> routesStorage;

    public DefaultRouteService() {
        this.routesStorage = new ConcurrentHashMap<>();
    }

    @Override
    public boolean isDirectLink(int departure, int arrival) {
        return routesStorage.entrySet().parallelStream().anyMatch(route -> isDirectLinkRoute(departure, arrival, route));
    }

    @Override
    public void clearRoutes() {
        LOGGER.debug("Clearing routes");
        routesStorage.clear();
    }

    @Override
    public void addRoute(Integer rid, List<Integer> sids) {
        checkNotNull(rid, ROUNT_MANDATORY_MSG);
        checkArgument(sids.size() > 2, MIN_ROUTES_SIZE_MSG);
        LOGGER.debug("Putting route {} with stations {}", rid, sids);
        routesStorage.put(rid, sids);
    }

    private boolean isDirectLinkRoute(int departure, int arrival, Map.Entry<Integer, List<Integer>> route) {
        List<Integer> stationIds = route.getValue();
        return stationIds.contains(departure) && stationIds.contains(arrival);
    }
}
