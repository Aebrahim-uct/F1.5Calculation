package my.f1_5;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Consolidate all JUnit testing within this single test file
 */
public class JUnitTests {

    // #region WebDataSceaper

    /**
     * Automated testing for the CleanResult Overloaded method1
     */
    @Test
    public void testCleanResults1() {
        List<String> raceResultList = Arrays.asList("Verstappen", "Hamilton", "Perez", "Ocon", "Bottas", "Sainz",
                "Vettel", "Alonso");
        List<String> fastLapList = Arrays.asList("Giovinazzi", "Gasly", "Mazepin", "Ocon", "Stroll", "Tsunoda",
                "Verstappen", "Schumacher", "Hamilton", "Raikonnen", "Perez", "Latifi", "Bottas", "Sainz",
                "Vettel", "Russel", "Alonso");
        String[] names = { "Hamilton", "Verstappen", "Perez", "Bottas" };

        List<List<String>> resultList = WebDataScraper.cleanResults(raceResultList, fastLapList, names);
        List<String> temp1 = Arrays.asList("Ocon", "Sainz", "Vettel", "Alonso");
        List<String> temp2 = Arrays.asList("Giovinazzi", "Gasly", "Mazepin", "Ocon", "Stroll", "Tsunoda",
                "Schumacher", "Raikonnen", "Latifi", "Sainz", "Vettel", "Russel", "Alonso");
        List<List<String>> test = new ArrayList<>();
        test.add(temp1);
        test.add(temp2);
        assertEquals(test, resultList);
    }

    /**
     * Automated testing for the CleanResult Overloaded method2
     */
    @Test
    public void testCleanResults2() {
        List<String> raceResultList = Arrays.asList("Verstappen", "Hamilton", "Perez", "Ocon", "Bottas", "Sainz",
                "Vettel", "Alonso");
        List<String> fastLapList = Arrays.asList("Giovinazzi", "Gasly", "Mazepin", "Ocon", "Stroll", "Tsunoda",
                "Verstappen", "Schumacher", "Hamilton", "Raikonnen", "Perez", "Latifi", "Bottas", "Sainz",
                "Vettel", "Russel", "Alonso");
        List<String> sprintResults = Arrays.asList("Verstappen", "Hamilton", "Perez", "Ocon", "Bottas", "Sainz",
                "Vettel", "Alonso");
        String[] names = { "Hamilton", "Verstappen", "Perez", "Bottas" };

        List<List<String>> resultList = WebDataScraper.cleanResults(raceResultList, fastLapList, sprintResults, names);
        List<String> temp1 = Arrays.asList("Ocon", "Sainz", "Vettel", "Alonso");
        List<String> temp2 = Arrays.asList("Giovinazzi", "Gasly", "Mazepin", "Ocon", "Stroll", "Tsunoda",
                "Schumacher", "Raikonnen", "Latifi", "Sainz", "Vettel", "Russel", "Alonso");
        List<String> temp3 = Arrays.asList("Ocon", "Sainz", "Vettel", "Alonso");

        List<List<String>> test = new ArrayList<>();
        test.add(temp1);
        test.add(temp2);
        test.add(temp3);
        assertEquals(test, resultList);
    }
    // #endregion

}
