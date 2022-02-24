package files;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

/**
 * Calculate the results for each race and then return to calling class.
 * 
 * @author Abdullah
 *
 */
public class ResultCalculator {
	private List<Entry<String, Integer>> driverResults = new ArrayList<>();
	private List<Entry<String, Integer>> teamResults = new ArrayList<>();

	private int[] racePoints = { 25, 18, 15, 12, 10, 8, 6, 4, 2, 1 };
	private int[] sprintPoints = { 3, 2, 1 };
	private int fastLap = 1;

	/**
	 * This constructor is used when a circuit did not have a Sprint race.
	 * 
	 * @param parRaceResults - The race results used to calculate point allocations.
	 * @param parFastLap     - The fastest lap used to calculate point allocation.
	 */
	public ResultCalculator(List<String> parRaceResults, String parFastLap) {
	}

	/**
	 * This constructor is used when a circuit had a Sprint race.
	 * 
	 * @param parRaceResults   - The race results used to calculate point
	 *                         allocations.
	 * @param parFastLap       - The fastest lap used to calculate point allocation.
	 * @param parSprintResults - The sprint race results used to calculate point
	 *                         allocations.
	 */
	public ResultCalculator(List<String> parRaceResults, String parFastLap, List<String> parSprintResults) {

	}

}
