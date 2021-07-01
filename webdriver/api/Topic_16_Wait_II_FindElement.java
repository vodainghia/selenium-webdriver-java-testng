package api;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_16_Wait_II_FindElement {
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
		
		//driver = new ChromeDriver();
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	@Test
	public void TC_01_FindElement() {
		driver.get("https://www.facebook.com/");
		
		/* CASE 1: Có duy nhất 1 element
		 * KHÔNG cần chờ hết timeout của implicit nếu như đã tìm thấy
		 * tiếp tục tương tác với element ngay
		 */
		driver.findElement(By.id("email")).sendKeys("automationfc.vn@gmail.com");
		
		/* CASE 2: KHÔNG có element nào hết
		 * chờ hết timeout của implicit
		 * Trong thời gian chờ cứ mỗi 0.5s sẽ tìm lại 1 lần
		 * Khi chờ hết timeout của implicit thì sẽ đánh fail TC và throw exception: NoSuchElementException
		 */
		driver.findElement(By.id("address")).sendKeys("Vietnam"); //failed
		driver.findElement(By.id("pass")).sendKeys("123456"); //ko chạy do failed phía trên
		
		/* CASE 3: Có nhiều hơn 1 element
		 * KHÔNG cần chờ hết timeout của implicit nếu như đã tìm thấy
		 * Nó sẽ lấy cái element đầu tiên để tương tác, KHÔNG quan tâm có bao nhiêu matching node
		 */
		System.out.println(driver.findElement(By.xpath("//input")).getAttribute("name"));
		System.out.println(driver.findElement(By.xpath("//input")).getAttribute("type"));
		System.out.println(driver.findElement(By.xpath("//input")).getAttribute("value"));
	}
	
	@Test
	public void TC_02_FindElements() {
		driver.navigate().refresh();
		
		/* CASE 1: Có duy nhất 1 element
		 * KHÔNG cần chờ hết timeout của implicit nếu như đã tìm thấy
		 * tiếp tục tương tác với element ngay
		 */
		driver.findElements(By.id("email")).get(0).sendKeys("automationfc.vn@gmail.com");
		System.out.println(driver.findElements(By.id("email")).size()); //bằng 1
		
		/* CASE 2: KHÔNG có element nào hết: Áp dụng khi test 1 element KHÔNG xuất hiện trên UI và KHÔNG có trong DOM
		 * chờ hết timeout của implicit
		 * Trong thời gian chờ cứ mỗi 0.5s sẽ tìm lại 1 lần
		 * Khi chờ hết timeout của implicit thì sẽ KHÔNG đánh fail TC
		 * Trả về 1 list empty [rỗng/ko có phần tử (web element)]
		 * Chuyển qua step tiếp theo
		 */
		System.out.println(driver.findElements(By.id("address")).size()); //bằng 0, KHÔNG làm failed TC
		driver.findElement(By.id("pass")).sendKeys("123456"); //vấn chạy được
		
		/* CASE 3: Có nhiều hơn 1 element
		 * KHÔNG cần chờ hết timeout của implicit
		 * Lưu hết tất cả các element vào trong List
		 */
		List<WebElement> footerLinks = driver.findElements(By.cssSelector("ul.pageFooterLinkList a"));
		System.out.println(footerLinks.size());
		for (WebElement link : footerLinks) {
			System.out.println(link.getText());
		}
		
	}
	
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
