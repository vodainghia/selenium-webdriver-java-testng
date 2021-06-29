package api;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_15_Upload_Sendkeys {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	String cloudFileName = "cloud.jpg";
	String cloudFilePath = projectPath + "\\uploadFiles\\" + cloudFileName;
	String sasnarFileName = "nar-sas.jpg";
	String sasnarFilePath = projectPath + "\\uploadFiles\\" + sasnarFileName;
	String tobiFileName = "tobi.png";
	String tobiFilePath = projectPath + "\\uploadFiles\\" + tobiFileName;
	
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
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	//@Test
	public void TC_01_Sendkey_One_File() {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");

		//load file ko cần bật Open File Dialog
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(cloudFilePath);
		
		//Verify file load success
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + cloudFileName + "']")).isDisplayed());
		
		//Click Start upload button
		driver.findElement(By.cssSelector("table .start")).click();
		
		//Verify file uploaded successfully
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + cloudFileName + "']")).isDisplayed());
	}
	
	@Test
	public void TC_02_Sendkeys_Multi_Files() {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");

		//load nhiều files ko cần bật Open File Dialog, cộng thêm "\n" cho mỗi file
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(cloudFilePath + "\n" + sasnarFilePath + "\n" + tobiFilePath);
		
		//Verify files load success
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + cloudFileName + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + sasnarFileName + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + tobiFileName + "']")).isDisplayed());
		
		//Click Start upload buttons
		List<WebElement> startUploadButtons = driver.findElements(By.cssSelector("table .start"));
		for (WebElement startButton : startUploadButtons) {
			startButton.click();
			sleepInSecond(1);
		}
		
		//Verify files uploaded successfully
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + cloudFileName + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + sasnarFileName + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + tobiFileName + "']")).isDisplayed());
	}
	
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
