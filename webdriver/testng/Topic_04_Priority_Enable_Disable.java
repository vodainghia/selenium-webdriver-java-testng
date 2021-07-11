package testng;

import org.testng.annotations.Test;

public class Topic_04_Priority_Enable_Disable {
	@Test(priority = 1)
	public void User_01_Create_New_User() {
	}

	@Test(priority = 2, enabled = false)
	public void User_02_View_New_User() {
	}

	@Test(priority = 3, enabled = true, description = "JIRA-666: Edit the user")
	public void User_03_Edit_New_User() {
	}

	@Test(priority = 4)
	public void User_04_Move_New_User() {
	}

	@Test(priority = 5)
	public void User_05_Delete_New_User() {
	}
}
