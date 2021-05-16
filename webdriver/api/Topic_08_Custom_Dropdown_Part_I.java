package api;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.server.handler.FindElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_08_Custom_Dropdown_Part_I {
	WebDriver driver;
	WebDriverWait explicitWait;

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		explicitWait = new WebDriverWait(driver, 30); //wait phải được khởi tạo sau driver vì nó cần có driver để truyền vào
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	//@Test
	public void TC_01_JQuery() {
		driver.get("https://jqueryui.com/resources/demos/selectmenu/default.html");
		
		selectItemInCustomDropdown("//span[@id='number-button']", "//ul[@id='number-menu']//div", "13");
		Assert.assertEquals(driver.findElement(By.xpath("//span[@id='number-button']/span[@class='ui-selectmenu-text']")).getText(), "13");
		
		selectItemInCustomDropdown("//span[@id='number-button']", "//ul[@id='number-menu']//div", "1");
		Assert.assertEquals(driver.findElement(By.xpath("//span[@id='number-button']/span[@class='ui-selectmenu-text']")).getText(), "1");
		
		selectItemInCustomDropdown("//span[@id='number-button']", "//ul[@id='number-menu']//div", "19");
		Assert.assertEquals(driver.findElement(By.xpath("//span[@id='number-button']/span[@class='ui-selectmenu-text']")).getText(), "19");
		
		selectItemInCustomDropdown("//span[@id='number-button']", "//ul[@id='number-menu']//div", "5");
		Assert.assertEquals(driver.findElement(By.xpath("//span[@id='number-button']/span[@class='ui-selectmenu-text']")).getText(), "5");
	}
	
	//@Test
	public void TC_02_NopCommerce() {
		driver.get("https://demo.nopcommerce.com/register");
		
		selectItemInCustomDropdown("//select[@name='DateOfBirthDay']", "//select[@name='DateOfBirthDay']/option", "20");
		
		selectItemInCustomDropdown("//select[@name='DateOfBirthMonth']", "//select[@name='DateOfBirthMonth']/option", "February");
		
		selectItemInCustomDropdown("//select[@name='DateOfBirthYear']", "//select[@name='DateOfBirthYear']/option", "1993");
		
		sleepInSecond(5);
	}
	
	@Test
	public void TC_03_JQuery_BTVN() {
		driver.get("https://jqueryui.com/resources/demos/selectmenu/default.html");
		
		selectItemInCustomDropdown("//span[@id='number-button']", "//ul[@id='number-menu']//div", "19");
		Assert.assertEquals(driver.findElement(By.xpath("//span[@id='number-button']/span[@class='ui-selectmenu-text']")).getText(), "19");
		
	}
	
	/* Hành vi của 1 dropdown:
	 	- Click vào dropdown
	 	- Chờ cho các item được hiển thị ra
	 	- Tìm cái item cần chọn: -> Lấy ra text của item mà mình mong muốn -> so sánh với cái mình mong đợi -> bằng nhau
	 		+ Item mình cần nó nằm trong tầm nhìn thấy của User -> click luôn
	 		+ Ko nằm trong tầm nhìn thấy (viewport) -> scroll xuống -> click
	 	- Bấm vào
	 	- Kiểm tra xem chọn đúng hay chưa
	 */
	public void selectItemInCustomDropdown(String parentXpath, String allItemXpath, String expectedText) {
		//Click vào dropdown
		driver.findElement(By.xpath(parentXpath)).click();
		sleepInSecond(1);
		
		//Chờ cho các item được hiển thị ra
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(allItemXpath))); //do đợi cả những li ko thấy trong dropdown nên dùng presence thay vì visible
		
		//Lấy hết tất cả item con đưa vào 1 List để duyệt qua
		List<WebElement> allItem = driver.findElements(By.xpath(allItemXpath));
		
		//Dùng vòng lặp để duyệt qua từng item
		for (WebElement item : allItem) {
			//Duyệt qua từng cái và getText ra, nếu như text bằng với text mong muốn thì dừng lại và click vào item đó và dừng lặp
			if(item.getText().equals(expectedText)) {
				item.click();
				sleepInSecond(1);
				break;
			}
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
