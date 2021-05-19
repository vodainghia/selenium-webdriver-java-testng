package api;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_07_Default_Dropdown {
	WebDriver driver;
	JavascriptExecutor jsExecutor;
	Select select; // new trong @Test, vì nó ko truyền driver mà truyền element
	// explicitWait, jsExecutor, action thì new trong @BeforeClass vì nó truyền
	// driver
	String firstName, lastName, emailAddress, companyName, password;
	String date, month, year;

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		
		jsExecutor = (JavascriptExecutor) driver; //JavascriptExecutor là interface trong khi driver là class, nên chi cần ép kiểu của driver thành interface, ép kiểu tường minh 

		firstName = "John";
		lastName = "Wick";
		companyName = "Hollywood";
		password = "123456";

		date = "31";
		month = "June";
		year = "1999";
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://demo.nopcommerce.com/");

	}

	@Test(invocationCount = 5)
	public void TC_01_Register() {
		/* 1 - Mở ra trang Register */
		driver.findElement(By.cssSelector(".ico-register")).click();

		/* 2 - Điền thông tin vào các field required */
		checkToCheckboxOrRadio(By.cssSelector("#gender-male"));
		driver.findElement(By.cssSelector("#FirstName")).sendKeys(firstName);
		driver.findElement(By.cssSelector("#LastName")).sendKeys(lastName);

		// Khởi tạo biến select để thao tác với dropdown Day
		select = new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthDay']")));

		// Sử dụng index: đếm từ 1 trở lên, ví dụ chọn ngày 30 thì theo dropdown đó sẽ
		// là index 31
		// select.selectByIndex(31);

		// Sử dụng value, là att trong html tag: chọn ngày 15
		// select.selectByValue("15");

		// Sử dụng text: chọn ngày 30 ==> Nên sử dụng text
		select.selectByVisibleText(date);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), date);
		Assert.assertEquals(select.getOptions().size(), 32);

		// Khởi tạo biến select để thao tác với dropdown Month
		select = new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthMonth']")));
		select.selectByVisibleText(month);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), month);
		Assert.assertEquals(select.getOptions().size(), 13);

		// Khởi tạo biến select để thao tác với dropdown Year
		select = new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthYear']")));
		select.selectByVisibleText(year);

		// Kiểm tra dropdown này có phải là multiple hay ko?
		// Assert.assertFalse(select.isMultiple()); //assertFalse tức là ko hỗ trợ
		// multiple

		// Bỏ chọn tất cả option trong dropdown đó
		// select.deselectAll();

		// Lấy ra những options đã được chọn, dùng để kết hợp khi muốn verify lại các
		// options mình đã chọn
		// List<WebElement> itemSelected = select.getAllSelectedOptions();

		// Trả về text đã chọn thành công
		Assert.assertEquals(select.getFirstSelectedOption().getText(), year); // **
		Assert.assertEquals(select.getOptions().size(), 112);

		// Lấy ra tất cả các item trong dropdown đó
		//List<WebElement> allItems = select.getOptions();

		// Kiểm tra số lượng item có bằng với mong muốn hay không?
		//Assert.assertEquals(allItems.size(), 112);
		/*
		 * for(WebElement item : allItems) { System.out.println(item.getText()); }
		 */

		emailAddress = "johnwick" + getRandomNumber() + "@nomail.com";
		
		driver.findElement(By.cssSelector("#Email")).sendKeys(emailAddress);
		driver.findElement(By.cssSelector("#Company")).sendKeys(companyName);
		checkToCheckboxOrRadio(By.cssSelector("#Newsletter"));
		driver.findElement(By.cssSelector("#Password")).sendKeys(password);
		driver.findElement(By.cssSelector("#ConfirmPassword")).sendKeys(password);

		/* 3 - Đăng ký */
		//driver.findElement(By.cssSelector("#register-button")).click();//do trang Nop hay bị lỗi, sẽ TẠM THỜI dùng js để click thay vì selenium, QUA FRAMEWORK SẼ TỐI ƯU KIỂU KHÁC
		
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(By.cssSelector("#register-button")));//click bằng js sẽ luôn affect

		/* 4 - Kiểm tra xuất hiện message đăng ký thành công */
		Assert.assertEquals(driver.findElement(By.cssSelector(".result")).getText(), "Your registration completed");

		/* 5 - Vào trang My Account */
		//driver.findElement(By.cssSelector(".ico-account")).click();

		jsExecutor.executeScript("arguments[0].click();", driver.findElement(By.cssSelector(".ico-account")));
		
		/* 6 - Kiểm tra đúng với thông tin đăng ký */
		Assert.assertTrue(driver.findElement(By.cssSelector("#gender-male")).isSelected());
		Assert.assertEquals(driver.findElement(By.cssSelector("#FirstName")).getAttribute("value"), firstName);
		Assert.assertEquals(driver.findElement(By.cssSelector("#LastName")).getAttribute("value"), lastName);
		Assert.assertEquals(driver.findElement(By.cssSelector("#Email")).getAttribute("value"), emailAddress);
		Assert.assertEquals(driver.findElement(By.cssSelector("#Company")).getAttribute("value"), companyName);
		Assert.assertTrue(driver.findElement(By.cssSelector("#Newsletter")).isSelected());
		
		//sau khi click register thì nó đã chuyển qua một trang html khác rồi nên bắt buộc phải tìm element lại => lỗi ko tìm thấy element
		select = new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthDay']")));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), date);
		Assert.assertEquals(select.getOptions().size(), 32);
		
		select = new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthMonth']")));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), month);
		Assert.assertEquals(select.getOptions().size(), 13);
		
		select = new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthYear']")));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), year);
		Assert.assertEquals(select.getOptions().size(), 112);

		driver.findElement(By.cssSelector(".ico-logout")).click();
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public void checkToCheckboxOrRadio(By by) {
		WebElement element = driver.findElement(by);
		if (!element.isSelected()) {
			element.click();
		}
	}

	public void uncheckToCheckbox(By by) {
		WebElement element = driver.findElement(by);
		if (element.isSelected()) {
			element.click();
		}
	}

	public void sleepInSecond(long second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public int getRandomNumber() {
		Random rand = new Random();
		return rand.nextInt(99999);
	}
}
