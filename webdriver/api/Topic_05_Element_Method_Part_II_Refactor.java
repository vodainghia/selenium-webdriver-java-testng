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

public class Topic_05_Element_Method_Part_II_Refactor {
	WebDriver driver;
	
	//Legend: Textbox = Txb, Radio button = Rab, Text area = Txa, Dropdown list = Drl, Checkbox = Chb, Range = Rag
	By emailTxb = By.xpath("//input[@id='mail']");
	By ageUnder18Rab = By.xpath("//input[@id='under_18']");
	By educationTxa = By.xpath("//textarea[@id='edu']");
	By nameText = By.xpath("//h5[text()='Name: User5']");
	By jobRole01Drl = By.xpath("//select[@id='job1']");
	By jobRole02Drl = By.xpath("//select[@id='job2']");
	By interestsDevelopmentChb = By.xpath("//input[@id='development']");
	By slider01Rag = By.xpath("//input[@id='slider-1']");
	By passwordTxb = By.xpath("//input[@id='password']");
	By ageRadioButtonIsDisabledRab = By.xpath("//input[@id='radio-disabled']");
	By biographyTxa = By.xpath("//textarea[@id='bio']");
	By jobRole03Drl = By.xpath("//select[@id='job3']");
	By interestsCheckboxIsDisabledChb = By.xpath("//input[@id='check-disbaled']");
	By slider02Rag = By.xpath("//input[@id='slider-2']");
	By langJavaChb = By.xpath("//input[@id='java']");
	
	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	public boolean isElementDisplayed(By by) {
		WebElement element = driver.findElement(by);
		if (element.isDisplayed()) {
			System.out.println("Element is displayed");
			return true;
		} else {
			System.out.println("Element is not displayed");
			return false;
		}
	}
	
	public boolean isElementEnabled(By by) {
		WebElement element = driver.findElement(by);
		if (element.isEnabled()) {
			System.out.println("Element is enabled");
			return true;
		} else {
			System.out.println("Element is disabled");
			return false;
		}
	}
	
	public boolean isElementSelected(By by) {
		WebElement element = driver.findElement(by);
		if (element.isSelected()) {
			System.out.println("Element is selected");
			return true;
		} else {
			System.out.println("Element is deselected");
			return false;
		}
	}
	
	public void sendKeyToElement(By by, String value) {
		WebElement element = driver.findElement(by);
		element.clear();
		element.sendKeys(value);
	}
	
	public void clickToElement(By by) {
		WebElement element = driver.findElement(by);
		element.click();
	}
	
	@Test
	public void TC_01_Verify_Element_isDisplayed() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		if(isElementDisplayed(emailTxb)) {
			sendKeyToElement(emailTxb, "Automation Testing");
		}
		
		if(isElementDisplayed(ageUnder18Rab)) {
			clickToElement(ageUnder18Rab);
		}
		
		if(isElementDisplayed(educationTxa)) {
			sendKeyToElement(educationTxa, "Automation Testing");
		}
		
		Assert.assertFalse(isElementDisplayed(nameText));
	}
	
	@Test
	public void TC_02_Verify_Element_isEnabled() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		Assert.assertTrue(isElementEnabled(emailTxb));
		
		Assert.assertTrue(isElementEnabled(ageUnder18Rab));
		
		Assert.assertTrue(isElementEnabled(educationTxa));
		
		Assert.assertTrue(isElementEnabled(jobRole01Drl));
		
		Assert.assertTrue(isElementEnabled(jobRole02Drl));
		
		Assert.assertTrue(isElementEnabled(interestsDevelopmentChb));
		
		Assert.assertTrue(isElementEnabled(slider01Rag));
		
		Assert.assertFalse(isElementEnabled(passwordTxb));
		
		Assert.assertFalse(isElementEnabled(ageRadioButtonIsDisabledRab));
		
		Assert.assertFalse(isElementEnabled(biographyTxa));
		
		Assert.assertFalse(isElementEnabled(jobRole03Drl));
		
		Assert.assertFalse(isElementEnabled(interestsCheckboxIsDisabledChb));
		
		Assert.assertFalse(isElementEnabled(slider02Rag));
	}
	
	@Test
	public void TC_03_Verify_Element_isSelected() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		clickToElement(ageUnder18Rab);
		clickToElement(langJavaChb);

		Assert.assertTrue(isElementSelected(ageUnder18Rab));
		Assert.assertTrue(isElementSelected(langJavaChb));
		
		clickToElement(ageUnder18Rab);
		clickToElement(langJavaChb);
		
		Assert.assertTrue(isElementSelected(ageUnder18Rab));
		Assert.assertFalse(isElementSelected(langJavaChb));
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
