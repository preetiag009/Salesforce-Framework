package salesForce;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import utilityPackage.SalesForceUtility;

@Listeners(utilityPackage.TestEventListenersUtility.class)
public class LoginTest extends ParentLoginTest {

	@Test(enabled = true, priority = 1)
	public static void loginErrorMessage1() {
		SalesForceUtility salesforceutility = new SalesForceUtility();
		salesforceutility.loadFile("salesForceData");
		String userId = salesforceutility.getPropertyValue("login.invalid.userid");

		WebElement username = driver.findElement(By.id("username"));
		clickAction(username);
		enterText(username, userId, "UserName");

		WebElement loginButton = driver.findElement(By.id("Login"));
		clickAction(loginButton);

		WebElement error = driver.findElement(By.xpath("//div[@id='error']"));
		String actual = getTextFromWebEle(error, "Error");
		System.out.println(actual);
		String expected = "Please enter your password.";

		Assert.assertEquals(actual, expected);
	}

	@Test(enabled = true, priority = 2)
	public static void loginToSalesForce2() {

		SalesForceUtility salesforceutility = new SalesForceUtility();
		salesforceutility.loadFile("salesForceData");
		String userId = salesforceutility.getPropertyValue("login.valid.userid");
		String password = salesforceutility.getPropertyValue("login.valid.password");

		WebElement username = driver.findElement(By.id("username"));
		clickAction(username);
		enterText(username, userId, "UserName");

		WebElement passWord = driver.findElement(By.id("password"));
		clickAction(passWord);
		enterText(passWord, password, "Password");

		WebElement loginButton = driver.findElement(By.id("Login"));
		clickAction(loginButton);

		WebElement homePage = driver.findElement(By.xpath("//li[@id='home_Tab']"));
		String actual = getTextFromWebEle(homePage, "Home");
		System.out.println(actual);
		String expected = "Home";

		Assert.assertEquals(actual, expected);
	}

	@Test(enabled = true, priority = 3)
	public static void checkRememberme3() {
		SalesForceUtility salesforceutility = new SalesForceUtility();
		salesforceutility.loadFile("salesForceData");
		String userId = salesforceutility.getPropertyValue("login.valid.userid");
		String password = salesforceutility.getPropertyValue("login.valid.password");

		WebElement username = driver.findElement(By.id("username"));
		clickAction(username);
		enterText(username, userId, "UserName");

		WebElement passWord = driver.findElement(By.id("password"));
		clickAction(passWord);
		enterText(passWord, password, "Password");

		WebElement checkBox = driver.findElement(By.xpath("//input[@id='rememberUn']"));
		clickAction(checkBox);

		WebElement loginButton = driver.findElement(By.id("Login"));
		clickAction(loginButton);

		WebElement dropDown = driver.findElement(By.xpath("//div[@id='userNav-arrow']"));
		clickAction(dropDown);

		WebElement logOut = driver.findElement(By.xpath("//a[contains(text(),'Logout')]"));
		clickAction(logOut);

		sleepWait(2000);

		WebElement idCard = driver.findElement(By.xpath("//*[@id='idcard-identity']"));
		String actual = getTextFromWebEle(idCard, "Id card");
		System.out.println(actual);
		Assert.assertEquals(actual, userId);
	}

	@Test(enabled = true, priority = 4)
	public static void forgotPassword4A() {
		SalesForceUtility salesforceutility = new SalesForceUtility();
		salesforceutility.loadFile("salesForceData");
		String userId = salesforceutility.getPropertyValue("login.valid.userid");

		WebElement forgotPassword = driver.findElement(By.linkText("Forgot Your Password?"));
		clickAction(forgotPassword);

		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

		WebElement userId2 = driver.findElement(By.xpath("//input[contains(@onkeypress,'checkCaps(event)')]"));
		clickAction(userId2);
		enterText(userId2, userId, "UserName");

		WebElement continueButton = driver.findElement(By.id("continue"));
		clickAction(continueButton);

		String expected = "We’ve sent you an email with a link to finish resetting your password.";
		String actual = getTextFromWebEle(driver.findElement(By.xpath("//div/p[@class='senttext mb12'][1]")),
				"Element");

		Assert.assertEquals(actual, expected);
	}

	@Test(enabled = true, priority = 5)
	public static void forgotPassword4B() {
		SalesForceUtility salesforceutility = new SalesForceUtility();
		salesforceutility.loadFile("salesForceData");
		String userId = salesforceutility.getPropertyValue("login.invalid.userid");
		String password = salesforceutility.getPropertyValue("login.invalid.password");

		WebElement username = driver.findElement(By.id("username"));
		clickAction(username);
		enterText(username, userId, "UserName");

		WebElement passWord = driver.findElement(By.id("password"));
		clickAction(passWord);
		enterText(passWord, password, "Password");

		WebElement loginButton = driver.findElement(By.id("Login"));
		clickAction(loginButton);

		String expected = "Please check your username and password. If you still can't log in, contact your Salesforce administrator.";
		String actual = getTextFromWebEle(driver.findElement(By.xpath("//form//div[@class='loginError']")), "Element");

		Assert.assertEquals(actual, expected);
	}
}
