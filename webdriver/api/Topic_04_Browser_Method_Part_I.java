package api;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_04_Browser_Method_Part_I {
	WebDriver driver;
	
	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
	}
	
	@Test
	public void TC_00_Browser_Element_Commnad() {
		//Các gàm/ method/ command để tương tác với web browser thì sẽ qua driver.hàm()
		//Các gàm/ method/ command để tương tác với web element thì sẽ qua driver.findElement(By.x)
		
		//Legend: //** hay dùng, //* ít dùng
		
		driver.get("http://live.demoguru99.com/"); //**
		
		//Đóng current tab, nếu như chỉ có 1 tab thì = đóng trình duyệt
		//driver.close();
		
		//Đóng trình duyệt
		//driver.quit();
		
		//Mở ra trang Mobile
		driver.findElement(By.xpath("//a[text()='Mobile']")).click();
		
		//Kiểm tra Url của page mới mở ra xem có đúng hay ko?
		System.out.println(driver.getCurrentUrl());
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.demoguru99.com/index.php/mobile.html");
		
		//Lấy ra title của page hiện tại
		driver.getTitle(); //*
		
		//Lấy ra source code của page hiện tại
		driver.getPageSource();
		
		//Lấy ra ID của tab/ window nó đang đứng (active)
		driver.getWindowHandle(); //**
		
		//Lấy ra ID của tất cả tab/ window
		driver.getWindowHandles(); //**
		
		//wait 30s, 1s = 1000ms
		//implicitlyWait: là chờ cho element xuất hiện (findElement/ findElements) để tương tác trong vòng x (đơn vị thời gian)
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); //**
		driver.manage().timeouts().implicitlyWait(30000, TimeUnit.MILLISECONDS);
		
		//chờ cho page load xong trong vòng x (đơn vị thời gian)
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		
		//chờ cho 1 đoạn script của JS Executor thực thi xong trong vòng x (đơn vị thời gian)
		driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		
		//Phóng to browser thành full size
		driver.manage().window().maximize(); //**
		
		//Phóng to browser thành tương đương bấm F11
		driver.manage().window().fullscreen();
		
		//Kích thước của trình duyệt: test responsive
		//setSize
		//getSize
		
		//Vị trí của trình duyệt so với độ pbân giải màn hình hiện tại
		//setPosition
		//getPosition
		
		//Back lại trang trước đó
		driver.navigate().back(); //*
		
		//Đi tiếp trang sau đó
		driver.navigate().forward(); //*
		
		//Tải lại trang: F5/ Reload
		driver.navigate().refresh(); //*
		
		//Mở ra 1 URL: tracking history tốt hơn
		driver.navigate().to(""); //*
		
		//Thao tác với Alert
		driver.switchTo().alert(); //**
		
		//Thao tác với Frame/ Iframe
		driver.switchTo().frame(0); //**
		
		//Thao tác với Window/ Tab
		driver.switchTo().window(""); //**
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
