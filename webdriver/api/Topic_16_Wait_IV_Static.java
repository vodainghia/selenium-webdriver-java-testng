package api;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_16_Wait_IV_Static {
	WebDriver driver;
	WebDriverWait explicit;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	By startButton = By.cssSelector("#start>button");
	By loadingIcon = By.cssSelector("div#loading");
	By helloWorldText = By.xpath("//h4[text()='Hello World!']");
	
	
	@BeforeClass
	public void beforeClass() {
		if (osName.toLowerCase().contains("mac os")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
			System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver.exe");
			System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver.exe");
		}
		
		driver = new ChromeDriver();
		//driver = new FirefoxDriver();
		driver.manage().window().maximize();
		explicit = new WebDriverWait(driver, 10);
	}
	
	@Test
	public void TC_01_Less() {
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
		
		driver.findElement(startButton).click();
		
		//ít hơn thời gian để 1 step tiếp tục đc ready
		//Thiếu thời gian -> Fail
		sleepInSecond(3);
		Assert.assertTrue(driver.findElement(helloWorldText).isDisplayed());
	}
	
	@Test
	public void TC_02_Enough() {
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
		driver.findElement(startButton).click();
		
		//Đủ thời gian
		//Pass
		sleepInSecond(6);
		Assert.assertTrue(driver.findElement(helloWorldText).isDisplayed());
	}
	
	@Test
	public void TC_03_More() {
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
		driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
		
		driver.findElement(startButton).click();
		
		//Dư thời gian
		//Pass nhưng lãng phí time
		sleepInSecond(10);
		Assert.assertTrue(driver.findElement(helloWorldText).isDisplayed());
	}
	
	//static wait/dead wait/hard wait
	public void sleepInSecond(long second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
