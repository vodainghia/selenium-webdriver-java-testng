package api;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_05_Element_Method_Part_I {
	WebDriver driver;
	
	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Test
	public void TC_00_Web_Element_Command() {
		driver.get("http://live.demoguru99.com/");
		
		//Legend: //** hay dùng, //* ít dùng
		
		//1 - Thao tác trực tiếp lên element
		// Chỉ dùng 1 lần duy nhất + KHÔNG cần khai báo biến
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("selenium_20@gmail.com");
		
		
		//2 - Khai báo biến rồi mới thao tác (action: click/ sendkey/ getText/ select/...)
		//Nếu như muốn thao tác với 1 element thì kiểu dữ liệu của nó phải là: WebElement
		//Nếu như muốn thao tác với nhiều element (>2) thì kiểu dữ liệu của nó phải là: List <WebElement>
		// Dùng nhiều lần thì nên khai báo biến để đỡ viết lại code
		
		WebElement passwordTextBox = driver.findElement(By.xpath("//input[@id='pass']"));
		
		passwordTextBox.sendKeys("1234");
		
		passwordTextBox.clear();
		
		passwordTextBox.sendKeys("123456");
		
		//=================================================
		
		WebElement element = driver.findElement(By.xpath(""));
		
		//Xóa dữ liệu đã nhập trong 1 textbox/ textarea/ dropdown (editable)
		element.clear(); //**
		
		//Nhập dữ liệu đã nhập trong 1 textbox/ textarea/ dropdown (editable)
		element.sendKeys("Nghia Vo"); //**
		
		//Click vào button/ checkbox/ radio button/ link/ dropdown/ image/...
		element.click(); //**
		
		//Lấy ra giá trị nằm trong 1 attribute
		element.getAttribute("tên attribute, e.g.: placeholder"); //**
		
		//Lấy ra style của 1 element (font/ size/ color/ background/... => GUI)
		element.getCssValue("font-size");
		
		//Lấy ra vị trí, kích thước của nó so với trang web
		element.getLocation();
		element.getRect();
		element.getSize();
		
		//Chụp hình lỗi đưa vào report => sang phần Framework
		//element.getScreenshotAs(target);
		
		//Lấy tên thẻ html => đầu vào của 1 element khác (tagname trong xpath)
		element.getTagName();
		//e.g.:
		String emailTextBoxTagName = element.getTagName();
		Assert.assertEquals(emailTextBoxTagName, "input");
		driver.findElement(By.xpath("//" + emailTextBoxTagName + "[@id='email']"));
		
		//Lấy ra text của 1 element bất kỳ (label/ header/ span/ div/...), text KHÔNG nằm trong attribute
		element.getText(); //**
		
		//Kiểm tra xem 1 element đã hiển thị hay ko?
		Assert.assertTrue(element.isDisplayed()); //**
		Assert.assertFalse(element.isDisplayed());
		
		//Kiểm tra xem 1 element đã thao tác được hay ko?
		Assert.assertTrue(element.isEnabled()); //**
		Assert.assertFalse(element.isEnabled());
		
		//Kiểm tra xem 1 element đã được chọn hay chưa? (radio/ checkbox)
		Assert.assertTrue(element.isSelected()); //**
		Assert.assertFalse(element.isSelected());
		
		//submit trong form, chỉ thao tác được trong FORM
		element.submit();
		
		
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
