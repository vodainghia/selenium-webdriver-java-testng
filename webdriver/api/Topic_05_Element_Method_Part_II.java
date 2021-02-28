package api;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_05_Element_Method_Part_II {
	private static final WebDriver WebDriver = null;
	WebDriver driver;
	
	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Test
	public void TC_01_Verify_Element_isDisplayed() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		//Legend: Textbox = Txb, Radio button = Rab, Text area = Txa, Dropdown list = Drl, Checkbox = Chb, Range = Rag 
		WebElement emailTxb = driver.findElement(By.xpath("//input[@id='mail']"));
		WebElement ageUnder18Rab = driver.findElement(By.xpath("//input[@id='under_18']"));
		WebElement educationTxa = driver.findElement(By.xpath("//textarea[@id='edu']"));
		
		Assert.assertTrue(emailTxb.isDisplayed());
		
		Assert.assertTrue(ageUnder18Rab.isDisplayed());
		
		Assert.assertTrue(educationTxa.isDisplayed());
		
		if (emailTxb.isDisplayed()) {
			
			emailTxb.sendKeys("Automation Testing");
			
			System.out.println("Email textbox: có hiển thị");
			
		} else {
			System.out.println("Email textbox: không hiển thị");
		}
		
		if (ageUnder18Rab.isDisplayed()) {
			
			ageUnder18Rab.click();
			
			System.out.println("Age under 18 radio button: có hiển thị");
			
		} else {
			System.out.println("Age under 18 radio button: không hiển thị");
		}
		
		if (educationTxa.isDisplayed()) {
			
			educationTxa.sendKeys("Automation Testing");
			
			System.out.println("educationTxa textarea: có hiển thị");
			
		} else {
			System.out.println("educationTxa textarea: không hiển thị");
		}
	}
	
	@Test
	public void TC_02_Verify_Element_isEnabled() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		//Legend: Textbox = Txb, Radio button = Rab, Text area = Txa, Dropdown list = Drl, Checkbox = Chb, Range = Rag 
		WebElement emailTxb = driver.findElement(By.xpath("//input[@id='mail']"));
		WebElement ageUnder18Rab = driver.findElement(By.xpath("//input[@id='under_18']"));
		WebElement educationTxa = driver.findElement(By.xpath("//textarea[@id='edu']"));
		WebElement jobRole01Drl = driver.findElement(By.xpath("//select[@id='job1']"));
		WebElement jobRole02Drl = driver.findElement(By.xpath("//select[@id='job2']"));
		WebElement interestsDevelopmentChb = driver.findElement(By.xpath("//input[@id='development']"));
		WebElement slider01Rag = driver.findElement(By.xpath("//input[@id='slider-1']"));
		WebElement passwordTxb = driver.findElement(By.xpath("//input[@id='password']"));
		WebElement ageRadioButtonIsDisabledRab = driver.findElement(By.xpath("//input[@id='radio-disabled']"));
		WebElement biographyTxa = driver.findElement(By.xpath("//textarea[@id='bio']"));
		WebElement jobRole03Drl = driver.findElement(By.xpath("//select[@id='job3']"));
		WebElement interestsCheckboxIsDisabledChb = driver.findElement(By.xpath("//input[@id='check-disbaled']"));
		WebElement slider02Rag = driver.findElement(By.xpath("//input[@id='slider-2']"));
		
		Assert.assertTrue(emailTxb.isEnabled());
		
		Assert.assertTrue(ageUnder18Rab.isEnabled());
		
		Assert.assertTrue(educationTxa.isEnabled());
		
		Assert.assertTrue(jobRole01Drl.isEnabled());
		
		Assert.assertTrue(jobRole02Drl.isEnabled());
		
		Assert.assertTrue(interestsDevelopmentChb.isEnabled());
		
		Assert.assertTrue(slider01Rag.isEnabled());
		
		Assert.assertFalse(passwordTxb.isEnabled());
		
		Assert.assertFalse(ageRadioButtonIsDisabledRab.isEnabled());
		
		Assert.assertFalse(biographyTxa.isEnabled());
		
		Assert.assertFalse(jobRole03Drl.isEnabled());
		
		Assert.assertFalse(interestsCheckboxIsDisabledChb.isEnabled());
		
		Assert.assertFalse(slider02Rag.isEnabled());
		
		if (emailTxb.isEnabled()) {
			System.out.println("Email textbox: có cho phép");
		} else {
			System.out.println("Email textbox: không cho phép");
		}
		
		if (ageUnder18Rab.isEnabled()) {
			System.out.println("Age Under 18 Radio button: có cho phép");
		} else {
			System.out.println("Age Under 18 Radio button: không cho phép");
		}
		
		if (educationTxa.isEnabled()) {
			System.out.println("Education Text area: có cho phép");
		} else {
			System.out.println("Education Text area: không cho phép");
		}
		
		if (jobRole01Drl.isEnabled()) {
			System.out.println("Job Role 01 radio: có cho phép");
		} else {
			System.out.println("Job Role 01 radio: không cho phép");
		}
		
		if (jobRole02Drl.isEnabled()) {
			System.out.println("Job Role 02 radio: có cho phép");
		} else {
			System.out.println("Job Role 02 radio: không cho phép");
		}
		
		if (interestsDevelopmentChb.isEnabled()) {
			System.out.println("Interests Development Checkbox: có cho phép");
		} else {
			System.out.println("Interests Development Checkbox: không cho phép");
		}
		
		if (slider01Rag.isEnabled()) {
			System.out.println("Slider 01: có cho phép");
		} else {
			System.out.println("Slider 01: không cho phép");
		}
		
		if (passwordTxb.isEnabled()) {
			System.out.println("Password textbox: có cho phép");
		} else {
			System.out.println("Password textbox: không cho phép");
		}
		
		if (ageRadioButtonIsDisabledRab.isEnabled()) {
			System.out.println("Age Radio Button Is Disabled: có cho phép");
		} else {
			System.out.println("Age Radio Button Is Disabled: không cho phép");
		}
		
		if (biographyTxa.isEnabled()) {
			System.out.println("Biography Text area: có cho phép");
		} else {
			System.out.println("Biography Text area: không cho phép");
		}
		
		if (jobRole03Drl.isEnabled()) {
			System.out.println("Job Role 03 Radio: có cho phép");
		} else {
			System.out.println("Job Role 03 Radio: không cho phép");
		}
		
		if (interestsCheckboxIsDisabledChb.isEnabled()) {
			System.out.println("Interests Checkbox Is Disabled: có cho phép");
		} else {
			System.out.println("Interests Checkbox Is Disabled: không cho phép");
		}
		
		if (slider02Rag.isEnabled()) {
			System.out.println("Slider 02: có cho phép");
		} else {
			System.out.println("Slider 02: không cho phép");
		}
		
	}
	
	@Test
	public void TC_03_Verify_Element_isSelected() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		WebElement ageUnder18Rab = driver.findElement(By.xpath("//input[@id='under_18']"));
		WebElement langJavaChb = driver.findElement(By.xpath("//input[@id='java']"));
		
		ageUnder18Rab.click();
		
		langJavaChb.click();
		
		Assert.assertTrue(ageUnder18Rab.isSelected());
		
		Assert.assertTrue(langJavaChb.isSelected());
		
		langJavaChb.click();
		
		Assert.assertFalse(langJavaChb.isSelected());
		
		if (ageUnder18Rab.isSelected()) {
			System.out.println("Age Under 18 Radio button: đã được chọn");
		} else {
			System.out.println("Age Under 18 Radio button: không được chọn");
		}
		
		if (langJavaChb.isSelected()) {
			System.out.println("Languages: Java checkbox: đã được chọn");
		} else {
			System.out.println("Languages: Java checkbox: không được chọn");
		}
		
	}
	
	@Test
	public void TC_04_Register_func_at_MailChimp() throws InterruptedException  {
		driver.get("https://login.mailchimp.com/signup/");
		
		WebElement password = driver.findElement(By.xpath("//input[@id='new_password']"));
		WebElement createAccButtonBtn = driver.findElement(By.xpath("//button[@id='create-account']"));
		
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("nghiatest01@gmail.com");
		
		driver.findElement(By.xpath("//input[@id='new_username']")).sendKeys("Nghiatest");
		
		/* Lowercase */
		password.sendKeys("auto");
		Thread.sleep(1000);
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='lowercase-char completed' and text()='One lowercase character']")).isDisplayed());
		Assert.assertFalse(createAccButtonBtn.isEnabled());
		
		/* Uppercase */
		password.clear();
		password.sendKeys("Auto");
		Thread.sleep(1000);
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='uppercase-char completed' and text()='One uppercase character']")).isDisplayed());
		Assert.assertFalse(createAccButtonBtn.isEnabled());
		
		/* Number */
		password.clear();
		password.sendKeys("123456");
		Thread.sleep(1000);
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='number-char completed' and text()='One number']")).isDisplayed());
		Assert.assertFalse(createAccButtonBtn.isEnabled());
		
		/* Special */
		password.clear();
		password.sendKeys("!!!@@@");
		Thread.sleep(1000);
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='special-char completed' and text()='One special character']")).isDisplayed());
		Assert.assertFalse(createAccButtonBtn.isEnabled());
		
		/* Max-length */
		password.clear();
		password.sendKeys("Automation");
		Thread.sleep(1000);
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char completed' and text()='8 characters minimum']")).isDisplayed());
		Assert.assertFalse(createAccButtonBtn.isEnabled());
		
		/* Valid */
		password.clear();
		password.sendKeys("Auto123!@#");
		Thread.sleep(1000);
		Assert.assertTrue(createAccButtonBtn.isEnabled());
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
