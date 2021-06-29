package api;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_16_Wait_III_Implicit {
	WebDriver driver;
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
	}
	
	@Test
	public void TC_01_Dont_Set_Implicit() {
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
		
		driver.findElement(startButton).click();
		
		//đang set 5s sau thì hello world mới xuất hiện, ko set thời gian chờ => Failed
		Assert.assertTrue(driver.findElement(helloWorldText).isDisplayed());
	}
	
	@Test
	public void TC_02_Set_Implicit_3s() {
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
		driver.findElement(startButton).click();
		
		//đang set 5s sau thì hello world mới xuất hiện, thời gian chờ chưa đủ => Failed
		Assert.assertTrue(driver.findElement(helloWorldText).isDisplayed());
	}
	
	@Test
	public void TC_03_Set_Implicit_6s() {
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
		driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
		
		driver.findElement(startButton).click();
		
		//đang set 5s sau thì hello world mới xuất hiện, thời gian chờ lớn hơn nên đã đủ => Passed
		Assert.assertTrue(driver.findElement(helloWorldText).isDisplayed());
	}
	
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
