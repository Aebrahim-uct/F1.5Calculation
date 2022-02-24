package files;

import java.time.Duration;
//import java.io.FileWriter;
//import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Scrapes the stats from the f1 website using Selenium. Removes the top two
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
	private ResultCalculator[] allRaceResults = new ResultCalculator[22];

	public WebDataScraper() {
//		Testing values
//		public static void main(String[] args) {
//		String f1RaceURL = "https://www.formula1.com/en/results.html/2021/races.html";
//		String[] toBeRemoved = { "Hamilton", "Verstappen", "Bottas", "Perez" };
//		String inputRace = "Great Britain";

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
		for (String inputRace : races) {
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
			String fastestLap = cleanResults(raceResults, fastLapResults);
			if (sprintResults.isEmpty()) {
				allRaceResults[counter] = new ResultCalculator(raceResults, fastestLap);
			} else {
				cleanSprint(sprintResults);
				allRaceResults[counter] = new ResultCalculator(raceResults, fastestLap, sprintResults);
			}

			counter++;
			driver.get(f1RaceURL);

		}

//		driver.close();

	}

	/**
	 * Cleans the results of the race and the fastest lap records by removing the
	 * excluded drivers.
	 * 
	 * @param parRaceResults    - The race results to be cleaned
	 * @param parFastLapResults - The fastest lap results to be cleaned
	 * @return - The driver with the fastest lap after cleaning the data.
	 */
	private String cleanResults(List<String> parRaceResults, List<String> parFastLapResults) {
		for (String s : toBeRemoved) {
			int pos = parRaceResults.indexOf(s);
			int pos1 = parFastLapResults.indexOf(s);
			if (pos != -1) {
				parRaceResults.remove(pos);
			}
			if (pos1 != -1) {
				parFastLapResults.remove(pos1);
			}

		}
		return (parFastLapResults.get(0));
	}

	/**
	 * Cleans the results of the Sprint race by removing the excluded drivers.
	 * 
	 * @param parSprintResults - The Sprint race results to be cleaned
	 */
	private void cleanSprint(List<String> parSprintResults) {
		for (String s : toBeRemoved) {
			int pos = parSprintResults.indexOf(s);
			if (pos != -1) {
				parSprintResults.remove(pos);
			}
		}
	}

}
