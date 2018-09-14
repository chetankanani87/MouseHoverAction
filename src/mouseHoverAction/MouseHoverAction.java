package mouseHoverAction;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import lib.BrowserDriverUtility;
import lib.ExtentReportUtility;
import lib.ScreenshotUtility;

public class MouseHoverAction {
	WebDriver dr = BrowserDriverUtility.InvokeBrowser("webdriver.chrome.driver",
			"C:\\Chetan\\SeleniumSuite\\WebDrivers\\chromedriver.exe",
			"http://seleniumpractise.blogspot.com//2016//08//how-to-perform-mouse-hover-in-selenium.html");
	ExtentReports report = ExtentReportUtility.InvokeExtentReport();;
	ExtentTest logger = report.createTest("Mouse Hover Action");
	String path;
	
	@BeforeTest
	public void InvokeBrowser() {
		try {
			path = ScreenshotUtility.CaptureScreenshot(dr, "1_MainPage");
			logger.pass("Main Page - Screenshot taken.", MediaEntityBuilder.createScreenCaptureFromPath(path).build());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void MouseHover() {
		try {
			//Capture the element on which we have to perform mouse hover action
			WebElement ele = dr.findElement(By.xpath("//button[text()='Automation Tools']"));
			
			//Mouse hover action to 'ele' element
			Actions act = new Actions (dr);
			act.moveToElement(ele).perform();
			
			path = ScreenshotUtility.CaptureScreenshot(dr, "2_AfterMouseHover");
			logger.pass("After Mouse Hover - Screenshot taken.", MediaEntityBuilder.createScreenCaptureFromPath(path).build());
			
			List<WebElement> links = dr.findElements(By.xpath("//div[@class='dropdown-content']//a"));
			int total_count = links.size();
			
			for (int i=0; i<total_count; i++) {
				
				ele = links.get(i);
				String text = ele.getAttribute("innerHTML");
				boolean status = ele.isEnabled();
				System.out.println("Links name is: " + text + " and the link status is " + status);
				
				if (text.equalsIgnoreCase("Appium")) {
					ele.click();
					path = ScreenshotUtility.CaptureScreenshot(dr, "3_AppiumButtonPage");
					logger.pass("Appium Button Clicked - Screenshot taken.", MediaEntityBuilder.createScreenCaptureFromPath(path).build());
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@AfterTest
	public void tearDown() {
		report.flush();
		dr.close();
	}
}
