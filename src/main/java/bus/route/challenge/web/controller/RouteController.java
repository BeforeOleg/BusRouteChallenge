package bus.route.challenge.web.controller;

import bus.route.challenge.service.RouteService;
import bus.route.challenge.web.dto.DirectRouteData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RouteController {

    private final RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @RequestMapping("/direct")
    public DirectRouteData directLink(@RequestParam("dep_sid") Integer departure, @RequestParam("arr_sid") Integer arrival) {
        DirectRouteData directRouteData = new DirectRouteData();
        directRouteData.setDeparture(departure);
        directRouteData.setArrival(arrival);
        directRouteData.setDirectBusRoute(routeService.isDirectLink(departure, arrival));
        return directRouteData;
    }
}
