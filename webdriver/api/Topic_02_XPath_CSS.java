package api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import javax.lang.model.element.Element;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Topic_02_XPath_CSS {
	WebDriver driver;
	String projectLocation = System.getProperty("user.dir");
	String baseUrl = "http://live.demoguru99.com/";
	
	@BeforeClass
	public void beforeClass() {
		//driver = new FirefoxDriver(); //Không cần nếu FF ver <= 47
		//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//driver.manage().window().maximize();
	}
	
	@Test
	public void TC_00_Run_Demo_On_Chrome() {
		//Cách 1: set đường dẫn cứng đến chromedriver, cách này ko thể dùng khi làm việc nhóm vì đường dẫn chỉ dùng trên 1 máy
		//System.setProperty("webdriver.chrome.driver", "D:\\IT\\selenium-webdriver-java\\02 - Selenium API\\selenium-webdriver-java-testng\\browserDrivers\\chromedriver.exe");
		
		//Cách 2: Dùng được trên tất cả máy trong team, nhưng đọc đường dẫn sẽ khó hiểu ở chỗ dấu "." đại diện cho project location
		//System.setProperty("webdriver.chrome.driver", ".\\browserDrivers\\chromedriver.exe");
		
		//Cách 3: Dùng được trên tất cả máy trong team, đọc đường dẫn dễ hiểu hơn
		System.setProperty("webdriver.chrome.driver", projectLocation + "\\browserDrivers\\chromedriver.exe");
		
		driver = new ChromeDriver();
		driver.get("https://google.com");
		driver.quit();
		
	}
	
	//@Test
	public void TC_01_Login_Empty_Email_And_Password() {
		driver.get(baseUrl);
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		
		driver.findElement(By.id("email")).sendKeys("");
		driver.findElement(By.id("pass")).sendKeys("");
		
		driver.findElement(By.id("send2")).click();
		
		Assert.assertEquals(driver.findElement(By.id("advice-required-entry-email")).getText(), "This is a required field.");
		Assert.assertEquals(driver.findElement(By.id("advice-required-entry-pass")).getText(), "This is a required field.");
	}
	
	//@Test
	public void TC_02_Login_Invalid_Email() {
		driver.get(baseUrl);
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		
		driver.findElement(By.id("email")).sendKeys("123.456@789");
		driver.findElement(By.id("pass")).sendKeys("123456");
		
		driver.findElement(By.id("send2")).click();
		
		Assert.assertEquals(driver.findElement(By.id("advice-validate-email-email")).getText(), "Please enter a valid email address. For example johndoe@domain.com.");
	}
	
	//@Test
	public void TC_03_Login_Invalid_Password() {
		driver.get(baseUrl);
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		
		driver.findElement(By.id("email")).sendKeys("nghiatest01@gmail.com");
		driver.findElement(By.id("pass")).sendKeys("123");
		
		driver.findElement(By.id("send2")).click();
		
		Assert.assertEquals(driver.findElement(By.id("advice-validate-password-pass")).getText(), "Please enter 6 or more characters without leading or trailing spaces.");
	}
	
	//@Test
	public void TC_04_Login_Incorrect_Password() {
		driver.get(baseUrl);
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		
		driver.findElement(By.id("email")).sendKeys("nghiatest01@gmail.com");
		driver.findElement(By.id("pass")).sendKeys("123457");
		
		driver.findElement(By.id("send2")).click();
	
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='error-msg']//span")).getText(), "Invalid login or password.");
	}
	
	//@Test
	public void TC_05_Create_New_Account() {
		String dateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
		String firstName = "nghia" + dateTime;
		String lastName = "test" + dateTime;
		String email = "nghiatest01+" + dateTime + "@gmail.com";
		
		driver.get(baseUrl);
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.xpath("//form[@id='login-form']//a[@title='Create an Account']")).click();
	
		driver.findElement(By.id("firstname")).sendKeys(firstName);
		driver.findElement(By.id("lastname")).sendKeys(lastName);
		driver.findElement(By.id("email_address")).sendKeys(email);
		driver.findElement(By.id("password")).sendKeys("123456");
		driver.findElement(By.id("confirmation")).sendKeys("123456");
		
		driver.findElement(By.xpath("//form[@id='form-validate']//button[@title='Register']")).click();
		
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='success-msg']//span")).getText(), "Thank you for registering with Main Website Store.");
		//Assert.assertEquals(driver.findElement(By.xpath("//div[@class='col-1']//div[@class='box-content']/p")).getText(), firstName + " " + lastName);
		//Assert.assertEquals(driver.findElement(By.xpath("//div[@class='col-1']//div[@class='box-content']/p")).getText(), email);
		
		driver.findElement(By.xpath("//span[text()='Account']")).click();
		driver.findElement(By.xpath("//div[@id='header-account']//a[@title='Log Out']")).click();
		
		new WebDriverWait(driver, 20).until(ExpectedConditions.urlToBe(baseUrl));
		
	}
	
	//@Test
	public void TC_06_Login_Correct_Email_And_Password() {
		driver.get(baseUrl);
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
	
		//driver.findElement(By.id("email")).sendKeys(email);
		driver.findElement(By.id("pass")).sendKeys("123456");
		
		driver.findElement(By.id("send2")).click();
		
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='page-title']/h1")).getText(), "MY DASHBOARD");
		//Assert.assertEquals(driver.findElement(By.xpath("//p[@class='hello']/strong")).getText(), "Hello, " + firstName + lastName + "!");
		//Assert.assertEquals(driver.findElement(By.xpath("//div[@class='col-1']//div[@class='box-content']/p")).getText(), firstName + " " + lastName);
		//Assert.assertEquals(driver.findElement(By.xpath("//div[@class='col-1']//div[@class='box-content']/p")).getText(), email);
	}
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
