package goeuro.util;

/**
 * The Json result object class. Spring converts this object into JSON using Jackson jars.
 * Created by manoj on 19/09/16.
 */
public class DirectBusRouteResult {

    private int dep_sid;
    private int arr_sid;
    private boolean direct_bus_route;

    public DirectBusRouteResult(String dep_sid, String arr_sid, boolean direct_bus_route) {
        this.dep_sid = Integer.parseInt(dep_sid);
        this.arr_sid = Integer.parseInt(arr_sid);
        this.direct_bus_route = direct_bus_route;
    }

    public int getDep_sid() {
        return dep_sid;
    }

    public int getArr_sid() {
        return arr_sid;
    }

    public boolean isDirect_bus_route() {
        return direct_bus_route;
    }
}
