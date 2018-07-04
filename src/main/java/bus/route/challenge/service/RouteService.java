package bus.route.challenge.service;

import java.util.List;

public interface RouteService {
    boolean isDirectLink(int departure, int arrival);

    void clearRoutes();

    void addRoute(Integer rid, List<Integer> value);
}
