package testng;

import org.testng.annotations.Test;
//demo in runTestcases.xml
public class Topic_03_Group {
  @Test(groups = "web")
  public void TC_01_Web() {
  }
  @Test(groups = "mobile")
  public void TC_02_Mobile() {
  }
  @Test(groups = "api")
  public void TC_03_API() {
  }
}
