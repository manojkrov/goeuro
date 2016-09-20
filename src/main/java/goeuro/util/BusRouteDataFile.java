package goeuro.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;

/**
 * The class doing the most of the heavylifting in this application.
 * Reads the input file , parses it, checks for errors
 * and creates a {@link HashMap} of 'station id' and {@link HashSet} of 'route ids' the station is serviced by.
 * <p>
 * Created by manoj on 19/09/16.
 */
@Component
public class BusRouteDataFile {

    private static final Logger logger = LoggerFactory.getLogger(BusRouteDataFile.class);
    public static String BUS_DATA_FILE_PATH;
    @Value("${DATA_DELIMITER}")
    private String DELIMITER;


    @Value("${MIN_ROUTE_ARGUMENTS}")
    private int MIN_ROUTE_ARGUMENTS;

    private boolean isBusRouteDataLoaded = false;


    private HashMap<Integer, HashSet<Integer>> busRouteData = new HashMap<>();


    public BusRouteDataFile() {

    }

    public HashMap<Integer, HashSet<Integer>> getBusRouteData() throws IOException {
        if (!isBusRouteDataLoaded) {
            if (!loadBusRouteData()) {
                logger.error("Error loading the input Data");
                throw new IOException("Error loading Bus Data");

            }
        }
        return busRouteData;
    }

    private boolean loadBusRouteData() {

        try (BufferedReader br = Files.newBufferedReader(Paths.get(BUS_DATA_FILE_PATH))) {

            // Read the number of routes in the first line. No validation checks performed on the number of routes provided.

            String line = br.readLine();

            if (line == null) {
                logger.error("Empty data in the file " + BUS_DATA_FILE_PATH);
                throw new IllegalArgumentException("No data in the file " + BUS_DATA_FILE_PATH);
            }

            while ((line = br.readLine()) != null) {
                System.out.println(" Delimiter " + DELIMITER);
                String[] routeInfo = line.split(DELIMITER);

                if (routeInfo.length < MIN_ROUTE_ARGUMENTS) {
                    logger.error(" Illegal Data in the input. Each route should have atleast " + MIN_ROUTE_ARGUMENTS + " inputs. " +
                            "Actual data " + line);
                    throw new IllegalArgumentException("Each Route should contain atleast " + MIN_ROUTE_ARGUMENTS + " integers.");
                } else {

                    //Route Id , the first integer in the line.
                    int routeNumber = Integer.parseInt(routeInfo[0]);

                    // Parse the station id's in this particular route.
                    for (int i = 1; i < routeInfo.length; i++) {
                        populateBusRouteData(routeNumber, routeInfo[i]);
                    }
                }
            }

            isBusRouteDataLoaded = true;
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            isBusRouteDataLoaded = false;
        }

        return isBusRouteDataLoaded;
    }

    /**
     * Adds the routeNumber to the {@link HashSet} of routeNumbers for the stationId Key in the busRouteData map.
     *
     * @param routeNumber     the current route number
     * @param stationIdString the station id serviced by the routeNumber
     * @throws NumberFormatException if the stationIdString is not a valid Integer
     */
    private void populateBusRouteData(int routeNumber, String stationIdString) throws NumberFormatException {

        int stationId = Integer.parseInt(stationIdString);

        HashSet<Integer> routeIdSet;

        routeIdSet = busRouteData.get(stationId);

        if (routeIdSet == null) {
            routeIdSet = new HashSet<>();
            routeIdSet.add(routeNumber);
            busRouteData.put(stationId, routeIdSet);
        } else {
            routeIdSet.add(routeNumber);
        }
    }

    public void setDELIMITER(String DELIMITER) {
        this.DELIMITER = DELIMITER;
    }

    public void setMIN_ROUTE_ARGUMENTS(int MIN_ROUTE_ARGUMENTS) {
        this.MIN_ROUTE_ARGUMENTS = MIN_ROUTE_ARGUMENTS;
    }
}
