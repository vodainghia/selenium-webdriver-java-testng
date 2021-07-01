package api;

import java.util.Date;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_16_Wait_V_Explicit {
	WebDriver driver;
	WebDriverWait explicitWait;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	By startButton = By.cssSelector("#start>button");
	By loadingIcon = By.cssSelector("div#loading");
	By helloWorldText = By.xpath("//h4[text()='Hello World!']");
	String cloudFileName = "cloud.jpg";
	String cloudFilePath = projectPath + "\\uploadFiles\\" + cloudFileName;
	String sasnarFileName = "nar-sas.jpg";
	String sasnarFilePath = projectPath + "\\uploadFiles\\" + sasnarFileName;
	
	
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
		explicitWait = new WebDriverWait(driver, 60);
	}
	
	@Test
	public void TC_01_Visible() {
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
		
		driver.findElement(startButton).click();
		
		//Chờ cho text hello world được hiển thị (visible): sau khi step trước được hoàn thành, sẽ chờ cho element của step sau được xuất hiện
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(helloWorldText));
		
		Assert.assertTrue(driver.findElement(helloWorldText).isDisplayed());
	}
	
	@Test
	public void TC_02_Invisible() {
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
		
		driver.findElement(startButton).click();
		
		//Chờ cho loading bar biến mất (invisible): chờ cho step trước được hoàn thành rồi mới qua step sau
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(loadingIcon));
		
		Assert.assertTrue(driver.findElement(helloWorldText).isDisplayed());
	}
	
	@Test
	public void TC_03_Ajax_Loading() {
		driver.get("https://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");
		
		//Wait cho Datepicker hiển thị
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_ContentPlaceholder1_Label1")));
		
		//WebElement dateSelected = driver.findElement(By.id("ctl00_ContentPlaceholder1_Label1")); //Ở đây ko thể đặt biến để dùng lại, do chạy qua các step thì state của Element lưu ở lúc tạo biến đã khác với state của cùng Element sau khi thao tác (UI vẫn y chang), nên chỉ có thể findElement lại
		
		Assert.assertEquals(driver.findElement(By.id("ctl00_ContentPlaceholder1_Label1")).getText(), "No Selected Dates to display.");
		
		//Click vào today
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td/a[text()='1']")));
		
		driver.findElement(By.xpath("//td/a[text()='1']")).click();
		
		//Wait cho Ajax loading biến mất
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[not(@style='display:none;')]/div[@class='raDiv']")));
		
		//Verify today được selected
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@class='rcSelected']/a[text()='1']")));
		
		Assert.assertTrue(driver.findElement(By.xpath("//td[@class='rcSelected']/a[text()='1']")).isDisplayed());
		
		//Verify today được update lên Selected Dates
		Assert.assertEquals(driver.findElement(By.id("ctl00_ContentPlaceholder1_Label1")).getText(), "Thursday, July 1, 2021");
		
	}
	
	@Test
	public void TC_04_Upload_File() {
		driver.get("https://gofile.io/?t=uploadFiles");
		
		//wait Add file button visible
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.uploadButton")));
		
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(cloudFilePath + "\n" + sasnarFilePath);
		
		//wait choose server icon invisible
		System.out.println("Start server icon = " + getDateTimeNow()); //quan sát thời gian chạy để dễ debug khi cần
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#rowUploadProgress-selectServer")));
		System.out.println("End server icon = " + getDateTimeNow()); //quan sát thời gian chạy để dễ debug khi cần
		
		//Wait for file uploaded success
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'text-truncate') and text()='" + cloudFileName + "']")));
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'text-truncate') and text()='" + sasnarFileName + "']")));
		System.out.println("End file uploaded = " + getDateTimeNow()); //quan sát thời gian chạy để dễ debug khi cần
		
		//Wait progress bar icons invisible
		explicitWait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(By.cssSelector("div.progress"))));
		System.out.println("End progress bar icons = " + getDateTimeNow()); //quan sát thời gian chạy để dễ debug khi cần
		
		//Wait success message displayed
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[text()='Your files have been successfully uploaded']")));
		System.out.println("End success message = " + getDateTimeNow()); //quan sát thời gian chạy để dễ debug khi cần
		
		Assert.assertTrue(driver.findElement(By.xpath("//h5[text()='Your files have been successfully uploaded']")).isDisplayed());
		
		//Wait Show file button clickable
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button#rowUploadSuccess-showFiles")));
		
		driver.findElement(By.cssSelector("button#rowUploadSuccess-showFiles")).click();
		
		//Verify images are uploaded successfully
		Assert.assertTrue(driver.findElement(By.xpath("//span[@class='contentName' and text()='" + cloudFileName + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//span[@class='contentName' and text()='" + sasnarFileName + "']")).isDisplayed());
	}
	
	public String getDateTimeNow() {
		Date date = new Date();
		return date.toString();
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
