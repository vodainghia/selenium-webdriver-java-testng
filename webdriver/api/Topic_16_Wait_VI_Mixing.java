package api;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_16_Wait_VI_Mixing {
	WebDriver driver;
	WebDriverWait explicitWait;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	By startButton = By.cssSelector("#start>button");
	By loadingIcon = By.cssSelector("div#loading");
	By helloWorldText = By.xpath("//h4[text()='Hello World!']");
	String cloudFileName = "cloud.jpg";
	String cloudFilePath = projectPath + "\\uploadFiles\\" + cloudFileName;
	String sasnarFileName = "nar-sas.jpg";
	String sasnarFilePath = projectPath + "\\uploadFiles\\" + sasnarFileName;
	
	
	@BeforeClass
	public void beforeClass() {
		if (osName.toLowerCase().contains("mac os")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
			System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver.exe");
			System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver.exe");
		}
		
		driver = new ChromeDriver();
		//driver = new FirefoxDriver();
		driver.manage().window().maximize();
		explicitWait = new WebDriverWait(driver, 60);
	}
	
	//@Test
	public void TC_01_Element_Found_Implicit_Explicit() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		explicitWait = new WebDriverWait(driver, 15);
		
		driver.get("https://www.facebook.com/");
		
		showDateTimeNow("Start explicit: ");
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='email']")));
		showDateTimeNow("End explicit: ");
		
		showDateTimeNow("Start implicit: ");
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("automationfc.vn@gmail.com");
		showDateTimeNow("End implicit: ");
	}
	
	//@Test
	public void TC_02_Element_Not_Found_Implicit() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		driver.get("https://www.facebook.com/");
		
		/*
		 * chờ hết timeout của implicit
		 * Trong thời gian chờ cứ mỗi 0.5s sẽ tìm lại 1 lần
		 * Khi chờ hết timeout của implicit thì sẽ đánh fail TC và throw exception: NoSuchElementException
		 */
		
		showDateTimeNow("Start implicit: ");
		try {
			driver.findElement(By.xpath("//input[@id='wrong_id']")).sendKeys("automationfc.vn@gmail.com");
		} catch (Exception e) {
			e.printStackTrace();
		}
		showDateTimeNow("End implicit: ");
	}
	
	//@Test //hardest!
	public void TC_03_Element_Not_Found_Implicit_Explicit() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		explicitWait = new WebDriverWait(driver, 10);
		
		driver.get("https://www.facebook.com/");
		
		//luôn findElement trước
		//sau đó apply điều kiện sau
		//implicit sẽ ảnh hưởng vào các step có dùng explicit
		showDateTimeNow("Start explicit: ");
		try {
			explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='wrong_id']")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		showDateTimeNow("End explicit: ");
		
		//Dù có dùng explicit thì KHÔNG ảnh hưởng vào implicit (findElement)
//		showDateTimeNow("Start implicit: ");
//		try {
//			driver.findElement(By.xpath("//input[@id='wrong_id']")).sendKeys("automationfc.vn@gmail.com");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		showDateTimeNow("End implicit: ");
	}
	
	@Test
	public void TC_04_Element_Not_Found_Explicit_Param_By() {
		explicitWait = new WebDriverWait(driver, 5);
		
		driver.get("https://www.facebook.com/");
		
		//Kết quả chạy sẽ ra 5s, do đang tìm bằng xpath (chỉ là 1 đoạn string), implicit bằng 0s nhưng explicit bằng 5s, cần phải tìm tuần tự mỗi 0.5s đến khi cả implicit và explicit đều xong thì mới ngừng (5s).
		showDateTimeNow("Start explicit: ");
		try {
			explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='wrong_id']")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		showDateTimeNow("End explicit: ");
		
	}
	
	@Test
	public void TC_05_Element_Not_Found_Explicit_Param_WebElement() {
		explicitWait = new WebDriverWait(driver, 5);
		
		driver.get("https://www.facebook.com/");
		
		//Kết quả chạy sẽ ra 0s, do lúc vừa chạy ko tìm được WebElement (lưu ý là đang tìm theo WebElement) mà lại ko có set implicit (findElement) nên default = 0, dừng chạy luôn trong giây 0s.
		showDateTimeNow("Start explicit: ");
		try {
			explicitWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//input[@id='wrong_id']"))));
		} catch (Exception e) {
			e.printStackTrace();
		}
		showDateTimeNow("End explicit: ");
		
	}
	
	public void showDateTimeNow(String status) {
		Date date = new Date();
		System.out.println("--------------- " + status + date.toString() + " ---------------");
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
