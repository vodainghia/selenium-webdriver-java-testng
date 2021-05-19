package api;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.server.handler.FindElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_09_Button_Radio_Checkbox {
	WebDriver driver;
	WebDriverWait explicitWait;
	JavascriptExecutor jsExecutor;
	
	By loginButton = By.cssSelector(".fhs-btn-login");
	By usernameTextbox = By.cssSelector("#login_username");
	By passwordTextbox = By.cssSelector("#login_password");

	@BeforeClass
	public void beforeClass() {
		//System.setProperty("webdriver.chrome.driver",".\\browserDrivers\\chromedriver.exe");
		//driver = new ChromeDriver();
		driver = new FirefoxDriver();
		
		jsExecutor = (JavascriptExecutor) driver;
		explicitWait = new WebDriverWait(driver, 30); //wait phải được khởi tạo sau driver vì nó cần có driver để truyền vào
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}
	
	//@Test
	public void TC_01_Button() {
		driver.get("https://www.fahasa.com/customer/account/create");
		
		driver.findElement(By.cssSelector(".popup-login-tab-login")).click();
		
		//Verify login button disable
		Assert.assertFalse(driver.findElement(loginButton).isEnabled());
		
		driver.findElement(usernameTextbox).sendKeys("automation@gmail.com");
		driver.findElement(passwordTextbox).sendKeys("123456");
		sleepInSecond(3);
		
		//Verify login button enable
		Assert.assertTrue(driver.findElement(loginButton).isEnabled());
		
		driver.navigate().refresh();
		
		driver.findElement(By.cssSelector(".popup-login-tab-login")).click();
		
		jsExecutor.executeScript("arguments[0].removeAttribute('disabled')", driver.findElement(loginButton));
		sleepInSecond(3);
		
		//Verify login button enable
		Assert.assertTrue(driver.findElement(loginButton).isEnabled());
		
		driver.findElement(loginButton).click();
		
		Assert.assertEquals(driver.findElement(By.xpath("//input[@id='login_username']/parent::div/following-sibling::div")).getText(), "Thông tin này không thể để trống");
		Assert.assertEquals(driver.findElement(By.xpath("//input[@id='login_password']/parent::div/following-sibling::div")).getText(), "Thông tin này không thể để trống");
		
	}
	
	//@Test
	public void TC_01a_Hidden_Element() {
		driver.get("https://www.fahasa.com/customer/account/create");
		
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//div[contains(@class,'background-menu-none-homepage')]//span[text()='Sách Trong Nước']")));
		sleepInSecond(4);
	}
	
	//@Test
	public void TC_02_Default_Radio_Checkbox() {
		driver.get("http://demos.telerik.com/kendo-ui/styling/radios");
		
		driver.findElement(By.xpath("//label[text()='1.8 Petrol, 118kW']/preceding-sibling::input")).click();
		sleepInSecond(3);
		
		Assert.assertTrue(driver.findElement(By.xpath("//label[text()='1.8 Petrol, 118kW']/preceding-sibling::input")).isSelected());
		
		driver.findElement(By.xpath("//label[text()='2.0 Diesel, 103kW']/preceding-sibling::input")).click();
		sleepInSecond(3);
		
		Assert.assertFalse(driver.findElement(By.xpath("//label[text()='1.8 Petrol, 118kW']/preceding-sibling::input")).isSelected());
		Assert.assertTrue(driver.findElement(By.xpath("//label[text()='2.0 Diesel, 103kW']/preceding-sibling::input")).isSelected());
		
		driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");
		
		driver.findElement(By.xpath("//label[text()='Luggage compartment cover']/preceding-sibling::input")).click();
		driver.findElement(By.xpath("//label[text()='Heated front and rear seats']/preceding-sibling::input")).click();
		driver.findElement(By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::input")).click();
		
		Assert.assertTrue(driver.findElement(By.xpath("//label[text()='Luggage compartment cover']/preceding-sibling::input")).isSelected());
		Assert.assertTrue(driver.findElement(By.xpath("//label[text()='Heated front and rear seats']/preceding-sibling::input")).isSelected());
		Assert.assertTrue(driver.findElement(By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::input")).isSelected());
	}
	
	//@Test
	public void TC_03_Select_All_Checkbox() {
		driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");
		
		List<WebElement> allCheckboxes = driver.findElements(By.xpath("//input[@type='checkbox' and not (@disabled)]"));
		
		//Select all checkboxes
		for (WebElement checkbox : allCheckboxes) {
			if (!checkbox.isSelected()) {
				checkbox.click();
				sleepInSecond(1);
			}
			Assert.assertTrue(checkbox.isSelected());
		}
		
		//Deselect all checkboxes
		for (WebElement checkbox : allCheckboxes) {
			if (checkbox.isSelected()) {
				checkbox.click();
				sleepInSecond(1);
			}
			Assert.assertFalse(checkbox.isSelected());
		}
	}
	
	//@Test
	public void TC_04_Custom_Radio_Checkbox() {
		driver.get("https://material.angular.io/components/radio/examples");
		
		//Input để click + Input để verify: trường hợp này sẽ báo lỗi do span đang đè lên input, input ko click được
		//Input is hidden
		//driver.findElement(By.xpath("//span[contains(text(),'Spring')]/preceding-sibling::span/input")).click();
		//sleepInSecond(3);
		//Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text(),'Spring')]/preceding-sibling::span/input")).isSelected());
		
		//Label để click + Label để verify: click được nhưng label ko thể dùng để verify
		//driver.findElement(By.xpath("//span[contains(text(),'Spring')]")).click();
		//sleepInSecond(3);
		//Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text(),'Spring')]")).isSelected());
		
		//Label để click + Input để verify thì thành công: nhược điểm là maintain code rất cực do phải handle 2 locators
		//driver.findElement(By.xpath("//span[contains(text(),'Spring')]")).click();
		//sleepInSecond(3);
		//Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text(),'Spring')]/preceding-sibling::span/input")).isSelected());
		
		//Input để click (JavaScript) + Input để verify: tận dụng js executor có thể click mà ko cần nhìn thấy thẻ input, chỉ còn 1 locator dùng chung
		By springRadio = By.xpath("//span[contains(text(),'Spring')]/preceding-sibling::span/input");
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(springRadio));
		sleepInSecond(3);
		Assert.assertTrue(driver.findElement(springRadio).isSelected());	
	}
	
	@Test
	public void TC_05_Custom_Radio_Checkbox_Google_Form() {
		driver.get("https://docs.google.com/forms/d/e/1FAIpQLSfiypnd69zhuDkjKgqvpID9kwO29UCzeCVrGGtbNPZXQok0jA/viewform");
		
		driver.findElement(By.xpath("//span[text()='Cần Thơ']/ancestor::div[@class='docssharedWizToggleLabeledContent']/preceding-sibling::div")).click();
		sleepInSecond(3);
		Assert.assertTrue(driver.findElement(By.xpath("//div[@aria-label='Cần Thơ' and @aria-checked='true']")).isDisplayed());
		
		//select all checkboxes start with text = "Quảng"
		List<WebElement> allCheckboxes = driver.findElements(By.xpath("//div[starts-with(@aria-label,'Quảng')]/div[contains(@class,'exportInnerBox')]"));
		for (int i = 0; i < allCheckboxes.size(); i++) {
			allCheckboxes.get(i).click();
			sleepInSecond(1);
		}
		
		//Verify cách 1
		List<WebElement> allCheckboxesSelected = driver.findElements(By.xpath("//div[starts-with(@aria-label,'Quảng') and @role='checkbox' and @aria-checked='true']"));
		for (WebElement checkbox : allCheckboxesSelected) {
			Assert.assertTrue(checkbox.isDisplayed());
		}
		
		//Verify cách 2
		allCheckboxesSelected = driver.findElements(By.xpath("//div[starts-with(@aria-label,'Quảng') and @role='checkbox']"));
		for (WebElement checkbox : allCheckboxesSelected) {
			Assert.assertEquals(checkbox.getAttribute("aria-checked"), "true");
		}
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
