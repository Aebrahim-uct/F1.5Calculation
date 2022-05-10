package my.f1_5;

import java.time.Duration;
//import java.io.FileWriter;
//import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
// import java.util.stream.Collector;
import java.util.stream.Collectors;
// import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Scrapes the stats from the f1 website using Selenium. Removes the top
 * teams and their drivers.
 * 
 * @author Abdullah
 *
 */
public class WebDataScraper {

	private String f1RaceURL = "https://www.formula1.com/en/results.html/2021/races.html";
	private String[] races = { "Bahrain", "Emilia Romagna", "Portugal", "Spain", "Monaco", "Azerbaijan", "France",
			"Styria", "Austria", "Great Britain", "Hungary", "Belgium", "Netherlands", "Italy", "Russia", "Turkey",
			"United States", "Mexico", "Brazil", "Qatar", "Saudi Arabia", "Abu Dhabi" };
	private String[] toBeRemoved = { "Hamilton", "Verstappen", "Bottas", "Perez" };

	// public WebDataScraper() {}

	// Testing values
	public static void main(String[] args) {
		String f1RaceURL = "https://www.formula1.com/en/results.html/2021/races.html";
		String[] toBeRemoved = { "Hamilton", "Verstappen", "Bottas", "Perez" };
		String inputRace = "Great Britain";

		// Set WebDriver and navigate to URL
		System.setProperty("webdriver.edge.driver", "./edgedriver_win64/msedgedriver.exe");
		WebDriver driver = new EdgeDriver();
		driver.manage().window().maximize();
		driver.get(f1RaceURL);

		// Click past cookie button and navigate to the races page and click on relevant
		// race
		WebElement cookieButton = driver.findElement(By.id("truste-consent-button"));
		cookieButton.click();

		// Loop through each race
		int counter = 0;
		// for (String inputRace : races) {
		WebElement raceLink = driver.findElement(By.linkText(inputRace));
		raceLink.click();

		// Wait until page has loaded before reading data
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions
				.presenceOfAllElementsLocatedBy(By.xpath("//a[@class='side-nav-item-link ArchiveLink selected']")));

		// Retrieve the result data, Clean the data and then add to List of Strings
		List<WebElement> raceResultWebEl = driver.findElements(By.xpath("//span[@class='hide-for-mobile']"));
		List<String> raceResults = new ArrayList<String>();
		for (WebElement webEl : raceResultWebEl) {
			raceResults.add(webEl.getAttribute("textContent"));
		}

		// Navigate to Fastest lap page
		WebElement fastestLapLink = driver.findElement(By.linkText("FASTEST LAPS"));
		fastestLapLink.click();

		// Wait until page has loaded priot to reading data
		// Retrieve the result data, Clean the data and then add to List of Strings
		wait.until(ExpectedConditions.titleContains("FASTEST LAPS"));

		List<WebElement> fastlapResultWebEl = driver.findElements(By.xpath("//span[@class='hide-for-mobile']"));
		List<String> fastLapResults = new ArrayList<>();
		for (WebElement webEl : fastlapResultWebEl) {
			fastLapResults.add(webEl.getAttribute("textContent"));
		}

		// Check if there is a Sprint Race for this circuit and handle the errors and
		// points allocation
		List<String> sprintResults = new ArrayList<>();
		try {
			WebElement sprintResultsLink = driver.findElement(By.linkText("SPRINT"));
			sprintResultsLink.click();

			wait.until(ExpectedConditions.titleContains("SPRINT"));
			List<WebElement> sprintResultsWebEls = driver
					.findElements(By.xpath("//span[@class='hide-for-mobile']"));
			for (WebElement webEl : sprintResultsWebEls) {
				sprintResults.add(webEl.getAttribute("textContent"));
			}
		} catch (org.openqa.selenium.NoSuchElementException e) {
		}

		// Clean results and create objects to assign to array
		if (sprintResults.isEmpty()) {
			List<List<String>> dataList = cleanResults(raceResults, fastLapResults, toBeRemoved);
			// allRaceResults[counter] = new ResultCalculator(raceResults, fastestLap);
		} else {
			List<List<String>> dataList = cleanResults(raceResults, fastLapResults, sprintResults, toBeRemoved);
			// cleanSprint(sprintResults);
			// allRaceResults[counter] = new ResultCalculator(raceResults, fastestLap,
			// sprintResults);
		}

		counter++;
		driver.get(f1RaceURL);

		// }

		driver.close();

	}

	/**
	 * Cleans the results of the race and the fastest lap records by removing the
	 * excluded drivers.
	 * 
	 * @param parRaceResults    - The race results to be cleaned
	 * @param parFastLapResults - The fastest lap results to be cleaned
	 * @param parTobeRemoved    - The drivers to be removed from the results
	 * @return - All the results for the respective race parts at a track
	 */
	public static List<List<String>> cleanResults(List<String> parRaceResults, List<String> parFastLapResults,
			String[] parTobeRemoved) {
		for (String s : parTobeRemoved) {
			parRaceResults = parRaceResults.stream().filter(string -> !string.equals(s)).collect(Collectors.toList());
			parFastLapResults = parFastLapResults.stream().filter(string -> !string.equals(s))
					.collect(Collectors.toList());
		}
		List<List<String>> temp = new ArrayList<>();
		temp.add(parRaceResults);
		temp.add(parFastLapResults);
		return temp;
	}

	/**
	 * Cleans the results of the race, the sprint race, and the fastest lap records
	 * by removing the excluded drivers.
	 * 
	 * @param parRaceResults    - The race results to be cleaned
	 * @param parFastLapResults - The fastest lap results to be cleaned
	 * @param parSprintResults  - The sprint results to be cleaned
	 * @param parTobeRemoved    - The drivers to be removed from the results
	 * @return - All the results for the respective race parts at a track
	 */
	public static List<List<String>> cleanResults(List<String> parRaceResults, List<String> parFastLapResults,
			List<String> parSprintResults, String[] parTobeRemoved) {
		List<List<String>> temp = cleanResults(parRaceResults, parFastLapResults, parTobeRemoved);
		for (String s : parTobeRemoved) {
			parSprintResults = parSprintResults.stream().filter(string -> !string.equals(s))
					.collect(Collectors.toList());
		}
		temp.add(parSprintResults);
		return temp;
	}
}
