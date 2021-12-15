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
public class webDataScraper {

//	private String f1RaceURL = "https://www.formula1.com/en/results.html/2021/races.html";

//	public webDataScraper() {
	public static void main(String[] args) throws InterruptedException {

		String inputRace = "Bahrain";
		String f1RaceURL = "https://www.formula1.com/en/results.html/2021/races.html";
		String[] toBeRemoved = { "Hamilton", "Verstappen", "Bottas", "Perez" };

		System.setProperty("webdriver.edge.driver", "./edgedriver_win64/msedgedriver.exe");

		WebDriver driver = new EdgeDriver();

		driver.get(f1RaceURL);

		WebElement cookieButton = driver.findElement(By.id("truste-consent-button"));
		cookieButton.click();

		List<WebElement> races = driver.findElements(By.xpath("//a[@class='dark bold ArchiveLink']"));

		for (WebElement webEl : races) {
			if (webEl.getText().equals(inputRace)) {
				webEl.click();
				break;
			}
		}

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions
				.presenceOfAllElementsLocatedBy(By.xpath("//a[@class='side-nav-item-link ArchiveLink selected']")));

		List<WebElement> raceResultWebEl = driver.findElements(By.xpath("//span[@class='hide-for-mobile']"));

		
		List<String> raceResult = new ArrayList<String>();
		for (WebElement webEl : raceResultWebEl) {
			raceResult.add(webEl.getAttribute("textContent"));
		}
		
		for (String s : toBeRemoved) {
			int pos=raceResult.indexOf(s);
			raceResult.remove(pos);
		}
		for (String s : raceResult) {
			System.out.println(s);
		}
//		Thread.sleep(10000);
//		driver.close();

	}

//	public static void main(String[] args) {
//		WebDriver driver = new EdgeDriver();
//	}

}
