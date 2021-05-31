package api;

import java.util.List;
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

public class Topic_12_Popup {
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
		driver.manage().timeouts().implicitlyWait(defaultTimeout, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}
	
	//@Test
	public void TC_01_Fixed_Popup_In_DOM() {
		driver.get("https://www.zingpoll.com/");
		
		By signInPopup = By.cssSelector(".modal_dialog_custom");
		
		driver.findElement(By.xpath("//a[@id='Loginform']")).click();
		sleepInSecond(1);
		
		//Verify signin popup is displayed
		Assert.assertTrue(driver.findElement(signInPopup).isDisplayed());
		
		driver.findElement(By.id("loginEmail")).sendKeys("nghiatest01@gmail.com");
		driver.findElement(By.id("loginPassword")).sendKeys("123456");
		
		driver.findElement(By.cssSelector(".modal_dialog_custom .close")).click();
		sleepInSecond(1);
		
		//Verify signin popup is NOT displayed
		Assert.assertFalse(driver.findElement(signInPopup).isDisplayed());		
	}
	
	//@Test
	public void TC_02_Random_Popup_In_DOM() {//isDisplayed() sẽ làm fail TC vì element ko còn tồn tại trong DOM
		driver.get("https://blog.testproject.io/");
		
		if (isElementDisplayed2(By.cssSelector(".mailch-wrap"))) {
			driver.findElement(By.cssSelector("#close-mailch")).click();
			sleepInSecond(1);
		}
		
		driver.findElement(By.cssSelector("section input[class='search-field']")).sendKeys("Selenium");
		driver.findElement(By.cssSelector("section span[class='glass']")).click();
		
		List<WebElement> postArticles = driver.findElements(By.xpath("//h3[@class='post-title']/a"));
		
		for (WebElement articles : postArticles) {
			Assert.assertTrue(articles.getText().contains("Selenium"));
		}
	}
	
	//@Test
	public void TC_03_Random_Popup_Not_In_DOM() {//isDisplayed() sẽ làm fail TC vì element ko còn tồn tại trong DOM
		driver.get("https://shopee.vn/");
		
		By homePopup = By.cssSelector("img[alt='home_popup_banner']");
		
		//Verify home popup is displayed
		Assert.assertTrue(isElementDisplayed2(homePopup));
		
		driver.findElement(By.cssSelector(".shopee-popup__close-btn")).click();
		sleepInSecond(1);
		
		//Verify home popup is NOT displayed
		Assert.assertFalse(isElementDisplayed2(homePopup));		
	}
	
	@Test
	public void TC_04_Random_Popup_Not_In_DOM() {
		driver.get("https://shopee.vn/");
		String searchKeyWord = "Macbook Pro";
		
		By homePopup = By.cssSelector("img[alt='home_popup_banner']");
		
		if (isElementDisplayed2(homePopup)) {
			driver.findElement(By.cssSelector(".shopee-popup__close-btn")).click();
			sleepInSecond(1);
		}
		
		driver.findElement(By.cssSelector(".shopee-searchbar-input input")).sendKeys(searchKeyWord);
		driver.findElement(By.cssSelector(".btn-solid-primary")).click();
		
		List<WebElement> products = driver.findElements(By.cssSelector(".shopee-search-item-result__items div[data-sqe='name'] .yQmmFK"));
		
		for (WebElement product : products) {
			String productName = product.getText().toLowerCase();
			Assert.assertTrue(productName.contains(searchKeyWord.split(" ")[0].toLowerCase()) || productName.contains(searchKeyWord.split(" ")[1].toLowerCase()));
		}
	}
	
	public boolean isElementDisplayed(By by) {
		try {
			return driver.findElement(by).isDisplayed();
		} catch (Exception e) {//Exception sẽ làm fail TC ngay step đó, nên cần viết hàm lại dùng try catch
			return false;
		}
	}
	
	public boolean isElementDisplayed2(By by) {//cách tối ưu hơn, sử dụng trong framework
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		List<WebElement> elements = driver.findElements(by);
		driver.manage().timeouts().implicitlyWait(defaultTimeout, TimeUnit.SECONDS);
		if (elements.size() == 0) {
			return false;
		} else if (elements.size() > 0 && !elements.get(0).isDisplayed()) {
			return false;
		} else {
			return true;
		}
	}
	
	long defaultTimeout = 30;

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
