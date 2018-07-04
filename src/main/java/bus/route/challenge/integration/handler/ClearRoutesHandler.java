package bus.route.challenge.integration.handler;

import bus.route.challenge.service.RouteService;
import org.springframework.integration.handler.GenericHandler;

import java.io.File;
import java.util.Map;

public class ClearRoutesHandler implements GenericHandler<File> {
    private RouteService routeService;

    public ClearRoutesHandler(RouteService routeService) {
        this.routeService = routeService;
    }

    @Override
    public Object handle(File file, Map<String, Object> map) {
        routeService.clearRoutes();
        return file;
    }
}
