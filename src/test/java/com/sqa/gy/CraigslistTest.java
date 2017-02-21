package com.sqa.gy;

import java.io.*;

import org.apache.commons.io.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.*;
import org.testng.annotations.*;

public class CraigslistTest {

	static String baseUrl = "http://sfbay.craigslist.org/";
	static WebDriver driver;
	static String resultsCount;

	@DataProvider
	public Object[][] cgData() {
		Object[][] datasets = new Object[][] {
				new Object[] { "Java Tester", "emailOfSender", "malla.isha807@gmail.com" },
				// new Object[] { "QA Automation", "emailOfSender",
				// "malla.isha807@gmail.com" },
				// new Object[] { "QA Java", "emailOfSender",
				// "malla.isha807@gmail.com" }
		};

		for (int i = 0; i < datasets.length; i++) {
			datasets[i][1] = "gytesting" + (i + 1) + "@gmail.com";
		}
		return datasets;
	}

	@BeforeMethod
	public void setUp() {
		driver = new FirefoxDriver();
	}

	public void takeSS(String name) {
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(srcFile, new File("screenshots/" + name + ".png"));
		} catch (Exception e) {
			System.out.println("SS not saved correctly.");
		}
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	@Test(dataProvider = "cgData")
	public void testCraigslist(String keywordSearch, String emailSender, String emailRecipient) {
		System.out.println("Craigslist Test: \"" + keywordSearch + "\""
		// + "\n\tSent from: " + emailSender
		// + "\n\tSending info to: " + emailRecipient
		);
		driver.get(baseUrl + "/");
		driver.findElement(By.cssSelector("a.jjj > span.txt")).click();
		driver.findElement(By.id("query")).clear();
		driver.findElement(By.id("query")).sendKeys(keywordSearch);
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		if (isElementPresent(By.className("totalcount"))) {
			resultsCount = driver.findElement(By.className("totalcount")).getText().trim();
		} else if (isElementPresent(By.className("rangeTo"))) {
			resultsCount = driver.findElement(By.className("rangeTo")).getText().trim();
		} else {
			resultsCount = "no";
		}
		System.out.println("The search for " + keywordSearch + " returned " + resultsCount + " listing(s).");
		// driver.findElement(By.cssSelector("a.result-title.hdrlnk")).click();
		takeSS(keywordSearch + " listing");
		// driver.findElement(By.linkText("email to friend")).click();
		// driver.findElement(By.id("S")).clear();
		// driver.findElement(By.id("S")).sendKeys(emailSender);
		// System.out.println(emailSender);
		// driver.findElement(By.id("D")).clear();
		// driver.findElement(By.id("D")).sendKeys(emailRecipient);
		// driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		// takeSS(keywordSearch + " sent");

	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
}
