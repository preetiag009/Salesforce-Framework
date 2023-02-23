package salesForce;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;
import utilityPackage.Constants;
import utilityPackage.ExtentReportsUtility;
import utilityPackage.SalesForceUtility;

public class ParentApplicationTest {

	// static WebDriver driver = null;
	protected static WebDriver driver = null;
	protected static WebDriverWait wait = null;
	protected static Logger logger = null;
	public static ExtentReports report;
	public static ExtentSparkReporter sparkReporter;
	public static ExtentTest testLogger;
	protected static ExtentReportsUtility extentreport = ExtentReportsUtility.getInstance();

	@BeforeTest
	public void setUpBeforeTest() {
		logger = LogManager.getLogger(ParentApplicationTest.class.getName());
		logger.info("The test has started.");
	}
		
	@BeforeMethod
	@Parameters({ "browsername", "url" })
	public void setUpBeforeMethod(@Optional("chrome") String browserName,
			@Optional("https://login.salesforce.com/") String url, Method method) {
		String s = method.getName();
		logger.info("Inside method -" + s);
		SalesForceUtility salesforceutility = new SalesForceUtility();
		salesforceutility.loadFile("salesForceData");
		String Url = salesforceutility.getPropertyValue("url");
		getDriverInstance(browserName);
		gotourl(Url);
	}
	public static void getDriverInstance(String browserName) {
		switch (browserName) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			
			  ChromeOptions options = new ChromeOptions();
			  options.addArguments("--headless"); driver = new ChromeDriver(options);
			 
			//driver = new ChromeDriver();
			driver.manage().window().maximize();
			break;
		case "edge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			driver.manage().window().maximize();
			break;
		default:
			logger.info("Invalid browser name");
		}
	}
	
	public static void popupClose() {
		try {
			sleepWait(4000);
			WebElement closeTab = driver.findElement(By.xpath("//div/a[@id ='tryLexDialogX']"));
			waitTime(closeTab);
			clickAction(closeTab);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Popup not found");
		}
	}

	public static void loginToSalesforce() {
		SalesForceUtility salesforceutility = new SalesForceUtility();
		salesforceutility.loadFile("salesForceData");
		String userId = salesforceutility.getPropertyValue("login.valid.userid");
		String password = salesforceutility.getPropertyValue("login.valid.password");

		WebElement Username = driver.findElement(By.id("username"));
		clickAction(Username);
		enterText(Username, userId, "Username");

		WebElement passWord = driver.findElement(By.id("password"));
		clickAction(passWord);
		enterText(passWord, password, "Password");

		WebElement loginButton = driver.findElement(By.id("Login"));
		clickAction(loginButton);

		WebElement homePage = driver.findElement(By.xpath("//li[@id='home_Tab']"));
		String actual = getTextFromWebEle(homePage, "Home");
	//	System.out.println(actual);
		String expected = "Home";
		Assert.assertEquals(actual, expected);
		extentreport.logTestInfo("Logged into Salesforce.");

	}
	
	public static void gotourl(String url) {
		driver.get(url);
	}

	public static void enterText(WebElement element, String text, String name) {
		if (element.isDisplayed()) {
			element.clear();
			element.sendKeys(text);
			logger.info("Entered text to: " + name);
			extentreport.logTestInfo("Text entered to " + name + " .");
		} else {
			logger.info(name + "  -WebElement not displayed.");
			extentreport.logTestInfo(name + "  -WebElement not displayed.");
		}
	}

	public static void enterTextRandom(WebElement element, String text, String name) {
		if (element.isDisplayed()) {
			element.clear();
			element.sendKeys(text);
			logger.info("Entered text to: " + name);
			extentreport.logTestInfo("Text entered to " + name + " .");
		} else {
			logger.info(name + "  -WebElement not displayed.");
			extentreport.logTestInfo(name + "  -WebElement not displayed.");
		}
	}

	public static String getTextFromWebEle(WebElement element, String name) {
		if (element.isDisplayed()) {
			return (element.getText());
		} else {
			logger.info(name + " element not displayed");
			return null;
		}
	}

	public static void sleepWait(int mSec) {
		try {
			Thread.sleep(mSec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Thread sleep error");
		}
	}

	public static void waitTime(WebElement userMenuDropDown) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf((WebElement) userMenuDropDown));
	}

	public static void waitUntilPageLoads() {
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
	}

	@SuppressWarnings("deprecation")
	public static void fluentWait(WebElement ele, String name) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(30))
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
		wait.until(ExpectedConditions.visibilityOf(ele));

	}

	public static void clickAction(WebElement ele) {
		Actions action = new Actions(driver);
		action.moveToElement(ele).click().perform();
		logger.info("WebElement clicked " + ele.toString());
	}

	public static void moveToElementAction(WebElement ele) {
		Actions action = new Actions(driver);
		action.moveToElement(ele).build().perform();
	}

	public static void contextClickAction(WebElement ele) {
		Actions action = new Actions(driver);
		action.contextClick(ele).build().perform();
	}

	public static void listDropdownOptions(WebElement ele) {
		Select ob = new Select(ele);
		List<WebElement> list = ob.getOptions();
		int size = list.size();
		for (int i = 0; i < size; i++) {
			System.out.println(list.get(i).getText());
		}
	}

	public static void selectFromDropdown(WebElement ele, String option) {
		Select ob = new Select(ele);
		ob.selectByVisibleText(option);
		logger.info(option + " selected from list.");
		extentreport.logTestInfo(option + " selected from dropdown.");
	}

	public static void selectByIndex(WebElement ele, int index, String name) {
		Select ob = new Select(ele);
		ob.selectByIndex(index);
		logger.info(name + "- Option selected");
		extentreport.logTestInfo(name + " selected from dropdown.");
	}

	public static String getSelectedOption(WebElement ele) {
		Select ob = new Select(ele);
		String s = ob.getFirstSelectedOption().getText();
		return s;
	}

	public static void switchToAlert() {
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}

	public static void switchToWindowOpened(String mainWindowHandle) {
		Set<String> allWindowHandles = driver.getWindowHandles();
		for (String handle : allWindowHandles) {
			if (!mainWindowHandle.equalsIgnoreCase(handle)) {
				driver.switchTo().window(handle);
				logger.info("Switched to opened window.");
				extentreport.logTestInfo("Switched to opened window.");
			}
		}
	}

	public WebDriver returnDriverInstance() {
		return driver;
	}

	public String getScreenshotOfPage(WebDriver driver) throws IOException {

		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		Date date1 = Calendar.getInstance().getTime();
		// System.out.println(dateFormat.format(date1));

		String date = dateFormat.format(date1).toString();// SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		// String curDir = System.getProperty("user.dir");
		TakesScreenshot screenShot = (TakesScreenshot) driver;
		File imgFile = screenShot.getScreenshotAs(OutputType.FILE);
		File destFile = new File(Constants.SCREENSHOTS_DIRECTORY_PATH + date + ".png");
		FileHandler.copy(imgFile, destFile);
		return destFile.getAbsolutePath();
	}

	public static void closeBrowser() {
		driver.close();
	}

	@AfterMethod
	public void setUpAfterMethod(Method method) {
		try {
			Thread.sleep(2000);

			WebElement userMenuOption = driver.findElement(By.id("userNavLabel"));
			waitTime(userMenuOption);
			clickAction(userMenuOption);

			WebElement logoutOption = driver.findElement(By.xpath("//div/a[@title='Logout']"));
			// waitTime(logoutOption);
			clickAction(logoutOption);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(" Couldn't logout: " + method.getName());

			e.printStackTrace();
		}

		driver.quit();
		logger.info(" Method completed: " + method.getName());
		extentreport.logTestInfo("Logged out of Salesforce.");
		// extentreport.endReport();
	}

	@AfterTest
	public void setUpAfterTest() {
		// driver.quit();
		logger.info("The test is completed");
		extentreport.endReport();
	}
}
