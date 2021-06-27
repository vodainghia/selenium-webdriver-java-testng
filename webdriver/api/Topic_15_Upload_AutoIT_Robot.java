package api;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_15_Upload_AutoIT_Robot {
	WebDriver driver;
	WebDriverWait explicitWait;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	String cloudFileName = "cloud.jpg";
	String cloudFilePath = projectPath + "\\uploadFiles\\" + cloudFileName;
	String sasnarFileName = "nar-sas.jpg";
	String sasnarFilePath = projectPath + "\\uploadFiles\\" + sasnarFileName;
	String tobiFileName = "tobi.png";
	String tobiFilePath = projectPath + "\\uploadFiles\\" + tobiFileName;
	
	String firefoxOneTimeAutoIT = projectPath + "\\autoIT\\firefoxUploadOneTime.exe";
	String chromeOneTimeAutoIT = projectPath + "\\autoIT\\chromeUploadOneTime.exe";
	String firefoxMultipleTimeAutoIT = projectPath + "\\autoIT\\firefoxUploadMultiple.exe";
	String chromeMultipleTimeAutoIT = projectPath + "\\autoIT\\chromeUploadMultiple.exe";
	
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
		explicitWait = new WebDriverWait(driver, 15);
	}
	
	//@Test
	public void TC_01_AutoIT_One_File() throws IOException {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");

		//bật Open File Dialog: Click vào button 
		driver.findElement(By.cssSelector(".btn-success")).click();
		
		//dùng AutoIT để chọn file
		if (driver.toString().contains("chrome") || driver.toString().contains("edge")) {
			Runtime.getRuntime().exec(new String[] { chromeOneTimeAutoIT, cloudFilePath });
		} else if (driver.toString().contains("firefox")) {
			Runtime.getRuntime().exec(new String[] { firefoxMultipleTimeAutoIT, cloudFilePath });
		}
		
		//Verify file load success
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + cloudFileName + "']")).isDisplayed());
		
		//Click Start upload button
		driver.findElement(By.cssSelector("table .start")).click();
		
		//Verify file uploaded successfully
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + cloudFileName + "']")).isDisplayed());
	}
	
	//@Test
	public void TC_02_AutoIT_Multi_Files() throws IOException {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");

		//bật Open File Dialog: Click vào button 
		driver.findElement(By.cssSelector(".btn-success")).click();
				
		//dùng AutoIT để chọn nhiều file
		//Do giới hạn số ký tự tối đa ở "File name" của Open file dialog nên ở đây chỉ chọn đc 2 files
		if (driver.toString().contains("chrome") || driver.toString().contains("edge")) {
			Runtime.getRuntime().exec(new String[] { chromeMultipleTimeAutoIT, cloudFilePath, sasnarFilePath, tobiFilePath });
		} else if (driver.toString().contains("firefox")) {
			Runtime.getRuntime().exec(new String[] { firefoxMultipleTimeAutoIT, cloudFilePath, sasnarFilePath, tobiFilePath });
		}
		
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
	
	//@Test
	public void TC_03_Java_Robot() {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		
		//bật Open File Dialog: Click vào button 
		driver.findElement(By.cssSelector(".btn-success")).click();
		
		uploadFileByRobot(cloudFilePath);
		
		//Verify file load success
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + cloudFileName + "']")).isDisplayed());
				
		//Click Start upload button
		driver.findElement(By.cssSelector("table .start")).click();
				
		//Verify file uploaded successfully
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + cloudFileName + "']")).isDisplayed());
	}
	
	@Test
	public void TC_04_Upload_Flow() {
		driver.get("https://gofile.io/?t=uploadFiles");

		String parentTabID = driver.getWindowHandle();
		
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(cloudFilePath + "\n" + sasnarFilePath + "\n" + tobiFilePath);
		
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rowUploadSuccess-downloadPage")));
		
		Assert.assertTrue(driver.findElement(By.id("rowUploadSuccess-downloadPage")).isDisplayed());
		
		driver.findElement(By.id("rowUploadSuccess-downloadPage")).click();
		
		switchToWindowByID(parentTabID);
		//Verify download action displayed for each file
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + cloudFileName + "']/parent::td/following-sibling::td/a[contains(@class,'download')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + sasnarFileName + "']/parent::td/following-sibling::td/a[contains(@class,'download')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + tobiFileName + "']/parent::td/following-sibling::td/a[contains(@class,'download')]")).isDisplayed());
	
		//Verify play action displayed for each file
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + cloudFileName + "']/parent::td/following-sibling::td/a[contains(@class,'play')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + sasnarFileName + "']/parent::td/following-sibling::td/a[contains(@class,'play')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + tobiFileName + "']/parent::td/following-sibling::td/a[contains(@class,'play')]")).isDisplayed());
		
		//Verify info action displayed for each file
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + cloudFileName + "']/parent::td/following-sibling::td/a[contains(@class,'info')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + sasnarFileName + "']/parent::td/following-sibling::td/a[contains(@class,'info')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + tobiFileName + "']/parent::td/following-sibling::td/a[contains(@class,'info')]")).isDisplayed());
	
	
	}
	
	public void sleepInSecond(long second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void uploadFileByRobot(String filePath) {
		try {
			//Specific the file location with extension
			StringSelection select = new StringSelection(filePath);
			
			//Copy to clipboard
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(select, null);
			
			Robot robot = new Robot();
			sleepInSecond(1);
			
			//Nhan va nha phim Enter
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			
			//Nhan phim Ctrl + V
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			
			//Nha phim Ctrl + V
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_V);
			sleepInSecond(1);
			
			//Nhan va nha phim Enter
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	public void switchToWindowByID(String windowID) {
		//Get hết các Window ID đang có
		Set<String> allTabIDs = driver.getWindowHandles();
		
		//Duyệt qua các giá trị trong all windows
		for (String id : allTabIDs) {
			if (!id.equals(windowID)) {
				driver.switchTo().window(id);
				break;
			}
		}
	}
	
	@AfterClass
	public void afterClass() {
		//driver.quit();
	}

}
