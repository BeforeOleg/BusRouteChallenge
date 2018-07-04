package bus.route.challenge.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DirectRouteData {

    @JsonProperty("dep_sid")
    private Integer departure;
    @JsonProperty("arr_sid")
    private Integer arrival;
    @JsonProperty("direct_bus_route")
    private boolean directBusRoute;

    public Integer getDeparture() {
        return departure;
    }

    public void setDeparture(Integer departure) {
        this.departure = departure;
    }

    public Integer getArrival() {
        return arrival;
    }

    public void setArrival(Integer arrival) {
        this.arrival = arrival;
    }

    public boolean isDirectBusRoute() {
        return directBusRoute;
    }

    public void setDirectBusRoute(boolean directBusRoute) {
        this.directBusRoute = directBusRoute;
    }
}
