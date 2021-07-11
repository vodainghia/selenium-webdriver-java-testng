package testng;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class Topic_01_Annotations {
	
	/*
	 * Thứ tự chạy của các Annotations sẽ là:
	 * Before Suite
	 * 		Before Test (Không phải testcase mà là test suite)
	 * 			Before Class
	 * 				Before Method (Method chính là các testcase)
	 * 					TC_01
	 * 				After Method
	 * 				Before Method
	 * 					TC_02
	 * 				After Method
	 * 				...
	 * 			After Class
	 * 		After Test
	 * After Suite
	 * 
	 * => Nếu muốn lặp lại cho từng TC thì sẽ để trong Before Method
	 */
	
  @Test()
  public void TC_01() {
	  System.out.println("TC_01");
  }
  @Test()
  public void TC_02() {
	  System.out.println("TC_02");
  }
  @Test()
  public void TC_03() {
	  System.out.println("TC_03");
  }
  
  @BeforeMethod
  public void beforeMethod() {
	  System.out.println("Before Method");
  }

  @AfterMethod
  public void afterMethod() {
	  System.out.println("After Method");
  }


  //@DataProvider
  public Object[][] dp() {
    return new Object[][] {
      new Object[] { 1, "a" },
      new Object[] { 2, "b" },
    };
  }
  @BeforeClass
  public void beforeClass() {
	  System.out.println("Before Class");
  }

  @AfterClass
  public void afterClass() {
	  System.out.println("After Class");
  }

  @BeforeTest
  public void beforeTest() {
	  System.out.println("Before Test");
  }

  @AfterTest
  public void afterTest() {
	  System.out.println("After Test");
  }

  @BeforeSuite
  public void beforeSuite() {
	  System.out.println("Before Suite");
  }

  @AfterSuite
  public void afterSuite() {
	  System.out.println("After Suite");
  }

}
