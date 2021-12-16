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
 * @author acee1
 *
 */
public class WebDataScraper {

//	private String f1RaceURL = "https://www.formula1.com/en/results.html/2021/races.html";

//	public webDataScraper() {
	public static void main(String[] args) {

		// Setting vars that will come as params
		String inputRace = "Emilia Romagna";
		String f1RaceURL = "https://www.formula1.com/en/results.html/2021/races.html";
		String[] toBeRemoved = { "Hamilton", "Verstappen", "Bottas", "Perez" };

		// Set WebDriver and navigate to URL
		System.setProperty("webdriver.edge.driver", "./edgedriver_win64/msedgedriver.exe");

		WebDriver driver = new EdgeDriver();

		driver.manage().window().maximize();
		
		driver.get(f1RaceURL);

		// Click past cookie button and navigate to the races page and click on relevant
		// race
		WebElement cookieButton = driver.findElement(By.id("truste-consent-button"));
		cookieButton.click();

		List<WebElement> races = driver.findElements(By.xpath("//a[@class='dark bold ArchiveLink']"));

		for (WebElement webEl : races) {
			if (webEl.getText().equals(inputRace)) {
				webEl.click();
				break;
			}
		}

		// Wait until page has loaded before reading data
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions
				.presenceOfAllElementsLocatedBy(By.xpath("//a[@class='side-nav-item-link ArchiveLink selected']")));
		
//		wait.until(ExpectedConditions.titleContains("RACE RESULT"));

		// Retrieve the result data, Clean the data and then add to List of Strings
//		System.out.println("Checking race results Lap");
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
//				System.out.println("Navigated");
				break;
			}
		}

		wait.until(ExpectedConditions.titleContains("FASTEST LAPS"));
		
//		System.out.println("Checking fastest Lap");
		List<WebElement> lapResultWebEl = driver.findElements(By.xpath("//span[@class='hide-for-mobile']"));
		List<String> lapResult = new ArrayList<>();
		for (WebElement webEl : lapResultWebEl) {
//			System.out.println(webEl.getAttribute("textContent"));
			lapResult.add(webEl.getAttribute("textContent"));
		}
//		for(String s: lapResult) {
//			System.out.println(s);
//		}

		for (String s : toBeRemoved) {
			int pos = raceResult.indexOf(s);
			int pos1 = lapResult.indexOf(s);
			raceResult.remove(pos);
			lapResult.remove(pos1);
		}
//		
		int counter=1;
		for(String s: raceResult) {
			System.out.println(counter+": "+s);
			counter++;
		}
		System.out.println(lapResult.get(0));
		// Retrieve results and then clean for the fastest lap

//		Thread.sleep(10000);
//		driver.close();

	}

//	public static void main(String[] args) {
//		WebDriver driver = new EdgeDriver();
//	}

}
