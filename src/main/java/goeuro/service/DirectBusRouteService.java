package goeuro.service;

import goeuro.util.BusRouteDataFile;
import goeuro.util.DirectBusRouteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Main service handler for the api
 * Created by manoj on 19/09/16.
 */
@Service
@Path("direct")
public class DirectBusRouteService {

    /**
     * Autowired class for loading the data file.
     */
    private final BusRouteDataFile busRouteDataFile;

    @Autowired
    public DirectBusRouteService(BusRouteDataFile busRouteDataFile) {
        this.busRouteDataFile = busRouteDataFile;
    }

    /**
     * @param depSid departure Station Id (Mandatory Parameter)
     * @param arrSid arrival Station Id (Mandatory Parameter)
     * @return JSON representation of DirectBusResult Object.
     */
    @GET
    @Produces("application/json")
    public DirectBusRouteResult getDirectBusRoute(@QueryParam("dep_sid") @NotNull String depSid, @QueryParam("arr_sid") @NotNull String arrSid) {

        HashMap<Integer, HashSet<Integer>> busRouteData;
        try {
            busRouteData = busRouteDataFile.getBusRouteData();
        } catch (IOException e) {
            //Any error in data loading returns false
            return new DirectBusRouteResult(depSid, arrSid, false);
        }

        HashSet<Integer> depSidRouteSet = busRouteData.get(Integer.parseInt(depSid));
        HashSet<Integer> arrSidRouteSet = busRouteData.get(Integer.parseInt(arrSid));

        boolean directBusRoute = intersection(depSidRouteSet, arrSidRouteSet);

        return new DirectBusRouteResult(depSid, arrSid, directBusRoute);
    }

    /**
     * @param depSidRouteSet The set of route Id's servicing the departure station
     * @param arrSidRouteSet The set of route Id's servicing the arrival station
     * @return 'true' if any route services both the departure and arrival stations
     *          ( or if departure and arrival stations are the same ), 'false' otherwise.
     */
    private boolean intersection(HashSet<Integer> depSidRouteSet, HashSet<Integer> arrSidRouteSet) {

        if (depSidRouteSet == null || arrSidRouteSet == null) {
            return false;
        }

        for (int depSidRoute : depSidRouteSet) {
            // If atleast one route has both the station id's we return true
            if (arrSidRouteSet.contains(depSidRoute)) {
                return true;
            }
        }
        return false;
    }

}
