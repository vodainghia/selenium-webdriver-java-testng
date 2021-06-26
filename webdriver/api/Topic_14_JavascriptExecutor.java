package api;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_14_JavascriptExecutor {
	//interface hoặc abtract class ko có new được, class thường thì new được. WebDriver và jsExecutor là interface
	//class mà kế thừa interface thì gọi là implements, 1 class được kế thừa nhiều interface (đa kế thừa)
	//class mà kế thừa class thì gọi là extends, 1 class chỉ kế thừa được 1 class (đơn kế thừa)
	
	WebDriver driver; //interface, interface hoặc abtract class ko có new được, class thường thì new được
	JavascriptExecutor jsExecutor; //interface
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	WebDriverWait explicitWait;
	
	@BeforeClass
	public void beforeClass() {
		if (osName.toLowerCase().contains("mac os")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
			System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver.exe");
			System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver.exe");
		}
		
		driver = new ChromeDriver(); //sau khi new sẽ có 1 ID, ID đó sẽ được ép vô (JavascriptExecutor) phía dưới
		//driver = new FirefoxDriver();
		jsExecutor = (JavascriptExecutor) driver; //do ko thể khởi tạo bằng new nên có thể dùng 1 cách khác là ép kiểu thông minh, ép driver thành JavascriptExecutor
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		explicitWait = new WebDriverWait(driver, 10);
	}
	
	//@Test
	public void TC_01_Live_Guru() {
		navigateToUrlByJS("http://live.demoguru99.com/");

		String liveGuruDomain = (String) executeForBrowser("return document.domain;");
		Assert.assertEquals(liveGuruDomain, "live.demoguru99.com");
		
		String liveGuruUrl = (String) executeForBrowser("return document.URL;");
		Assert.assertEquals(liveGuruUrl, "http://live.demoguru99.com/");
		
		clickToElementByJS("//a[text()='Mobile']");
		
		clickToElementByJS("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']/button");
		
		Assert.assertTrue(isExpectedTextInInnerText("Samsung Galaxy was added to your shopping cart."));
		
		clickToElementByJS("//a[text()='Customer Service']");
		
		scrollToElement("//input[@id='newsletter']");
		
		sendkeyToElementByJS("//input[@id='newsletter']", getRandomEmail());
		
		clickToElementByJS("//button[@title='Subscribe']");
		
		Assert.assertTrue(isExpectedTextInInnerText("Thank you for your subscription."));
		
		navigateToUrlByJS("http://demo.guru99.com/v4/");
		explicitWait.until(ExpectedConditions.urlToBe("http://demo.guru99.com/v4/"));
		
		Assert.assertEquals((String) executeForBrowser("return document.domain;"), "demo.guru99.com");
	}
	
	//@Test
	public void TC_02_Validation_Message() {
		navigateToUrlByJS("https://automationfc.github.io/html5/index.html");
		
		clickToElementByJS("//input[@name='submit-btn']");
		sleepInSecond(1);
		
		Assert.assertEquals(getElementValidationMessage("//input[@id='fname']"), "Please fill out this field.");
		
		sendkeyToElementByJS("//input[@id='fname']", "Automation FC");
		sleepInSecond(1);
		
		clickToElementByJS("//input[@name='submit-btn']");
		sleepInSecond(1);
		
		Assert.assertEquals(getElementValidationMessage("//input[@id='pass']"), "Please fill out this field.");
		
		sendkeyToElementByJS("//input[@id='pass']", "123456");
		sleepInSecond(1);
		
		clickToElementByJS("//input[@name='submit-btn']");
		sleepInSecond(1);
		
		Assert.assertEquals(getElementValidationMessage("//input[@id='em']"), "Please fill out this field.");
		
		sendkeyToElementByJS("//input[@id='em']", "123");
		sleepInSecond(1);
		
		clickToElementByJS("//input[@name='submit-btn']");
		sleepInSecond(1);
		
		Assert.assertEquals(getElementValidationMessage("//input[@id='em']"), "Please include an '@' in the email address. '123' is missing an '@'.");
		
		sendkeyToElementByJS("//input[@id='em']", "123@234");
		sleepInSecond(1);
		
		clickToElementByJS("//input[@name='submit-btn']");
		sleepInSecond(1);
		
		Assert.assertEquals(getElementValidationMessage("//input[@id='em']"), "Please match the requested format.");
		
		sendkeyToElementByJS("//input[@id='em']", "123@234.567");
		sleepInSecond(1);
		
		clickToElementByJS("//input[@name='submit-btn']");
		sleepInSecond(1);
		
		Assert.assertEquals(getElementValidationMessage("//select"), "Please select an item in the list.");
	}
	
	//@Test
	public void TC_03_New_Customer() {
		navigateToUrlByJS("http://demo.guru99.com/v4");
		
		String loginPageUrl, userID, password;
		String name, dob, address, city, state, pin, phone, email;
		
		By nameTextboxBy = By.name("name");
		By dobTextboxBy = By.name("dob");
		By addressTextAreaBy = By.name("addr");
		By cityTextboxBy = By.name("city");
		By stateTextboxBy = By.name("state");
		By pinTextboxBy = By.name("pinno");
		By phoneTextboxBy = By.name("telephoneno");
		By emailTextboxBy = By.name("emailid");
		By passwordTextboxBy = By.name("password");
		
		name = "John Kennedy";
		dob = "1960-01-01";
		address = "564 Suitable Address";
		city = "New York";
		state = "California";
		pin = "999666";
		phone = "0999666999";
		email = getRandomEmail();
		
		loginPageUrl = driver.getCurrentUrl();

		driver.findElement(By.xpath("//a[text()='here']")).click();

		driver.findElement(By.xpath("//input[@name='emailid']")).sendKeys(email);

		driver.findElement(By.xpath("//input[@name='btnLogin']")).click();

		userID = driver.findElement(By.xpath("//td[text()='User ID :']/following-sibling::td")).getText();
		password = driver.findElement(By.xpath("//td[text()='Password :']/following-sibling::td")).getText();
		
		driver.get(loginPageUrl);

		driver.findElement(By.xpath("//input[@name='uid']")).sendKeys(userID);
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
		driver.findElement(By.xpath("//input[@name='btnLogin']")).click();

		Assert.assertTrue(driver.findElement(By.xpath("//td[text()='Manger Id : " + userID + "']")).isDisplayed());
		
		driver.findElement(By.xpath("//a[text()='New Customer']")).click();

		driver.findElement(nameTextboxBy).sendKeys(name);
		
		//REMOVE DATE PICKER
		removeAttributeInDOM("//input[@name='dob']", "type");
		sleepInSecond(1);
		
		driver.findElement(dobTextboxBy).sendKeys(dob);
		driver.findElement(addressTextAreaBy).sendKeys(address);
		driver.findElement(cityTextboxBy).sendKeys(city);
		driver.findElement(stateTextboxBy).sendKeys(state);
		driver.findElement(pinTextboxBy).sendKeys(pin);
		driver.findElement(phoneTextboxBy).sendKeys(phone);
		driver.findElement(emailTextboxBy).sendKeys(email);
		driver.findElement(passwordTextboxBy).sendKeys(password);
		driver.findElement(By.name("sub")).click();

		Assert.assertTrue(driver.findElement(By.xpath("//p[text()='Customer Registered Successfully!!!']")).isDisplayed());
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText(), name);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Birthdate']/following-sibling::td")).getText(), dob);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(), address);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(), city);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(), state);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(), pin);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(), phone);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(), email);
	}
	
	@Test
	public void TC_04_Register() {
		navigateToUrlByJS("http://live.demoguru99.com/");
		
		clickToElementByJS("//div[@id='header-account']//a[text()='My Account']");
		
		
	}
	
	public void sleepInSecond(long second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public String getRandomEmail() {
		Random rand = new Random();
		return "johndeep" + rand.nextInt(9999) + "@hotmail.com";
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	
	/*
	//Commond JavascriptExecutor function
	*/
	
	public Object executeForBrowser(String javaScript) { //public object vì return object
		return jsExecutor.executeScript(javaScript);
	}

	public String getInnerText() {
		return (String) jsExecutor.executeScript("return document.documentElement.innerText;"); //có return trong script là vì đoạn script chỉ lấy dữ liệu ra, còn muốn trả về thì phải thêm return vô
	}

	public boolean isExpectedTextInInnerText(String textExpected) {
		String textActual = (String) jsExecutor.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0];");
		return textActual.equals(textExpected);
	}

	public void scrollToBottomPage() {
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public void navigateToUrlByJS(String url) {
		jsExecutor.executeScript("window.location = '" + url + "'");
	}

	public void highlightElement(String locator) {
		WebElement element = getElement(locator);
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", "border: 2px solid red; border-style: dashed;");
		sleepInSecond(1);
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
	}

	public void clickToElementByJS(String locator) {
		jsExecutor.executeScript("arguments[0].click();", getElement(locator));
	}

	public void scrollToElement(String locator) {
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getElement(locator));
	}

	public void sendkeyToElementByJS(String locator, String value) {
		jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", getElement(locator));
	}

	public void removeAttributeInDOM(String locator, String attributeRemove) {
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getElement(locator));
	}

	public String getElementValidationMessage(String locator) {
		return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", getElement(locator));
	}

	public boolean isImageLoaded(String locator) {
		boolean status = (boolean) jsExecutor.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", getElement(locator));
		return status;
	}

	public WebElement getElement(String locator) {
		return driver.findElement(By.xpath(locator));
	}

}
