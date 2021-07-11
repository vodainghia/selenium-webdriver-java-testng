package testng;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Topic_07_Loop {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	By emailTextbox = By.xpath("//*[@id='email']");
	By passwordTextbox = By.xpath("//*[@id='pass']");
	By loginButton = By.xpath("//*[@id='send2']");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver.exe");

		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	@Test(dataProvider = "register", invocationCount = 10)
	public void RegisterToSystem(String email, String password) {
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

	@DataProvider(name = "register")
	public String[][] RegisterData() {
		return new String[][] { { "Nghia_autotest" + getRandomNumber() + "@gmail.com", "111111" } };
	}

	public int getRandomNumber() {
		Random rand = new Random();
		return rand.nextInt(999999);
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
