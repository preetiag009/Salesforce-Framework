	package utilityPackage;

import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportsUtility {
	public static ExtentReports report;
	public static ExtentSparkReporter sparkReporter;
	public static ExtentTest testLogger;
	private static ExtentReportsUtility extentObject=null; 
	 
	/*
	 * public ExtentReportsUtility() { }
	 */
	  public static ExtentReportsUtility getInstance() {
		  if(extentObject==null) {
	  System.out.println("creating extent utility object"); 
	  extentObject = new ExtentReportsUtility(); } 
		  return extentObject; 
	}
	 
	
	public void startExtentReport() {
		sparkReporter=new ExtentSparkReporter(Constants.SPARKS_HTML_REPORT_PATH);
		report=new ExtentReports();
		report.attachReporter(sparkReporter);
		
		report.setSystemInfo("Host Name", "Salesforce");
		report.setSystemInfo("Environment", "Automation Testing");
		report.setSystemInfo("User Name", "Preeti");

		sparkReporter.config().setDocumentTitle("Test Execution Report");
		sparkReporter.config().setReportName("Salesforce Regression tests");
		sparkReporter.config().setTheme(Theme.DARK);
		//testLogger = new ExtentTest();
	}
	
	public void startSingleTestReport(String testScript_Name) {
		testLogger=report.createTest(testScript_Name);
	}
	
	public void logTestInfo(String text) {
		testLogger.info(text);
	}
	public void logTestpassed(String testcaseName) {
		testLogger.pass(MarkupHelper.createLabel(testcaseName + "is passTest", ExtentColor.GREEN));
	}
	
	public void logTestFailed(String testcaseName) {
		testLogger.fail(MarkupHelper.createLabel(testcaseName + "is failed", ExtentColor.RED));
	}
	
	public void logTestFailedWithException(Throwable throwable) {
		testLogger.log(Status.FAIL,throwable);
	}
	
	public void logTestScreenshot(String path) {
		testLogger.addScreenCaptureFromPath(path);
		}
	
	public void logTestSkipped (ITestResult result) {
		testLogger.log(Status.INFO, "Test case skipped.");
	}
	
	public void testFailedWithTimeout (ITestResult result) {
		testLogger.log(Status.INFO, "Test case failed with Timeout.");
	}
	public void endReport() {
		testLogger.info("Test completed");
		report.flush();
	}
	
}
