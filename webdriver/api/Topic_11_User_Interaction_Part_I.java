package api;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_11_User_Interaction_Part_I {
	WebDriver driver;
	Actions action;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		action = new Actions(driver);
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}
	
	//@Test
	public void TC_01_Hover() {
		driver.get("https://automationfc.github.io/jquery-tooltip/");
		//action luôn đi với perform mới thực hiện được
		action.moveToElement(driver.findElement(By.xpath("//input[@id='age']"))).perform();
		sleepInSecond(2);
		
		Assert.assertEquals(driver.findElement(By.cssSelector(".ui-tooltip-content")).getText(), "We ask for your age only for statistical purposes.");
		
	}
	
	//@Test
	public void TC_02_Hover() {
		driver.get("https://www.myntra.com/");
		
		action.moveToElement(driver.findElement(By.xpath("//a[@class='desktop-main' and text()='Kids']"))).perform();
		sleepInSecond(2);
		
		action.click(driver.findElement(By.xpath("//a[@class='desktop-categoryName' and text()='Home & Bath']"))).perform();
		
		Assert.assertTrue(driver.findElement(By.xpath("//span[@class='breadcrumbs-crumb' and text()='Kids Home Bath']")).isDisplayed());
		
	}
	
	@Test
	public void TC_03_Hover() {
		driver.get("https://hn.telio.vn/");
		
		action.moveToElement(driver.findElement(By.xpath("//main[@id='maincontent']//span[text()='Sữa & Sản phẩm từ sữa']"))).perform();
		sleepInSecond(1);
		
		Assert.assertTrue(driver.findElement(By.xpath("//main[@id='maincontent']//a[contains(text(),'Sữa đặc')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//main[@id='maincontent']//a[contains(text(),'Sữa tươi')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//main[@id='maincontent']//a[contains(text(),'Sữa chua')]")).isDisplayed());
	}
	
	//@Test
	public void TC_04_Click_And_Hold() {
		driver.get("http://jqueryui.com/resources/demos/selectable/display-grid.html");
		
		//Lấy ra đc 12 items, index 0-11
		List<WebElement> retangles = driver.findElements(By.cssSelector("ui-selectee"));
		
		action.clickAndHold(retangles.get(0)) //Click và giữ ô số 1
		.moveToElement(retangles.get(3))      //Di chuyển chuột đến ô số 4
		.release()                            //Nhả chuột trái ra
		.perform();                           //Thực thi các actions
		
		sleepInSecond(5);
		
		Assert.assertEquals(driver.findElements(By.cssSelector(".ui-selectee.ui-selected")).size(), 4);
		
	}
	
	//@Test
	public void TC_05_Click_And_Hold() {
		driver.get("http://jqueryui.com/resources/demos/selectable/display-grid.html");
		
		//Lấy ra đc 12 items, index 0-11
		List<WebElement> retangles = driver.findElements(By.cssSelector("ui-selectee"));
		
		//nhấn phím Ctrl xuống và giữ
		if (osName.toLowerCase().contains("mac os")) {
			action.keyDown(Keys.COMMAND).perform();
		} else {
			action.keyDown(Keys.CONTROL).perform();
		}
		
		action.click(retangles.get(0))
		.click(retangles.get(2))
		.click(retangles.get(5))
		.click(retangles.get(10)).perform();
		
		//Thả phím Ctrl ra
		if (osName.toLowerCase().contains("mac os")) {
			action.keyDown(Keys.COMMAND).perform();
		} else {
			action.keyDown(Keys.CONTROL).perform();
		}
		
		sleepInSecond(5);
		
		Assert.assertEquals(driver.findElements(By.cssSelector(".ui-selectee.ui-selected")).size(), 4);
		
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
