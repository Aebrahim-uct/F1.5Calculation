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
	private ResultCalculator[] allRaceResults=new ResultCalculator[22];
	
	public WebDataScraper() {

		// Set WebDriver and navigate to URL
		System.setProperty("webdriver.edge.driver", "./edgedriver_win64/msedgedriver.exe");

		WebDriver driver = new EdgeDriver();

		driver.manage().window().maximize();

		driver.get(f1RaceURL);

		// Click past cookie button and navigate to the races page and click on relevant
		// race
		WebElement cookieButton = driver.findElement(By.id("truste-consent-button"));
		cookieButton.click();

		for (String inputRace : races) {
			List<WebElement> webElementRaces = driver.findElements(By.xpath("//a[@class='dark bold ArchiveLink']"));

			for (WebElement webEl : webElementRaces) {
				if (webEl.getText().equals(inputRace)) {
					webEl.click();
					break;
				}
			}

			// Wait until page has loaded before reading data
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			wait.until(ExpectedConditions
					.presenceOfAllElementsLocatedBy(By.xpath("//a[@class='side-nav-item-link ArchiveLink selected']")));

			// Retrieve the result data, Clean the data and then add to List of Strings
			List<WebElement> raceResultWebEl = driver.findElements(By.xpath("//span[@class='hide-for-mobile']"));
			List<String> raceResult = new ArrayList<String>();
			for (WebElement webEl : raceResultWebEl) {
				raceResult.add(webEl.getAttribute("textContent"));
			}

			// Navigate to Fastest lap page
			List<WebElement> navTabs = driver.findElements(By.xpath("//a[@class='side-nav-item-link ArchiveLink ']"));
			for (WebElement webEl : navTabs) {
				if (webEl.getText().equals("FASTEST LAPS")) {
					webEl.click();
					break;
				}
			}

			wait.until(ExpectedConditions.titleContains("FASTEST LAPS"));

			List<WebElement> lapResultWebEl = driver.findElements(By.xpath("//span[@class='hide-for-mobile']"));
			List<String> lapResult = new ArrayList<>();
			for (WebElement webEl : lapResultWebEl) {
				lapResult.add(webEl.getAttribute("textContent"));
			}

			for (String s : toBeRemoved) {
				int pos = raceResult.indexOf(s);
				int pos1 = lapResult.indexOf(s);
				raceResult.remove(pos);
				lapResult.remove(pos1);
			}

			driver.get(f1RaceURL);
			
		}

		// Retrieve results and then clean for the fastest lap

//		Thread.sleep(10000);
//		driver.close();

	}

//	public static void main(String[] args) {
//		WebDriver driver = new EdgeDriver();
//	}

}
