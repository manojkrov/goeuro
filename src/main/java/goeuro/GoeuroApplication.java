package goeuro;

import goeuro.util.BusRouteDataFile;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GoeuroApplication {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.err.println("Please provide a single Argument for the Bus Data File Location");
        }

        BusRouteDataFile.BUS_DATA_FILE_PATH = args[0];
        SpringApplication.run(GoeuroApplication.class, args);
    }
}
