package testng;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(ReportListener.class)
public class Topic_08_Dependency {
	// flow này nên để depend
	@Test
	public void User_01_Create_New_User() {
		Assert.assertTrue(false);
	}

	@Test(dependsOnMethods = "User_01_Create_New_User")
	public void User_02_View_New_User() {
	}

	@Test(dependsOnMethods = "User_01_Create_New_User")
	public void User_03_Edit_New_User() {
	}

	@Test(dependsOnMethods = "User_01_Create_New_User")
	public void User_04_Move_New_User() {
	}

	@Test(dependsOnMethods = "User_01_Create_New_User")
	public void User_05_Delete_New_User() {
	}
}
