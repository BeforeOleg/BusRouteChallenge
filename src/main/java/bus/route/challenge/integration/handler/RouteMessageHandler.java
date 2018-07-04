package bus.route.challenge.integration.handler;

import bus.route.challenge.service.RouteService;
import com.google.common.base.Verify;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.file.FileHeaders;
import org.springframework.integration.handler.GenericHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class RouteMessageHandler implements GenericHandler<String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteMessageHandler.class);
    private static final int MIN_ROUTE_LINE_SIZE = 3;
    private static final String MIN_ROUTE_LINE_SIZE_ERR_MSG = "Route line must contain at least route id and 2 stations.";

    private RouteService routeService;

    public RouteMessageHandler(RouteService routeService) {
        this.routeService = routeService;
    }

    @Override
    public Object handle(String payload, Map<String, Object> headers) {
        LOGGER.debug("Parsing payload of file [{}]", headers.get(FileHeaders.RELATIVE_PATH));
        Pair<Integer, List<Integer>> route = getRoute(payload);
        routeService.addRoute(route.getKey(), route.getValue());
        return null;
    }

    private Pair<Integer, List<Integer>> getRoute(String payload) {
        String trimmedPayload = payload.trim();
        String[] ids = StringUtils.split(trimmedPayload);
        Verify.verify(ids.length >= MIN_ROUTE_LINE_SIZE, MIN_ROUTE_LINE_SIZE_ERR_MSG);
        return Pair.of(getRouteId(ids), getStationIds(ids));
    }

    private List<Integer> getStationIds(String[] words) {
        return Arrays.stream(words).skip(1)
                .map(String::trim)
                .map(Integer::valueOf)
                .collect(ImmutableList.toImmutableList());
    }

    private Integer getRouteId(String[] ids) {
        return Integer.valueOf(ids[0].trim());
    }
}
