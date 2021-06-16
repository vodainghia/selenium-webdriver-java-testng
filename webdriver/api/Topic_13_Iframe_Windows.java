package api;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_13_Iframe_Windows {
	WebDriver driver;
	Actions action;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	JavascriptExecutor jsExecutor;
	
	@BeforeClass
	public void beforeClass() {
		if (osName.toLowerCase().contains("mac os")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver.exe");
		}
		
		driver = new FirefoxDriver();
		action = new Actions(driver);
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}
	
	//@Test
	public void TC_01_Iframe() {
		//switch alert
		//driver.switchTo().alert();	

		//switch frame/ iframe
		//driver.switchTo().frame(0); //index: ko nên dùng
		
		//driver.switchTo().frame("video-2679-1_youtube_iframe"); //id or name: ngon nhưng ko phải có sẵn để dùng
		
		//driver.switchTo().frame(driver.findElement(By.xpath(""))); //webElement: dùng được trong mọi tình huống
		
		driver.get("https://automationfc.com/2020/02/18/training-online-automation-testing/");
		
		//Switch to facebook iframe: muốn bắt element trong iframe thì phải switch vào iframe, 
		//sau đó nếu muốn bắt element trong parent thì phải switch lại về parent. 
		//KHÔNG thể di chuyển trực tiếp qua lại giữa các iframe, phải đi qua trung gian là parent page! 
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@data-testid='fb:page Facebook Social Plugin']")));
		
		//Element of facebook iframe
		String likeNumber = driver.findElement(By.xpath("//a[text()='Automation FC']/parent::div/following-sibling::div")).getText();
		System.out.println(likeNumber);
		
		//Switch to parent page
		driver.switchTo().defaultContent();
		
		//Element of parent
		String postTitle = driver.findElement(By.xpath("//h1[@class='post-title']")).getText();
		System.out.println(postTitle);

		//Switch to google doc iframe then sendKeys
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'docs.google.com')]")));
		driver.findElement(By.xpath("//div[contains(@data-params,'HỌ TÊN')]//input")).sendKeys("Automation FC");
	}
	
	@Test
	public void TC_02_Frame() {
		driver.get("https://v1.hdfcbank.com/assets/popuppages/netbanking.htm");
		
		driver.findElement(By.xpath("(//div[@class='container']/div/a[text()='Continue to NetBanking'])[1]")).click();
		
		driver.switchTo().frame("login_page");
		
		driver.findElement(By.name("fldLoginUserId")).sendKeys("AutomationFC");
		driver.findElement(By.xpath("//a[contains(@onclick,'fLogon')]/img[@alt='continue']")).click();

		Assert.assertTrue(driver.findElement(By.name("fldPassword")).isDisplayed());
		
		//Switch to parent page
		driver.switchTo().defaultContent();
		
		driver.switchTo().frame("footer");
		
		driver.findElement(By.xpath("//a[text()='Terms and Conditions']")).click();
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
