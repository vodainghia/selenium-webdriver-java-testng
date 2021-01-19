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
		//Mo trinh duyet
		driver = new FirefoxDriver();
		
		//Mo app
		driver.get("https://www.google.com/?gws_rd=ssl");
	}
	
	@Test
	public void sample_testcase() {
	
	}
  
	@AfterClass
	public void afterClass() {
		//Dong trinh duyet
		driver.quit();
	}

}
