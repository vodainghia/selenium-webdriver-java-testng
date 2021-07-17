package api;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_13_Iframe_Windows {
	WebDriver driver;
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
		
		driver = new ChromeDriver();
		//driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}
	//Part I: Iframe/Frame
	//@Test
	public void TC_01_Iframe() {
		//switch alert
		//driver.switchTo().alert();	

		//switch frame/ iframe
		//driver.switchTo().frame(0); //index: ko nên dùng
		
		//driver.switchTo().frame("video-2679-1_youtube_iframe"); //id or name: ngon nhưng ko phải có sẵn để dùng
		
		//driver.switchTo().frame(driver.findElement(By.xpath(""))); //webElement: dùng được trong mọi tình huống
		
		driver.get("https://automationfc.com/2020/02/18/training-online-automation-testing/");
		
		//Switch to facebook iframe: muốn bắt element trong iframe thì phải switch vào iframe, 
		//sau đó nếu muốn bắt element trong parent thì phải switch lại về parent. 
		//KHÔNG thể di chuyển trực tiếp qua lại giữa các iframe, phải đi qua trung gian là parent page! 
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@data-testid='fb:page Facebook Social Plugin']")));
		
		//Element of facebook iframe
		String likeNumber = driver.findElement(By.xpath("//a[text()='Automation FC']/parent::div/following-sibling::div")).getText();
		System.out.println(likeNumber);
		
		//Switch to parent page
		driver.switchTo().defaultContent();
		
		//Element of parent
		String postTitle = driver.findElement(By.xpath("//h1[@class='post-title']")).getText();
		System.out.println(postTitle);

		//Switch to google doc iframe then sendKeys
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'docs.google.com')]")));
		driver.findElement(By.xpath("//div[contains(@data-params,'HỌ TÊN')]//input")).sendKeys("Automation FC");
	}
	
	//@Test
	public void TC_02_Frame() {
		driver.get("https://v1.hdfcbank.com/assets/popuppages/netbanking.htm");
		
		driver.findElement(By.xpath("(//div[@class='container']/div/a[text()='Continue to NetBanking'])[1]")).click();
		
		driver.switchTo().frame("login_page");
		
		driver.findElement(By.name("fldLoginUserId")).sendKeys("AutomationFC");
		driver.findElement(By.xpath("//a[contains(@onclick,'fLogon')]/img[@alt='continue']")).click();

		Assert.assertTrue(driver.findElement(By.name("fldPassword")).isDisplayed());
		
		//Switch to parent page
		driver.switchTo().defaultContent();
		
		driver.switchTo().frame("footer");
		
		driver.findElement(By.xpath("//a[text()='Terms and Conditions']")).click();
	}

	//Part II: Windows/Tabs
	//@Test
	public void TC_03_Github() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		//Get ra Window/ tab ID tại tab đang được active
		String parentTabID = driver.getWindowHandle();
		
		//Click to Google link => mở ra Tab/Window mới
		driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
		
		//Switch to Google tab: chỉ có 2 tabs ở đây, nên so sánh tab nào khác ID với parent tab thì chính là tab cần switch vào
		switchToWindowByID(parentTabID);
		
		String googleTabID = driver.getWindowHandle();
		
		driver.findElement(By.xpath("//input[@role='combobox']")).sendKeys("Selenium");
	
		//Switch về parent tab
		switchToWindowByID(googleTabID);
		
		//Click to Facebook link
		driver.findElement(By.xpath("//a[text()='FACEBOOK']")).click();
		
		//Switch to Facebook tab
		switchToWindowByTitle("Facebook - Đăng nhập hoặc đăng ký");
		
		//Switch to parent tab
		switchToWindowByTitle("SELENIUM WEBDRIVER FORM DEMO");
		
		//Click to Tiki link
		driver.findElement(By.xpath("//a[text()='TIKI']")).click();
		
		//Switch to Tiki tab
		switchToWindowByTitle("Tiki - Mua hàng online giá tốt, hàng chuẩn, ship nhanh");
		
		Assert.assertTrue(driver.findElement(By.xpath("//input[@data-view-id='main_search_form_input']")).isDisplayed());
		
		//Switch to parent tab
		switchToWindowByTitle("SELENIUM WEBDRIVER FORM DEMO");
		
		//Click to Lazada link
		driver.findElement(By.xpath("//a[text()='LAZADA']")).click();
		
		//Switch to Lazada tab
		switchToWindowByTitle("Shopping online - Buy online on Lazada.vn");
	}
	
	@Test
	public void TC_04_Kyna() {
		driver.get("https://kyna.vn/");
		
		//String parentID = driver.getWindowHandle();
		
		driver.findElement(By.xpath("//div[@id='k-footer']//img[@alt='facebook']")).click();
		
		switchToWindowByTitle("Kyna.vn - Trang chủ | Facebook");
		
		switchToWindowByTitle("Kyna.vn - Học online cùng chuyên gia");
		
		driver.findElement(By.xpath("//div[@id='k-footer']//a[text()='VietnamWorks']")).click();

		switchToWindowByTitle("Tuyển dụng, việc làm, tìm việc làm nhanh mới nhất | VietnamWorks");
		
		driver.findElement(By.xpath("//input[@id='search-bar-input']")).sendKeys("Automation Test");
		sleepInSecond(3);
		
		switchToWindowByTitle("Kyna.vn - Học online cùng chuyên gia");
		
		driver.findElement(By.xpath("//div[@id='k-footer']//a[text()='PRIMUS']")).click();
		
		switchToWindowByTitle("PRIMUS Homepage");
		
		Assert.assertTrue(driver.findElement(By.cssSelector(".btn-register1")).isDisplayed());
		
		//closeAllTabWithoutParent(parentID);
	}
	
	//@Test
	public void TC_05_Liveguru() {
		driver.get("https://live.demoguru99.com/");
		
		driver.findElement(By.xpath("//a[text()='Mobile']")).click();
		
		String parentID = driver.getWindowHandle();
		
		driver.findElement(By.xpath("//a[text()='IPhone']/parent::h2/following-sibling::div[@class='actions']//a[text()='Add to Compare']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='The product IPhone has been added to comparison list.']")).isDisplayed());
		
		driver.findElement(By.xpath("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']//a[text()='Add to Compare']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='The product Samsung Galaxy has been added to comparison list.']")).isDisplayed());
		
		driver.findElement(By.xpath("//button[@title='Compare']")).click();
		
		switchToWindowByID(parentID);

		Assert.assertEquals(driver.getCurrentUrl(), "http://live.demoguru99.com/index.php/catalog/product_compare/index/");
		
	}
	
	public void switchToWindowByTitle(String tabTitle) {
		Set<String> allWindowIDs = driver.getWindowHandles();
		
		for (String id : allWindowIDs) {
			driver.switchTo().window(id);
			String actualTitle = driver.getTitle();
			if (actualTitle.equals(tabTitle)) {
				break;
			}
		}
	}
	
	public void closeAllTabWithoutParent(String parentID) {
		Set<String> allTabIDs = driver.getWindowHandles();
		
		for (String id : allTabIDs) {
			if (!id.equals(parentID)) {
				driver.switchTo().window(id);
				driver.close(); //close thì đóng tab/window đang đứng, quit thì đóng toàn bộ tabs/windows
			}
		}
		driver.switchTo().window(parentID);
	}
	
	public void switchToWindowByID(String windowID) {
		//Get hết các Window ID đang có
		Set<String> allTabIDs = driver.getWindowHandles();
		
		//Duyệt qua các giá trị trong all windows
		for (String id : allTabIDs) {
			if (!id.equals(windowID)) {
				driver.switchTo().window(id);
				break;
			}
		}
	}
	
	public void sleepInSecond(long second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
