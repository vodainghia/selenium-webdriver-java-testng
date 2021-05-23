package api;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_10_Alert {
	WebDriver driver;
	Alert alert;
	WebDriverWait explicitWait;
	By resultValue = By.xpath("//p[@id='result']");

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		explicitWait = new WebDriverWait(driver, 10);
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}
	
	
	//@Test
	public void TC_01_Accept_Alert() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
		
		//Có 3 loại cần switch vào mới thao tác được, là: Alert, Frame/ iFrame, Windows/ Tab
		//Cách 1, để dùng driver để switch vào alert, chỉ khởi tạo khi nào nó đã xuất hiện 
		//alert = driver.switchTo().alert();
		
		//Cách 2, vừa wait mà vừa dùng driver để switch vào alert, cách này tối ưu hơn cách 1
		alert = explicitWait.until(ExpectedConditions.alertIsPresent());
		
		//get text to verify
		Assert.assertEquals(alert.getText(), "I am a JS Alert");
		
		//accept alert
		alert.accept();
		sleepInSecond(3);
		
		Assert.assertEquals(driver.findElement(resultValue).getText(), "You clicked an alert successfully");
	}
	
	//@Test
	public void TC_02_Confirm_Alert() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
		
		alert = explicitWait.until(ExpectedConditions.alertIsPresent());
		
		Assert.assertEquals(alert.getText(), "I am a JS Confirm");
		
		//cancel alert
		alert.dismiss();
		sleepInSecond(3);
		
		Assert.assertEquals(driver.findElement(resultValue).getText(), "You clicked: Cancel");
		
		driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
		
		//accept alert
		alert.accept();
		sleepInSecond(3);
		
		Assert.assertEquals(driver.findElement(resultValue).getText(), "You clicked: Ok");
	}
	
	//@Test
	public void TC_03_Prompt_Alert() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		String alertValue = "Selenium Webdriver";
		
		driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
		
		alert = explicitWait.until(ExpectedConditions.alertIsPresent());
		
		Assert.assertEquals(alert.getText(), "I am a JS prompt");
		
		//cancel alert
		alert.dismiss();
		sleepInSecond(3);
		
		Assert.assertEquals(driver.findElement(resultValue).getText(), "You entered: null");
		
		driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
		
		//sendkey alert
		alert.sendKeys(alertValue);
		sleepInSecond(3);
		alert.accept();
		
		Assert.assertEquals(driver.findElement(resultValue).getText(), "You entered: " + alertValue);
	}
	
	//@Test
	public void TC_04_Authentication_Alert() {
		//dùng trick để login theo cú pháp: http(s)://username:password@domain.xxx
		driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");
		
		Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());
		
	}
	
	@Test
	public void TC_05_Authentication_Alert() {
		//Xử lý lấy URL để truyền vào khi chuyển trang mà trang sau đòi authen alert
		driver.get("http://the-internet.herokuapp.com");
		
		String basicAuthenUrl = driver.findElement(By.xpath("//a[text()='Basic Auth']")).getAttribute("href");
		System.out.println(basicAuthenUrl);
		driver.get(passInforToUrl(basicAuthenUrl));
		sleepInSecond(3);
		
		Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());
		
	}
	
	public String passInforToUrl(String url) {
		String[] urlValue = url.split("//");
		url = urlValue[0] + "//" + "admin" + ":" + "admin" + "@" + urlValue[1];
		return url;
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
