package testng;

import org.testng.Assert;
import org.testng.annotations.Test;

public class Topic_02_Assertions {
	@Test()
	public void TC_01() {
		String fullName = "Automation Testing";

		// ko nên dùng cách equals trong assertTrue vì nếu failed thì ko trả ra được log
		// rõ ràng
		// Nên dùng Assert.assertEquals(fullName,"Manual Testing");
		// Assert.assertTrue(fullName.equals("Manual Testing"));
		// isDisplayed/isEnabled/isSelected/isMultiple -> return boolean

		// 2nd arg for error msg
		Assert.assertTrue(fullName.equals("Manual Testing"), "Fullname is not matching");
	}
}
