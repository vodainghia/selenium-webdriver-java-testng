package testng;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Topic_05_Data_Provider {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	By emailTextbox = By.xpath("//*[@id='email']");
	By passwordTextbox = By.xpath("//*[@id='pass']");
	By loginButton = By.xpath("//*[@id='send2']");

	@BeforeClass
	public void beforeClass() {
		if (osName.toLowerCase().contains("mac os")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
			System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver.exe");
			System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver.exe");
		}

		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	@Test(dataProvider = "user_pass", enabled = false)
	public void TC_01_LoginToSystem(String username, String password) throws InterruptedException {
		driver.get("http://live.demoguru99.com/index.php/customer/account/login/");

		driver.findElement(emailTextbox).sendKeys(username);
		driver.findElement(passwordTextbox).sendKeys(password);
		driver.findElement(loginButton).click();
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='col-1']//p")).getText().contains(username));
		driver.findElement(By.xpath("//header[@id='header']//span[text()='Account']")).click();
		driver.findElement(By.xpath("//a[text()='Log Out']")).click();

		driver.get("http://live.demoguru99.com/index.php/customer/account/login/");
	}

	@Test(dataProvider = "register")
	public void TC_02_RegisterToSystem(String email, String password) {
		driver.get("http://live.demoguru99.com/index.php/customer/account/login/");
		driver.findElement(By.xpath("//span[text()='Create an Account']")).click();

		driver.findElement(By.id("firstname")).sendKeys("John");
		driver.findElement(By.id("lastname")).sendKeys("Doe");
		driver.findElement(By.id("email_address")).sendKeys(email);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("confirmation")).sendKeys(password);
		driver.findElement(By.xpath("//button[@title='Register']")).click();

		driver.findElement(By.xpath("//header[@id='header']//span[text()='Account']")).click();
		driver.findElement(By.xpath("//a[text()='Log Out']")).click();

	}

	@DataProvider(name = "user_pass")
	public String[][] UserAndPasswordData() {
		return new String[][] { { "selenium_11_01@gmail.com", "111111" }, { "selenium_11_02@gmail.com", "111111" },
				{ "selenium_11_03@gmail.com", "111111" } };
	}

	@DataProvider(name = "register")
	public String[][] RegisterData() {
		return new String[][] { { "Nghia_autotest" + getRandomNumber() + "@gmail.com", "111111" },
				{ "Nghia_autotest" + getRandomNumber() + "@gmail.com", "111111" },
				{ "Nghia_autotest" + getRandomNumber() + "@gmail.com", "111111" },
				{ "Nghia_autotest" + getRandomNumber() + "@gmail.com", "111111" }, };
	}

	public int getRandomNumber() {
		Random rand = new Random();
		return rand.nextInt(9999);
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
