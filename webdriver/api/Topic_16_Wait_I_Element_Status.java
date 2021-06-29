package api;

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

public class Topic_16_Wait_I_Element_Status {
	WebDriver driver;
	WebDriverWait explicitWait;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	
	
	@BeforeClass
	public void beforeClass() {
		if (osName.toLowerCase().contains("mac os")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
			System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver.exe");
			System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver.exe");
		}
		
		//driver = new ChromeDriver();
		driver = new FirefoxDriver();
		//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		explicitWait = new WebDriverWait(driver, 15);
	}
	
	@Test //Hay gặp nhất
	public void TC_01_Visible_Displayed() {
		driver.get("https://www.facebook.com/");
		
		/*
		 * Hành động wait được thực hiện trước các hành động Action và Verify 
		 * Điều kiện của Hiển thị (displayed/visible/visibility): phải hiển thị trên UI và có trong DOM
		 */
		
		//Wait cho 1 element hiển thị trong khoảng thời gian 15s đã được set phía trên (explicitWait)
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='email']")));
		
		//Verify cho 1 element hiển thị
		Assert.assertTrue(driver.findElement(By.xpath("//input[@id='email']")).isDisplayed());
		
		
	}
	
	@Test //Hay gặp nhất
	public void TC_02_Invisible_Undisplayed() {
		driver.get("https://www.facebook.com/");
		
		/*
		 * Điều kiện của KHÔNG Hiển thị : KHÔNG hiển thị trên UI (bắt buộc) và: có thể có hoặc không có trong DOM
		 */
		
		//Wait cho button tạo tài khoản có thể click được
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Tạo tài khoản mới']")));
		
		//Action
		driver.findElement(By.xpath("//a[text()='Tạo tài khoản mới']")).click();
		
		//1. KHÔNG có trên UI nhưng vẫn có trong DOM
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//input[@name='reg_email_confirmation__']")));
		
		//==============================================
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Đăng ký']/parent::div/preceding-sibling::img")));
		driver.findElement(By.xpath("//div[text()='Đăng ký']/parent::div/preceding-sibling::img")).click();
		
		//2. KHÔNG có trên UI và vẫn có trong DOM
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//form[@id='reg']")));
		
	}
	
	@Test
	public void TC_03_Presence() {
		driver.get("https://www.facebook.com/");
		
		/*
		 * Điều kiện của Presence là: phải có trong DOM, có thể có hiện hoặc không hiện trên UI
		 */
		
		//Có trong DOM và hiển thị trên UI -> Pass
		explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='email']")));
		
		//Có trong DOM và KHÔNG hiển thị trên UI -> Pass
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Tạo tài khoản mới']")));
		driver.findElement(By.xpath("//a[text()='Tạo tài khoản mới']")).click();
		
		explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='reg_email_confirmation__']")));
		
		//KHÔNG có trong DOM và KHÔNG hiển thị trên UI -> Fail
		//explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Đăng ký']/parent::div/preceding-sibling::img")));
		//driver.findElement(By.xpath("//div[text()='Đăng ký']/parent::div/preceding-sibling::img")).click();
		
		//explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//form[@id='reg']")));
	}
	
	@Test
	public void TC_04_Staleness() {
		driver.get("https://www.facebook.com/");
		
		/*
		 * Điều kiện của Staleness là: KHÔNG có trong DOM
		 * Cách demo: Bắt lấy WebElement của 1 element nào đó rồi lưu lại, sau đó làm cho nó mất đi trên DOM (nó được thiết kế là sẽ mất trong DOM), rồi verify là nó đã mất bằng Staleness
		 */
		
		//Mở WebElement của form lên
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Tạo tài khoản mới']")));
		driver.findElement(By.xpath("//a[text()='Tạo tài khoản mới']")).click();
		
		//make sure là nó đã hiện
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='email']")));
		
		//Lưu WebElement đó vào biến:
		//Hiển thị trên UI và có trong form
		WebElement registerForm = driver.findElement(By.xpath("//form[@id='reg']"));
		
		//KHÔNG hiển thị trên UI và có trong form
		WebElement confirmEmail = driver.findElement(By.xpath("//input[@name='reg_email_confirmation__']"));
		
		//Làm cho WebElement đó mất đi trên DOM
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Đăng ký']/parent::div/preceding-sibling::img")));
		driver.findElement(By.xpath("//div[text()='Đăng ký']/parent::div/preceding-sibling::img")).click();
		
		//Mong muốn KHÔNG có trong DOM
		explicitWait.until(ExpectedConditions.stalenessOf(registerForm));
		
		explicitWait.until(ExpectedConditions.stalenessOf(confirmEmail));
	}
	
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
