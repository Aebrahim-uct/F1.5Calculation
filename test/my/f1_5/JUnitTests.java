package my.f1_5;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Consolidate all JUnit testing within this single test file
 */
public class JUnitTests {

    @Test
    public void testCleanSprint() {
        List<String> sprintResults = Arrays.asList("Verstappen","Hamilton","Perez","Ocon","Bottas","Sainz","Vettel","Alonso")
        
        String[] names={"Hamilton","Verstappen","Perez","Bottas"};
        List<String> resultList=WebDataScraper.cleanSprint(sprintResults, names);
        List<String> expectedList;
        assertEquals(expectedList,resultList );
    }
}
