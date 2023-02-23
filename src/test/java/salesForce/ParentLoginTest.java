package salesForce;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hpsf.Date;
import org.openqa.selenium.Alert;
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

public class ParentLoginTest {

	protected static WebDriver driver = null;
	protected static WebDriverWait wait = null;
	protected static Logger logger = null;
	public static ExtentReports report;
	public static ExtentSparkReporter sparkReporter;
	public static ExtentTest testLogger;
	protected static ExtentReportsUtility extentreport = ExtentReportsUtility.getInstance();

	@BeforeTest
	public void setUpBeforeTest() {

		logger = LogManager.getLogger(ParentLoginTest.class.getName());
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

	public static void sleepWait(int mSec) {
		try {
			Thread.sleep(mSec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void getDriverInstance(String browserName) {
		switch (browserName) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			 options.addArguments("--headless"); driver = new ChromeDriver(options);
		//	driver = new ChromeDriver();
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

	public static String getTextFromWebEle(WebElement element, String name) {
		if (element.isDisplayed()) {
			return (element.getText());
		} else {
			logger.info(name + " element not displayed");
			return null;
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

	public static void clickAction(WebElement ele, String command) {
		Actions action = new Actions(driver);
		action.moveToElement(ele).click().perform();
		logger.info("WebElement clicked: " + command);
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
	}

	public static void selectByIndex(WebElement ele, int index, String name) {
		Select ob = new Select(ele);
		ob.selectByIndex(index);
		logger.info(name + "- Option selected");
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
			}
		}
	}

	public static String getScreenshotOfPage(WebDriver driver) throws IOException {
		String date = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date());
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
		driver.quit();
		logger.info(method.getName() + " has completed");
	}

	@AfterTest
	public void setUpAfterTest() {
		logger.info("The test is completed");
	}
}
