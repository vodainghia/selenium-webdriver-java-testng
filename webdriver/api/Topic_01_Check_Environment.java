package api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;

public class Topic_01_Check_Environment {
	WebDriver driver;
	
	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.get("https://www.google.com/?gws_rd=ssl");
	}
	
	@Test
	public void sample_testcase() {
		//test new laptop
	
	}
  
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
