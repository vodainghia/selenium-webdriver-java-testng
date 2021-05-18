package api;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.management.RuntimeErrorException;

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

import com.sun.org.apache.bcel.internal.generic.ExceptionThrower;

public class Topic_08_Custom_Dropdown_Part_I {
	WebDriver driver;
	WebDriverWait explicitWait;
	JavascriptExecutor jsExecutor;
	
	String month = "February";
	String[] monthsGroup1 = {"March", "May", "September", "November"};
	String[] monthsGroup2 = {"March", "May", "September"};

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
	
	//@Test
	public void TC_01a_JQuery_BTVN() {
		driver.get("https://jqueryui.com/resources/demos/selectmenu/default.html");
		
		selectItemInCustomDropdown("//span[@id='number-button']", "//ul[@id='number-menu']//div", "19");
		Assert.assertEquals(driver.findElement(By.xpath("//span[@id='number-button']/span[@class='ui-selectmenu-text']")).getText(), "19");
		
	}
	
	//@Test
	public void TC_03_Angular() {
		driver.get("https://ej2.syncfusion.com/angular/demos/?_ga=2.262049992.437420821.1575083417-524628264.1575083417#/material/drop-down-list/data-binding");
		
		selectItemInCustomDropdown("//ejs-dropdownlist[@id='games']", "//ul[@id='games_options']/li", "Basketball");
		sleepInSecond(3);
		//Assert.assertTrue(driver.findElement(By.xpath("//select[@name='games']/option[@selected and text()='Basketball']")).isDisplayed()); //cách này ko xài được vì element có hiện trong DOM nhưng ko hiện lên được, tìm xpath ko thấy highlight
		//Assert.assertEquals(driver.findElement(By.xpath("//select[@name='games']/option[@selected]")).getText(), "Basketball"); //cách này cũng ko xài được cho Angular
		Assert.assertEquals(getAngularDropdownSelectedText(), "Basketball");
		
		selectItemInCustomDropdown("//ejs-dropdownlist[@id='games']", "//ul[@id='games_options']/li", "Snooker");
		sleepInSecond(3);
		Assert.assertEquals(getAngularDropdownSelectedText(), "Snooker");
		
		selectItemInCustomDropdown("//ejs-dropdownlist[@id='games']", "//ul[@id='games_options']/li", "American Football");
		sleepInSecond(3);
		Assert.assertEquals(getAngularDropdownSelectedText(), "American Football");
		
	}
	
	//@Test
	public void TC_04_React() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");
		
		selectItemInCustomDropdown("//div[@role='listbox']", "//div[@role='option']/span", "Christian");
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='divider text']")).getText(), "Christian");
		
		selectItemInCustomDropdown("//div[@role='listbox']", "//div[@role='option']/span", "Stevie Feliciano");
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='divider text']")).getText(), "Stevie Feliciano");
		
		selectItemInCustomDropdown("//div[@role='listbox']", "//div[@role='option']/span", "Jenny Hess");
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='divider text']")).getText(), "Jenny Hess");
	}
	
	//@Test
	public void TC_05_VueJS() {
		driver.get("https://mikerodham.github.io/vue-dropdowns/");
		
		selectItemInCustomDropdown("//li[@class='dropdown-toggle']", "//ul[@class='dropdown-menu']//a", "Second Option");
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='dropdown-toggle']")).getText(), "Second Option");
		
		selectItemInCustomDropdown("//li[@class='dropdown-toggle']", "//ul[@class='dropdown-menu']//a", "First Option");
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='dropdown-toggle']")).getText(), "First Option");
		
		selectItemInCustomDropdown("//li[@class='dropdown-toggle']", "//ul[@class='dropdown-menu']//a", "Third Option");
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='dropdown-toggle']")).getText(), "Third Option");
	}
	
	//@Test
	public void TC_06_Editable() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");
		
		selectItemInEditableDropdown("//input[@class='search']", "//div[@role='option']/span", "Albania");
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='divider text']")).getText(), "Albania");
	
		selectItemInEditableDropdown("//input[@class='search']", "//div[@role='option']/span", "Benin");
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='divider text']")).getText(), "Benin");
		
		selectItemInEditableDropdown("//input[@class='search']", "//div[@role='option']/span", "Angola");
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='divider text']")).getText(), "Angola");
	}
	
	//@Test
	public void TC_07_Editable2() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");
		
		selectItemEditableDropdown("//input[@class='search']", "Albania");
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='divider text']")).getText(), "Albania");
	
		selectItemEditableDropdown("//input[@class='search']", "Benin");
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='divider text']")).getText(), "Benin");
		
		selectItemEditableDropdown("//input[@class='search']", "Angola");
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='divider text']")).getText(), "Angola");
	}
	
	@Test
	public void TC_08_Multiple_Select() {
		driver.get("http://multiple-select.wenzhixin.net.cn/templates/template.html?v=189&url=basic.html");
		
		selectMultipleItemsInCustomDropdown("(//button[@class='ms-choice'])[1]", "(//div[@class='ms-drop bottom'])[1]//li//span", monthsGroup1);
		sleepInSecond(2);
		areItemSelected(monthsGroup1);
		
		driver.navigate().refresh();
		
		selectMultipleItemsInCustomDropdown("(//button[@class='ms-choice'])[1]", "(//div[@class='ms-drop bottom'])[1]//li//span", monthsGroup2);
		sleepInSecond(2);
		areItemSelected(monthsGroup2);
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
	
	public void selectItemInEditableDropdown(String parentXpath, String allItemXpath, String expectedText) {
		driver.findElement(By.xpath(parentXpath)).clear();
		driver.findElement(By.xpath(parentXpath)).sendKeys(expectedText);
		sleepInSecond(1);
		
		List<WebElement> allItem = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(allItemXpath)));
		
		for (WebElement item : allItem) {
			if(item.getText().equals(expectedText)) {
				item.click();
				sleepInSecond(1);
				break;
			}
		}
	}
	
	public void selectItemEditableDropdown(String parentXpath, String expectedText) {
		driver.findElement(By.xpath(parentXpath)).clear();
		driver.findElement(By.xpath(parentXpath)).sendKeys(expectedText);
		sleepInSecond(1);
		
		driver.findElement(By.xpath(parentXpath)).sendKeys(Keys.TAB);
	}
	
	public void selectMultipleItemsInCustomDropdown(String parentXpath, String allItemXpath, String[] months) {
		driver.findElement(By.xpath(parentXpath)).click();
		sleepInSecond(1);
		
		List<WebElement> allItem = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(allItemXpath)));
		
		for (String month : months) {
			for (WebElement item : allItem) {
				if(item.getText().equals(month)) {
					item.click();
					sleepInSecond(1);
					break;
				}
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

	public String getAngularDropdownSelectedText() {
		//return (String) jsExecutor.executeScript("return $(\"select[name='games']>option[selected]\").text"); //cách này cũng chưa dùng được do trang này ko support Jquery mà chỉ có Selenium hỗ trợ
		return (String) jsExecutor.executeScript("return document.querySelector(\"select[name='games']>option[selected]\").text"); //cách này sẽ được chạy ổn hơn do cả Selenium và các trang web đều hỗ trợ
	}
	
	public boolean areItemSelected(String[] months) {
		List<WebElement> itemSelected = driver.findElements(By.xpath("//li[@class='selected']//input"));
		//Lấy ra số lượng đã chọn
		int numberItemSelected = itemSelected.size();
		
		//Text sau khi chọn xong
		String allItemSelected = driver.findElement(By.xpath("(//button[@class='ms-choice']/span)[1]")).getText();
		
		if (numberItemSelected <= 3 && numberItemSelected > 0) {
			for (String item : months) {
				if (allItemSelected.contains(item)) {
					break;
				}
			}
			return true;
		} else if (numberItemSelected > 12) {
			return driver.findElement(By.xpath("//button[@class='ms-choice']/span[text()='All selected']")).isDisplayed();
		} else if (numberItemSelected > 3 && numberItemSelected < 12) {
			return driver.findElement(By.xpath("//button[@class='ms-choice']/span[text()='" + numberItemSelected + " of 12 selected']")).isDisplayed();
		} else {
			return new RuntimeException("No selected month!") != null;
		}
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
