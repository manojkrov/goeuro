package goeuro;

import goeuro.util.BusRouteDataFile;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoeuroApplicationTests {

    /**
     * Pass an empty data file to test the exception thrown while loading the file.
     *
     * @throws IOException which is asserted by the test case
     */
    @Test(expected = IOException.class)
    public void emptyDataFileExceptionTest() throws IOException {

        BusRouteDataFile.BUS_DATA_FILE_PATH = getAbsolutePathFromName("BusRouteDataTestEmptyFile");

        BusRouteDataFile dataFile = new BusRouteDataFile();
        dataFile.getBusRouteData();
    }

    /**
     * Pass an empty data file to test the exception thrown while loading the file.
     *
     * @throws IOException in case of improper data file set up
     */
    @Test
    public void sampleDataFileTest() throws IOException {

        BusRouteDataFile.BUS_DATA_FILE_PATH = getAbsolutePathFromName("BusRouteSampleDataFile");

        BusRouteDataFile dataFile = new BusRouteDataFile();
        HashMap<Integer, HashSet<Integer>> stationIdRouteMap = dataFile.getBusRouteData();

        // Non existing station Id, produces null
        Assert.assertNull(stationIdRouteMap.get(7));

        // Staion with only one route id
        Assert.assertTrue(stationIdRouteMap.get(5).contains(1));
        Assert.assertEquals(1, stationIdRouteMap.get(5).size());

    }

    private String getAbsolutePathFromName(String filename) {
        ClassLoader classLoader = getClass().getClassLoader();
        String fileName = classLoader.getResource(filename).getFile();
        File file = new File(fileName);
        return file.getAbsolutePath();
    }

}
