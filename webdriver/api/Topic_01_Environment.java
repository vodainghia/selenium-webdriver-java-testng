package api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;

public class Topic_01_Environment {
	WebDriver driver;
	String projectLocation = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	
	
	@BeforeClass
	public void beforeClass() {
		//driver = new FirefoxDriver(); //Không cần nếu FF ver <= 47
		
		System.out.println(projectLocation + "\\browserDrivers\\chromedriver.exe");
		
		System.out.println(osName);
	}
	
	@Test
	public void TC_00_Run_Demo_On_Chrome() {
		//Cách 1: set đường dẫn cứng đến chromedriver, cách này ko thể dùng khi làm việc nhóm vì đường dẫn chỉ dùng trên 1 máy
		//System.setProperty("webdriver.chrome.driver", "D:\\IT\\selenium-webdriver-java\\02 - Selenium API\\selenium-webdriver-java-testng\\browserDrivers\\chromedriver.exe");
		
		//Cách 2: Dùng được trên tất cả máy trong team, nhưng đọc đường dẫn sẽ khó hiểu ở chỗ dấu "." đại diện cho project location
		//System.setProperty("webdriver.chrome.driver", ".\\browserDrivers\\chromedriver.exe");
		
		//Cách 3: Dùng được trên tất cả máy trong team, đọc đường dẫn dễ hiểu hơn
		System.setProperty("webdriver.chrome.driver", projectLocation + "\\browserDrivers\\chromedriver.exe");
		
	}
	
	@Test
	public void TC_01_Run_On_Firefox() {
		driver = new FirefoxDriver();
		driver.get("https://demo.nopcommerce.com");
		driver.quit();
	}
	
	@Test
	public void TC_02_Run_On_Chrome_Windows() {
		System.setProperty("webdriver.chrome.driver", projectLocation + "\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://demo.nopcommerce.com");
		driver.quit();
	}
	
	@Test
	public void TC_03_Run_On_Chrome_MAC() {
		//Step 1
		//Step 2: Set permission for chromedriver
		System.setProperty("webdriver.chrome.driver", projectLocation + "//browserDrivers//chromedriver");
		driver = new ChromeDriver();
		driver.get("https://demo.nopcommerce.com");
		driver.quit();
	}
	
	@Test
	public void TC_04_Run_On_Edge_Windows() {
		System.setProperty("webdriver.edge.driver", projectLocation + "\\browserDrivers\\chromedriver.exe");
		driver = new EdgeDriver();
		driver.get("https://demo.nopcommerce.com");
		driver.quit();
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
