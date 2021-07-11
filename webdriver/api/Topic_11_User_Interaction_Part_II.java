package api;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_11_User_Interaction_Part_II {
	WebDriver driver;
	Actions action;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	JavascriptExecutor jsExecutor;
	String jsDragDropPath = projectPath + "/dragAndDrop/drag_and_drop_helper.js";
	String jQueryDragDropPath = projectPath + "/dragAndDrop/jquery_load_helper.js"; //Nếu web đã có import sẵn jQuery thì ko cần import file này
	
	@BeforeClass
	public void beforeClass() {
		if (osName.toLowerCase().contains("mac os")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver.exe");
		}
		
		driver = new FirefoxDriver();
		action = new Actions(driver);
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}
	
	//@Test
	public void TC_06_Double_Click() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		//scroll đến element trước
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//button[text()='Double click me']")));
		sleepInSecond(1);
		//double click
		action.doubleClick(driver.findElement(By.xpath("//button[text()='Double click me']"))).perform();
		sleepInSecond(2);
		
		Assert.assertEquals(driver.findElement(By.id("demo")).getText(), "Hello Automation Guys!");
		
	}
	
	//@Test
	public void TC_07_Right_Click() {
		driver.get("http://swisnl.github.io/jQuery-contextMenu/demo.html");
		
		action.contextClick(driver.findElement(By.xpath("//span[text()='right click me']"))).perform();
		sleepInSecond(2);
		
		//Before hover
		Assert.assertTrue(driver.findElement(By.cssSelector(".context-menu-icon-paste:not(.context-menu-hover):not(.context-menu-visible)")).isDisplayed());
		
		//Hover
		action.moveToElement(driver.findElement(By.className("context-menu-icon-paste"))).perform();
		sleepInSecond(2);
		
		//After hover
		Assert.assertTrue(driver.findElement(By.cssSelector(".context-menu-icon-paste.context-menu-hover.context-menu-visible")).isDisplayed());
		
		//Click to Paste
		action.click(driver.findElement(By.className("context-menu-icon-paste"))).perform();
		sleepInSecond(1);
		driver.switchTo().alert().accept();
	}
	
	//@Test
	public void TC_08_Drag_And_Drop_HTML4() {
		driver.get("https://automationfc.github.io/kendo-drag-drop/");
		
		WebElement smallCircle = driver.findElement(By.id("draggable"));
		WebElement bigCircle = driver.findElement(By.id("droptarget"));
		
		action.dragAndDrop(smallCircle, bigCircle).perform();
		sleepInSecond(1);
		
		Assert.assertEquals(bigCircle.getText(), "You did great!");
		
		//Or
		Assert.assertEquals(Color.fromString(bigCircle.getCssValue("background-color")).asHex(), "#03a9f4");
	}
	
	//@Test
	public void TC_09_Drag_And_Drop_HTML5() throws IOException {//1 hàm mà muốn tham chiếu tới 1 hàm khác thì phải trả về exception tương ứng
		driver.get("https://automationfc.github.io/drag-drop-html5/");
		
		String columnA = "#column-a";
		String columnB = "#column-b";
		
		String jsValue = readFile(jsDragDropPath);
		
		jsValue = jsValue + "$(\"" + columnA + "\").simulateDragDrop({ dropTarget: \"" + columnB + "\"});";
		jsExecutor.executeScript(jsValue);
		sleepInSecond(2);
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-a']/header[text()='B']")).isDisplayed());
		
		jsExecutor.executeScript(jsValue);
		sleepInSecond(2);
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-a']/header[text()='A']")).isDisplayed());
		
		jsExecutor.executeScript(jsValue);
		sleepInSecond(2);
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-a']/header[text()='B']")).isDisplayed());
	}
	
	@Test
	public void TC_09a_Drag_And_Drop_HTML5_With_Offset() throws AWTException {
		driver.get("https://automationfc.github.io/drag-drop-html5/");
		
		drag_the_and_drop_html5_by_xpath("//div[@id='column-a']", "//div[@id='column-b']");
		sleepInSecond(2);
		
		drag_the_and_drop_html5_by_xpath("//div[@id='column-b']", "//div[@id='column-a']");
		sleepInSecond(2);
		
		drag_the_and_drop_html5_by_xpath("//div[@id='column-a']", "//div[@id='column-b']");
		sleepInSecond(2);
	}
	
	public String readFile(String file) throws IOException {
		Charset cs = Charset.forName("UTF-8");
		FileInputStream stream = new FileInputStream(file);
		try {
			Reader reader = new BufferedReader(new InputStreamReader(stream, cs));
			StringBuilder builder = new StringBuilder();
			char[] buffer = new char[8192];
			int read;
			while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
				builder.append(buffer, 0, read);
			}
			return builder.toString();
		} finally {
			stream.close();
		}
	}
	
	//Dùng thư viện Robot, NOTE: ĐỂ DÙNG HÀM NÀY THÌ CẦN CHỈNH LẠI SCALE MÀN HÌNH THÀNH 100% (MẶC ĐỊNH LÀ 125%)
	@SuppressWarnings("deprecation")
	public void drag_the_and_drop_html5_by_xpath(String sourceLocator, String targetLocator) throws AWTException {

		WebElement source = driver.findElement(By.xpath(sourceLocator));
		WebElement target = driver.findElement(By.xpath(targetLocator));

		// Setup robot
		Robot robot = new Robot();
		robot.setAutoDelay(500);

		// Get size of elements
		Dimension sourceSize = source.getSize();
		Dimension targetSize = target.getSize();

		// Get center distance
		int xCentreSource = sourceSize.width / 2;
		int yCentreSource = sourceSize.height / 2;
		int xCentreTarget = targetSize.width / 2;
		int yCentreTarget = targetSize.height / 2;

		Point sourceLocation = source.getLocation();
		Point targetLocation = target.getLocation();
		//System.out.println(sourceLocation.toString());
		//System.out.println(targetLocation.toString());

		// Make Mouse coordinate center of element
		sourceLocation.x += 20 + xCentreSource;
		sourceLocation.y += 110 + yCentreSource;
		targetLocation.x += 20 + xCentreTarget;
		targetLocation.y += 110 + yCentreTarget;

		//System.out.println(sourceLocation.toString());
		//System.out.println(targetLocation.toString());

		// Move mouse to drag from location
		robot.mouseMove(sourceLocation.x, sourceLocation.y);

		// Click and drag
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseMove(((sourceLocation.x - targetLocation.x) / 2) + targetLocation.x, ((sourceLocation.y - targetLocation.y) / 2) + targetLocation.y);

		// Move to final position
		robot.mouseMove(targetLocation.x, targetLocation.y);

		// Drop
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
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
