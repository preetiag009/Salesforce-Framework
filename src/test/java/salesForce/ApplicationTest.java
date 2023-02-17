package salesForce;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import utilityPackage.SalesForceUtility;

@Listeners(utilityPackage.TestEventListenersUtility.class)
public class ApplicationTest extends ParentApplicationTest {

	@Test(priority = 0, alwaysRun = true)
	public static void userMenu5() {

		loginToSalesforce();

		List<WebElement> userMenuDropDown = driver.findElements(By.xpath("//div[@id ='userNav-menuItems']/a"));

		if (userMenuDropDown.size() == 0) {
			clickAction(userMenuDropDown.get(0));
			extentreport.logTestFailed("userMenu5");
		}
		sleepWait(2000);

		for (WebElement options : userMenuDropDown) {
			logger.info(options.getAttribute("text"));
		}
	}

	@Test(priority = 1, alwaysRun = true)
	public static void myProfileOption6() throws AWTException {

		loginToSalesforce();
		SalesForceUtility salesforceutility = new SalesForceUtility();
		salesforceutility.loadFile("salesForceData");

		String lastname = salesforceutility.getPropertyValue("lastname");
		String text = salesforceutility.getPropertyValue("text");

		WebElement userMenuOption = driver.findElement(By.id("userNavLabel"));
		waitTime(userMenuOption);
		clickAction(userMenuOption);

		WebElement myProfile = driver.findElement(By.xpath("//a[contains(text(),'My Profile')]"));
		waitTime(myProfile);
		clickAction(myProfile);

		sleepWait(2000);
		WebElement editButton = driver.findElement(By.xpath("//div/a[@class ='contactInfoLaunch editLink']"));
		waitTime(editButton);
		clickAction(editButton);
		if (editButton.isSelected()) {
			logger.info("Edit button clicked");
		}
		driver.switchTo().frame("contactInfoContentId");

		WebElement aboutTab = driver.findElement(By.xpath("//a[contains(text(),'About')]"));
		clickAction(aboutTab);

		WebElement lastName = driver.findElement(By.xpath("//input[@id='lastName' and @name= 'lastName']"));
		enterText(lastName, lastname, "Lastname");

		WebElement saveAll = driver.findElement(By.xpath("//input[contains(@value,'Save All')]"));
		clickAction(saveAll);

		driver.switchTo().defaultContent();
		sleepWait(2000);

		WebElement postButton = driver.findElement(By.xpath("//a[@id='publisherAttachTextPost']"));
		clickAction(postButton);

		WebElement frame1 = driver
				.findElement(By.xpath("//iframe[@title='Rich Text Editor, publisherRichTextEditor']"));
		driver.switchTo().frame(frame1);
		WebElement textBox = driver.findElement(By.xpath("//body"));

		fluentWait(textBox, "TextBox");
		clickAction(textBox);
		enterText(textBox, text, "Text");

		sleepWait(2000);

		driver.switchTo().defaultContent();
		WebElement share = driver.findElement(By.xpath("//input[@id='publishersharebutton']"));
		clickAction(share);

		sleepWait(2000);

		WebElement fileLink = driver.findElement(By.xpath("//a[@id='publisherAttachContentPost']"));
		waitTime(fileLink);
		clickAction(fileLink);

		WebElement uploadFileButton = driver.findElement(By.xpath("//a[@id='chatterUploadFileAction']"));
		clickAction(uploadFileButton);

		WebElement chooseFile = driver.findElement(By.xpath("//input[@id='chatterFile']"));
		clickAction(chooseFile);

		StringSelection stringSelection = new StringSelection("D:\\Book1.xlsx");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);

		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		WebElement moderator = driver.findElement(By.xpath("//div/span/img[@class='chatter-photo']"));
		moveToElementAction(moderator);

		WebElement addPhoto = driver
				.findElement(By.xpath("//div[@id='photoSection']/div[@class='photoUploadSection']"));
		clickAction(addPhoto);

		try {
			WebElement frame2 = driver.findElement(By.xpath("//iframe[@id='uploadPhotoContentId']"));
			driver.switchTo().frame(frame2);

			logger.info("Frame found");

			sleepWait(2000);
			WebElement choosePhoto = driver
					.findElement(By.xpath("//input[@id='j_id0:uploadFileForm:uploadInputFile']"));
			clickAction(choosePhoto);

			// choosePhoto.sendKeys("D:\\photo-2.jpeg");
			StringSelection stringSelection2 = new StringSelection("D:\\photo-2.jpeg");
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection2, null);

			sleepWait(2000);
			Robot robot1 = new Robot();
			robot1.keyPress(KeyEvent.VK_CONTROL);
			robot1.keyPress(KeyEvent.VK_V);
			robot1.keyRelease(KeyEvent.VK_V);
			robot1.keyRelease(KeyEvent.VK_CONTROL);
			robot1.keyPress(KeyEvent.VK_ENTER);
			robot1.keyRelease(KeyEvent.VK_ENTER);

			sleepWait(5000);

			WebElement uploadPhoto = driver.findElement(By.xpath("//input[@id='j_id0:uploadFileForm:uploadBtn']"));
			clickAction(uploadPhoto);

			sleepWait(4000);
			WebElement savePhoto = driver.findElement(By.xpath("//input[@id='j_id0:j_id7:save']"));
			waitTime(savePhoto);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].scrollIntoView();", savePhoto);
			executor.executeScript("arguments[0].click();", savePhoto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("Photo already uploaded");
			// e.printStackTrace();
		}
		driver.switchTo().defaultContent();
		
	}

	@Test(priority = 2, alwaysRun = true)
	public static void mySettingsOption7() {

		loginToSalesforce();
		SalesForceUtility salesforceutility = new SalesForceUtility();
		salesforceutility.loadFile("salesForceData");

		String email = salesforceutility.getPropertyValue("email.name");
		String emailAdd = salesforceutility.getPropertyValue("email.address");

		WebElement userMenuOption = driver.findElement(By.id("userNavLabel"));
		waitTime(userMenuOption);
		clickAction(userMenuOption);

		WebElement mySettings = driver.findElement(By.xpath("//div/a[@title =\"My Settings\"]"));
		waitTime(mySettings);
		clickAction(mySettings);

		sleepWait(2000);
		WebElement personal = driver.findElement(By.xpath("//div/a/span[@id ='PersonalInfo_font']"));
		waitTime(personal);
		clickAction(personal);

		WebElement loginHistory = driver.findElement(By.xpath("//div/a[@id =\"LoginHistory_font\"]"));
		waitTime(loginHistory);
		clickAction(loginHistory);

		WebElement downloadLoginHistory = driver
				.findElement(By.xpath("//a[contains(text(),'Download login history for last six months, includ')]"));
		waitTime(downloadLoginHistory);
		clickAction(downloadLoginHistory);

		sleepWait(2000);
		WebElement displayAndLayout = driver.findElement(By.xpath("//span[@id=\"DisplayAndLayout_font\"]"));
		waitTime(displayAndLayout);
		clickAction(displayAndLayout);

		WebElement customizeMyTabs = driver.findElement(By.xpath("//div/a[@id=\"CustomizeTabs_font\"]"));
		waitTime(customizeMyTabs);
		clickAction(customizeMyTabs);

		WebElement customAppDropdown = driver.findElement(By.xpath("//tr/td/select[@id=\"p4\"]"));
		waitTime(customAppDropdown);
		clickAction(customAppDropdown);
		selectFromDropdown(customAppDropdown, "Salesforce Chatter");

		WebElement availableTabs = driver.findElement(By.xpath("//td/select[@id='duel_select_0']"));
		// selectFromDropdown(availableTabs, "Reports");

		// WebElement addButton =
		// driver.findElement(By.xpath("//div/a/img[@class='rightArrowIcon']"));
		// clickAction(addButton);

		WebElement emailOption = driver.findElement(By.xpath("//a/span[@id ='EmailSetup_font']"));
		waitTime(emailOption);
		clickAction(emailOption);

		WebElement emailSettings = driver.findElement(By.xpath("//a/span[@id ='EmailSettings_font']"));
		waitTime(emailSettings);
		clickAction(emailSettings);

		WebElement emailName = driver.findElement(By.xpath("//div/input[@id ='sender_name']"));
		waitTime(emailName);
		clickAction(emailName);
		enterText(emailName, email, "Email");

		WebElement emailAddress = driver.findElement(By.xpath("//div/input[@id ='sender_email']"));
		waitTime(emailAddress);
		clickAction(emailAddress);
		enterText(emailAddress, emailAdd, "Email Address");

		WebElement automaticBcc = driver.findElement(By.xpath("//div/input[@id ='auto_bcc1']"));
		clickAction(automaticBcc);

		WebElement save = driver.findElement(By.xpath("//td/input[@value=' Save ']"));
		clickAction(save);

		WebElement calendarAndReminders = driver.findElement(By.xpath("//div/a/span[@id='CalendarAndReminders_font']"));
		waitTime(calendarAndReminders);
		clickAction(calendarAndReminders);

		WebElement activityReminders = driver.findElement(By.xpath("//div/a[@id='Reminders_font']"));
		waitTime(activityReminders);
		clickAction(activityReminders);

		WebElement openTestReminder = driver.findElement(By.xpath("//td/input[@id='testbtn']"));
		waitTime(openTestReminder);
		clickAction(openTestReminder);

		
	}

	@Test(priority = 3, alwaysRun = true)
	public static void developersConsole8() {

		loginToSalesforce();

		SalesForceUtility salesforceutility = new SalesForceUtility();
		salesforceutility.loadFile("salesForceData");

		WebElement userMenuOption = driver.findElement(By.id("userNavLabel"));
		waitTime(userMenuOption);
		clickAction(userMenuOption);

		String baseWindowHandle = driver.getWindowHandle();
		WebElement developerConsole = driver.findElement(By.xpath("//div/a[@title='Developer Console (New Window)']"));
		waitTime(developerConsole);
		clickAction(developerConsole);
		switchToWindowOpened(baseWindowHandle);
		closeBrowser();
		driver.switchTo().window(baseWindowHandle);

		// driver.switchTo().defaultContent();

		// String windowHandle2= driver.getWindowHandle();
		// closeBrowser();
	}

	@Test(priority = 4, alwaysRun = true)
	public static void logoutOption9() {

		loginToSalesforce();

		/*
		 * WebElement userMenuOption = driver.findElement(By.id("userNavLabel"));
		 * waitTime(userMenuOption); clickAction(userMenuOption);
		 * 
		 * WebElement logoutOption =
		 * driver.findElement(By.xpath("//div/a[@title='Logout']"));
		 * waitTime(logoutOption); clickAction(logoutOption);
		 */

	}

	@Test(priority = 5, alwaysRun = true)
	public static void createAccount10() {

		loginToSalesforce();

		SalesForceUtility salesforceutility = new SalesForceUtility();
		salesforceutility.loadFile("salesForceData");

		String accountName = salesforceutility.getPropertyValue("account.name");

		WebElement accountsTab = driver.findElement(By.xpath("//ul/li/a[@title='Accounts Tab']"));
		waitTime(accountsTab);
		clickAction(accountsTab);
		popupClose();

		waitUntilPageLoads();
		WebElement newButton = driver.findElement(By.xpath("//tbody/tr[1]/td[2]/input[1]"));
		waitTime(newButton);
		clickAction(newButton);

		sleepWait(2000);
		WebElement account_name = driver.findElement(By.xpath("//td/div/input[@id='acc2']"));
		waitTime(account_name);
		clickAction(account_name);
		enterText(account_name, accountName, "Account Name");

		sleepWait(2000);
		WebElement type = driver.findElement(By.xpath("//td/span/select[@id='acc6']"));
		waitTime(type);
		clickAction(type);
		Select ob = new Select(type);
		ob.selectByVisibleText("Technology Partner");

		sleepWait(2000);
		WebElement customerPriority = driver.findElement(By.xpath("//td/span/select[@id='00NDn00000SjaLk']"));
		waitTime(customerPriority);
		clickAction(customerPriority);
		selectFromDropdown(customerPriority, "High");

		WebElement saveButton = driver.findElement(By.xpath("//td/input[@value=' Save ']"));
		waitTime(saveButton);
		clickAction(saveButton);

	}

	@Test(priority = 6, alwaysRun = true)
	public static void createNewView11() {

		loginToSalesforce();
		SalesForceUtility salesforceutility = new SalesForceUtility();
		salesforceutility.loadFile("salesForceData");

		String viewname = salesforceutility.getPropertyValue("view.name");
		String viewuniquename = salesforceutility.getPropertyValue("view.unique.name");

		WebElement accountsTab = driver.findElement(By.xpath("//ul/li/a[@title='Accounts Tab']"));
		waitTime(accountsTab);
		clickAction(accountsTab);
		popupClose();

		WebElement createNewView = driver.findElement(By.xpath("//span/a[text()='Create New View']"));
		waitTime(createNewView);
		clickAction(createNewView);

		sleepWait(2000);
		WebElement viewName = driver.findElement(By.xpath("//div/input[@id='fname']"));
		waitTime(viewName);
		clickAction(viewName);
		enterText(viewName, viewname, "View Name");

		WebElement viewUniqueName = driver.findElement(By.xpath("//div/input[@id='devname']"));
		waitTime(viewUniqueName);
		clickAction(viewUniqueName);
		enterText(viewUniqueName, viewuniquename, "View Unique Name");

		WebElement saveButton = driver.findElement(By.xpath("//td/input[@value=' Save '][1]"));
		waitTime(saveButton);
		clickAction(saveButton);

	}

	@Test(priority = 7, alwaysRun = true)
	public static void editView12() {

		loginToSalesforce();
		SalesForceUtility salesforceutility = new SalesForceUtility();
		salesforceutility.loadFile("salesForceData");

		String viewnewname2 = salesforceutility.getPropertyValue("view.new.name2");

		WebElement accountsTab = driver.findElement(By.xpath("//ul/li/a[@title='Accounts Tab']"));
		waitTime(accountsTab);
		clickAction(accountsTab);
		popupClose();

		WebElement view = driver.findElement(By.xpath("//span/select[@id='fcf']"));
		waitTime(view);
		clickAction(view);
		selectFromDropdown(view, "View_New_Name_2");

		WebElement edit = driver.findElement(By.linkText("Edit"));
		clickAction(edit);
		sleepWait(2000);

		WebElement viewNewName2 = driver.findElement(By.xpath("//input[@id='fname']"));
		clickAction(viewNewName2);
		enterText(viewNewName2, viewnewname2, "View New Name 2");

		WebElement field = driver.findElement(By.xpath("//select[@id='fcol1']"));
		clickAction(field);
		selectFromDropdown(field, "Account Name");

		WebElement operator = driver.findElement(By.xpath("//select[@id='fop1']"));
		clickAction(operator);
		selectFromDropdown(operator, "contains");

		WebElement value = driver.findElement(By.xpath("//input[@id='fval1']"));
		clickAction(value);
		enterText(value, "a", "Value");

		WebElement save = driver.findElement(By.xpath(
				"//body[1]/div[1]/div[2]/table[1]/tbody[1]/tr[1]/td[2]/div[2]/form[1]/div[3]/table[1]/tbody[1]/tr[1]/td[2]/input[1]"));
		clickAction(save);

	}

	@Test(priority = 8, alwaysRun = true)
	public static void mergeAccounts13() {

		loginToSalesforce();
		SalesForceUtility salesforceutility = new SalesForceUtility();
		salesforceutility.loadFile("salesForceData");

		String accountName = salesforceutility.getPropertyValue("account.name");

		WebElement accountsTab = driver.findElement(By.xpath("//ul/li/a[@title='Accounts Tab']"));
		waitTime(accountsTab);
		clickAction(accountsTab);
		popupClose();

		WebElement mergeAccount = driver.findElement(By.linkText("Merge Accounts"));
		clickAction(mergeAccount);

		WebElement searchBox = driver.findElement(By.xpath(" //input[@id='srch']"));
		clickAction(searchBox);
		enterText(searchBox, accountName, "Account Name");

		WebElement findAccounts = driver.findElement(By.xpath("//input[@name='srchbutton']"));
		clickAction(findAccounts);

		WebElement checkBox1 = driver.findElement(By.xpath("//input[@id='cid0']"));
		clickAction(checkBox1);
		logger.info(checkBox1.isSelected());
		if (checkBox1.isSelected() == false) {
			clickAction(checkBox1);
		}

		WebElement checkBox2 = driver.findElement(By.xpath("//input[@id='cid1']"));
		clickAction(checkBox2);
		logger.info(checkBox2.isSelected());
		if (checkBox2.isSelected() == false) {
			clickAction(checkBox2);
		}

		WebElement next = driver
				.findElement(By.xpath("//div[contains(@class,'pbBottomButtons')]//input[contains(@title,'Next')]"));
		clickAction(next);

		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		WebElement merge = driver
				.findElement(By.xpath("//div[contains(@class,'pbBottomButtons')]//input[contains(@title,'Merge')]"));
		clickAction(merge);

		switchToAlert();
		// alert.accept();

		
	}

	@Test(priority = 9, alwaysRun = true)
	public static void createAccountReport14() {

		loginToSalesforce();
		SalesForceUtility salesforceutility = new SalesForceUtility();
		salesforceutility.loadFile("salesForceData");

		String reportname = salesforceutility.getPropertyValue("report.name");
		String reportuniquename = salesforceutility.getPropertyValue("report.unique.name");

		WebElement accountsTab = driver.findElement(By.xpath("//ul/li/a[@title='Accounts Tab']"));
		waitTime(accountsTab);
		clickAction(accountsTab);
		popupClose();

		WebElement accountsLastActivity = driver.findElement(By.linkText("Accounts with last activity > 30 days"));
		clickAction(accountsLastActivity);

		WebElement dateFieldDropDown = driver.findElement(By.id("ext-gen148"));
		fluentWait(dateFieldDropDown, "Date field dropdown");
		clickAction(dateFieldDropDown);

		WebElement createdDate = driver.findElement(By.xpath("//div[contains(text(),'Created Date')]"));
		clickAction(createdDate);

		WebElement from = driver.findElement(By.xpath("//img[@id='ext-gen152']"));
		clickAction(from);
		sleepWait(4000);

		WebElement fromToday = driver.findElement(By.xpath("//em/button[starts-with(@id,'ext-gen28')]"));
		clickAction(fromToday);

		WebElement to = driver.findElement(By.xpath("//img[@id='ext-gen154']"));
		clickAction(to);

		sleepWait(2000);
		WebElement toToday = driver
				.findElement(By.xpath("//em/button[starts-with(@id,'ext-gen29') and text()='Today']"));
		fluentWait(toToday, "To Today");
		clickAction(toToday);

		WebElement saveUnsavedReport = driver.findElement(By.xpath("//button[@id='ext-gen49']"));
		clickAction(saveUnsavedReport);

		WebElement reportName = driver.findElement(By.xpath("//input[@id='saveReportDlg_reportNameField']"));
		clickAction(reportName);
		enterText(reportName, reportname, "Report Name");

		WebElement reportUniqueName = driver.findElement(By.xpath("	//input[@id='saveReportDlg_DeveloperName']"));
		clickAction(reportUniqueName);
		enterText(reportUniqueName, reportuniquename, "Report Unique Name");

		sleepWait(2000);
		WebElement saveAndRunReport = driver.findElement(By.xpath("//button[@id='ext-gen316']"));
		waitTime(saveAndRunReport);
		clickAction(saveAndRunReport);
		sleepWait(3000);

		try {
			WebElement error = driver.findElement(By.xpath("//div[@id='ext-gen351']"));
			if (!error.getText().equals("")) {
				WebElement cancel = driver.findElement(By.xpath("//button[@id='ext-gen318']"));
				clickAction(cancel);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("Report Saved Sucessfully");
		}
	
	}

	@Test(priority = 10, alwaysRun = true)
	public static void opportunitiesDropdown15() {

		loginToSalesforce();

		WebElement opportunitiesTab = driver.findElement(By.xpath("//li[@id='Opportunity_Tab']"));
		waitTime(opportunitiesTab);

		clickAction(opportunitiesTab);

		popupClose();

		WebElement allOpportunities = driver.findElement(By.xpath("//select[@id='fcf']"));
		clickAction(allOpportunities);
		logger.info("Opportunities Dropdown options: ");
		listDropdownOptions(allOpportunities);


	}

	@Test(priority = 11, alwaysRun = true)
	public static void createNewOpty16() {

		loginToSalesforce();
		SalesForceUtility salesforceutility = new SalesForceUtility();
		salesforceutility.loadFile("salesForceData");

		String opportunityname = salesforceutility.getPropertyValue("opportunity.name");
		String accountname = salesforceutility.getPropertyValue("account.name");
		String prob = salesforceutility.getPropertyValue("probability");

		WebElement opportunitiesTab = driver.findElement(By.xpath("//li[@id='Opportunity_Tab']"));
		clickAction(opportunitiesTab);

		popupClose();

		WebElement newOpty = driver.findElement(By.xpath("//tr/td[2]/input[@name='new']"));
		clickAction(newOpty);

		WebElement optyName = driver.findElement(By.xpath("//input[@id='opp3']"));
		clickAction(optyName);
		enterText(optyName, opportunityname, "Opportunity Name");

		WebElement accountName = driver.findElement(By.xpath("//input[@id='opp4']"));
		clickAction(accountName);
		enterText(accountName, accountname, "Account Name");

		WebElement closeDate = driver.findElement(By.xpath("//span/span/a[@tabindex='7']"));
		clickAction(closeDate);

		WebElement stage = driver.findElement(By.xpath("//span/select[@id='opp11']"));
		clickAction(stage);
		selectFromDropdown(stage, "Closed Won");

		WebElement probability = driver.findElement(By.xpath("//input[@id='opp12']"));
		clickAction(probability);
		enterText(probability, prob, "Probability");

		WebElement leadSource = driver.findElement(By.xpath("//select[@id='opp6']"));
		clickAction(leadSource);
		selectFromDropdown(leadSource, "Web");

		// WebElement primaryCampaignSource =
		// driver.findElement(By.xpath("//input[@id='opp17']"));
		// clickAction(primaryCampaignSource);
		// enterText(primaryCampaignSource, primarycampaignsource, "Primary Campaign
		// Source");

		WebElement save = driver.findElement(By.xpath("//td/input[@name='save'][1]"));
		clickAction(save);

	}

	@Test(priority = 12, alwaysRun = true)
	public static void testOptyPipelineReport17() {

		loginToSalesforce();

		WebElement opportunitiesTab = driver.findElement(By.xpath("//li[@id='Opportunity_Tab']"));
		clickAction(opportunitiesTab);

		popupClose();

		WebElement optyPipeline = driver.findElement(By.xpath("//a[contains(text(),'Opportunity Pipeline')]"));
		waitTime(optyPipeline);
		clickAction(optyPipeline);

		sleepWait(2000);
		WebElement pageDisplayed = driver.findElement(By.xpath("//h1[contains(text(),'Opportunity Pipeline')]"));
		String pagename = getTextFromWebEle(pageDisplayed, "Page Displayed");
		Assert.assertEquals(pagename, "Opportunity Pipeline");
	}

	@Test(priority = 13, alwaysRun = true)
	public static void testStuckOptyReport18() {

		loginToSalesforce();

		WebElement opportunitiesTab = driver.findElement(By.xpath("//li[@id='Opportunity_Tab']"));
		clickAction(opportunitiesTab);

		popupClose();

		WebElement stuckOpportunities = driver.findElement(By.xpath("//a[contains(text(),'Stuck Opportunities')]"));
		clickAction(stuckOpportunities);

		WebElement pageDisplayed = driver.findElement(By.xpath("//h1[contains(text(),'Stuck Opportunities')]"));
		String pagename = getTextFromWebEle(pageDisplayed, "Page Displayed");
		Assert.assertEquals(pagename, "Stuck Opportunities");
	}

	@Test(priority = 14, alwaysRun = true)
	public static void testQuaterlySummaryReport19() {

		loginToSalesforce();

		WebElement opportunitiesTab = driver.findElement(By.xpath("//li[@id='Opportunity_Tab']"));
		clickAction(opportunitiesTab);

		popupClose();

		WebElement interval = driver.findElement(By.xpath("//select[@id='quarter_q']"));
		selectFromDropdown(interval, "Next FQ");

		WebElement include = driver.findElement(By.xpath("//select[@id='open']"));
		selectFromDropdown(include, "Open Opportunities");

		WebElement runReport = driver.findElement(By.xpath("//td/input[@value='Run Report']"));
		clickAction(runReport);

		sleepWait(2000);
		WebElement opportunityReportPage = driver.findElement(By.xpath("//h1[contains(text(),'Opportunity Report')]"));
		String pageText = getTextFromWebEle(opportunityReportPage, "Page Text");
		Assert.assertEquals(pageText, "Opportunity Report");
	}

	@Test(priority = 15, alwaysRun = true)
	public static void leadsTab20() {

		loginToSalesforce();

		WebElement leadsTab = driver.findElement(By.xpath("//li[@id ='Lead_Tab']"));
		clickAction(leadsTab);

		popupClose();

		WebElement leadsPage = driver.findElement(By.xpath("//tbody/tr[1]/td[2]/div[1]/div[1]/div[1]/h1[1]"));
		String actualText = getTextFromWebEle(leadsPage, "Actual Text");
		Assert.assertEquals(actualText, "Leads");
	}

	@Test(priority = 16, alwaysRun = true)
	public static void leadsSelectView21() {

		loginToSalesforce();

		WebElement leadsTab = driver.findElement(By.xpath("//li[@id ='Lead_Tab']"));
		clickAction(leadsTab);

		popupClose();

		WebElement viewDropDown = driver.findElement(By.xpath("//select[@id ='fcf']"));
		clickAction(viewDropDown);
		listDropdownOptions(viewDropDown);
	}

	@Test(priority = 17, alwaysRun = true)
	public static void defaultView22() {

		loginToSalesforce();

		WebElement leadsTab = driver.findElement(By.xpath("//li[@id ='Lead_Tab']"));
		clickAction(leadsTab);

		popupClose();

		WebElement viewDropDown = driver.findElement(By.xpath("//select[@id ='fcf']"));
		clickAction(viewDropDown);
		
	//	String expectedOptionSelected;
		try {
			selectFromDropdown(viewDropDown, "My Unread Leads");
			//expectedOptionSelected = getSelectedOption(viewDropDown);
		} catch (StaleElementReferenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("Option already selected");
		}

		WebElement userMenuOption = driver.findElement(By.id("userNavLabel"));
		waitTime(userMenuOption);
		clickAction(userMenuOption);

		WebElement logoutOption = driver.findElement(By.xpath("//div/a[@title='Logout']"));
		waitTime(logoutOption);
		clickAction(logoutOption);

		sleepWait(5000);
		loginToSalesforce();

		WebElement leadsTab1 = driver.findElement(By.xpath("//li[@id ='Lead_Tab']"));
		clickAction(leadsTab1);

		sleepWait(2000);
		WebElement go = driver.findElement(By.xpath("//input[@value =' Go! ']"));
		clickAction(go);

		WebElement dropDown1 = driver.findElement(By.xpath("//select[@id ='00BDn00000ImMSZ_listSelect']"));
		String actualOptionSelected = getSelectedOption(dropDown1);
		Assert.assertEquals(actualOptionSelected, "My Unread Leads");
	}

	@Test(priority = 18, alwaysRun = true)
	public static void todaysLeads23() {

		loginToSalesforce();

		WebElement leadsTab = driver.findElement(By.xpath("//li[@id ='Lead_Tab']"));
		clickAction(leadsTab);
		popupClose();

		WebElement viewDropDown = driver.findElement(By.xpath("//select[@id ='fcf']"));
		clickAction(viewDropDown);
		
		try {
			selectFromDropdown(viewDropDown, "Today's Leads");
//	String expectedOptionSelected = getSelectedOption(viewDropDown);
		} catch (StaleElementReferenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("Option already selected");
		}

		sleepWait(4000);
		WebElement go = driver.findElement(By.xpath("//input[@value =' Go! ']"));
		clickAction(go);

		WebElement dropDown1 = driver.findElement(By.xpath("//select[@id='00BDn00000ImMSn_listSelect']"));
		String actualOptionSelected = getSelectedOption(dropDown1);
		Assert.assertEquals(actualOptionSelected, "Today's Leads");

	}

	@Test(priority = 19, alwaysRun = true)
	public static void newLeads24() {

		loginToSalesforce();

		SalesForceUtility salesforceutility = new SalesForceUtility();
		salesforceutility.loadFile("salesForceData");

		String lastname = salesforceutility.getPropertyValue("lead.lastname");
		String companyname = salesforceutility.getPropertyValue("lead.companyname");

		WebElement leadsTab = driver.findElement(By.xpath("//li[@id ='Lead_Tab']"));
		clickAction(leadsTab);

		popupClose();

		WebElement newTab = driver.findElement(By.xpath("//tbody/tr[1]/td[2]/input[1]"));
		clickAction(newTab);

		sleepWait(2000);
		WebElement lastName = driver.findElement(By.id("name_lastlea2"));
		clickAction(lastName);
		enterText(lastName, lastname, lastname);

		WebElement companyName = driver.findElement(By.xpath("//input[@id='lea3']"));
		clickAction(companyName);
		enterText(companyName, companyname, companyname);

		WebElement save = driver.findElement(By.xpath("//input[@value=' Save '][1]"));
		clickAction(save);
	}

	@Test(priority = 20, alwaysRun = true)
	public static void createNewContact25() {

		loginToSalesforce();

		SalesForceUtility salesforceutility = new SalesForceUtility();
		salesforceutility.loadFile("salesForceData");

		String lastname = salesforceutility.getPropertyValue("contact.lastname");
		String accountname = salesforceutility.getPropertyValue("account.name");

		sleepWait(2000);
		WebElement contactTab = driver.findElement(By.xpath("//li[@id='Contact_Tab']"));
		clickAction(contactTab);

		popupClose();

		WebElement lastName = driver.findElement(By.xpath("//input[@id='name_last']"));
		clickAction(lastName);
		enterText(lastName, lastname, "Last Name");

		WebElement account = driver.findElement(By.xpath("//input[@id='account']"));
		clickAction(account);
		enterText(account, accountname, "Account Name");

		WebElement save = driver.findElement(By.xpath("//input[@value=' Save ']"));
		clickAction(save);
	}

	@Test(priority = 21, alwaysRun = true)
	public static void contactNewView26() {

		loginToSalesforce();

		SalesForceUtility salesforceutility = new SalesForceUtility();
		salesforceutility.loadFile("salesForceData");

		String viewname = salesforceutility.getPropertyValue("contact.viewname");
		String viewuniquename = salesforceutility.getPropertyValue("contact.uniquename");

		sleepWait(2000);
		WebElement contactTab = driver.findElement(By.xpath("//li[@id='Contact_Tab']"));
		clickAction(contactTab);
		popupClose();

		WebElement createNewView = driver.findElement(By.xpath("//a[text()='Create New View']"));
		clickAction(createNewView);

		WebElement viewName = driver.findElement(By.xpath("//input[@id='fname']"));
		clickAction(viewName);
		enterText(viewName, viewname, "View Name");

		WebElement viewUniqueName = driver.findElement(By.xpath("//input[@id='devname']"));
		clickAction(viewUniqueName);
		enterText(viewUniqueName, viewuniquename, "View Unique Name");

		WebElement save = driver.findElement(By.xpath("//input[@value=' Save ']"));
		clickAction(save);
	}

	@Test(priority = 22, alwaysRun = true)
	public static void recentlyCreatedContact27() {

		loginToSalesforce();

		sleepWait(2000);
		WebElement contactTab = driver.findElement(By.xpath("//li[@id='Contact_Tab']"));
		clickAction(contactTab);

		popupClose();

		WebElement dropDown = driver.findElement(By.xpath("//select[@id='hotlist_mode']"));
		selectFromDropdown(dropDown, "Recently Created");
	}

	@Test(priority = 23, alwaysRun = true)
	public static void myContacts28() {

		loginToSalesforce();

		sleepWait(2000);
		WebElement contactTab = driver.findElement(By.xpath("//li[@id='Contact_Tab']"));
		clickAction(contactTab);

		popupClose();

		WebElement view = driver.findElement(By.xpath("//select[@id='fcf']"));
		selectFromDropdown(view, "My Contacts");
	}

	@Test(priority = 24, alwaysRun = true)
	public static void viewContact29() {

		loginToSalesforce();

		sleepWait(2000);
		WebElement contactTab = driver.findElement(By.xpath("//li[@id='Contact_Tab']"));
		clickAction(contactTab);
		popupClose();

		WebElement contact1 = driver.findElement(By.xpath("//th/a[text()='Last Name']"));
		clickAction(contact1);
	}

	@Test(priority = 25, alwaysRun = true)
	public static void errorNewView30() {

		loginToSalesforce();
		SalesForceUtility salesforceutility = new SalesForceUtility();
		salesforceutility.loadFile("salesForceData");

		String viewuniquename = salesforceutility.getPropertyValue("error.viewuniquename");

		sleepWait(2000);
		WebElement contactTab = driver.findElement(By.xpath("//li[@id='Contact_Tab']"));
		clickAction(contactTab);
		popupClose();

		WebElement createNewView = driver.findElement(By.xpath("//a[text()='Create New View']"));
		clickAction(createNewView);

		WebElement viewUniqueName = driver.findElement(By.xpath("//input[@id='devname']"));
		clickAction(viewUniqueName);
		enterText(viewUniqueName, viewuniquename, "View Unique Name");

		WebElement save = driver.findElement(By.xpath("//input[@value=' Save ']"));
		clickAction(save);

		WebElement errorMessage = driver
				.findElement(By.xpath("//div[@class='errorMsg' and text()=' You must enter a value']"));
		String actual1 = getTextFromWebEle(errorMessage, "Error Message");
		String expected = "Error: You must enter a value";

		Assert.assertEquals(actual1, expected);
	}

	@Test(priority = 26, alwaysRun = true)
	public static void cancelNewView31() {

		loginToSalesforce();
		SalesForceUtility salesforceutility = new SalesForceUtility();
		salesforceutility.loadFile("salesForceData");

		String viewname = salesforceutility.getPropertyValue("error.viewname");
		String viewuniquename = salesforceutility.getPropertyValue("error.viewuniquename");

		sleepWait(2000);
		WebElement contactTab = driver.findElement(By.xpath("//li[@id='Contact_Tab']"));
		clickAction(contactTab);
		popupClose();

		WebElement createNewView = driver.findElement(By.xpath("//a[text()='Create New View']"));
		clickAction(createNewView);

		WebElement viewName = driver.findElement(By.xpath("//input[@id='fname']"));
		clickAction(viewName);
		enterText(viewName, viewname, "View Name");

		WebElement viewUniqueName = driver.findElement(By.xpath("//input[@id='devname']"));
		clickAction(viewUniqueName);
		enterText(viewUniqueName, viewuniquename, "View Unique Name");

		WebElement cancel = driver.findElement(By.xpath("//input[@value='Cancel'][1]"));
		clickAction(cancel);
	}

	@Test(priority = 27, alwaysRun = true)
	public static void createsaveAndNew32() {

		loginToSalesforce();

		SalesForceUtility salesforceutility = new SalesForceUtility();
		salesforceutility.loadFile("salesForceData");

		String lastname = salesforceutility.getPropertyValue("last.name32");
		String accountname = salesforceutility.getPropertyValue("account.name32");

		sleepWait(2000);
		WebElement contactTab = driver.findElement(By.xpath("//li[@id='Contact_Tab']"));
		clickAction(contactTab);
		popupClose();
		WebElement newButton = driver.findElement(By.xpath("//input[@value=' New ']"));
		clickAction(newButton);

		WebElement lastName = driver.findElement(By.xpath("//input[@id='name_lastcon2']"));
		clickAction(lastName);
		enterText(lastName, lastname, "Last Name");

		WebElement account = driver.findElement(By.xpath("//input[@id='con4']"));
		clickAction(account);
		enterText(account, accountname, "Account Name");

		WebElement saveAndNew = driver.findElement(By.xpath("//input[@value='Save & New'][1]"));
		clickAction(saveAndNew);

		// Test case failed
	}

	@Test(priority = 28, alwaysRun = true)
	public static void firstlastName33() {

		loginToSalesforce();

		WebElement homePage = driver.findElement(By.xpath("//li[@id='home_Tab']"));
		clickAction(homePage);
		popupClose();
		
		WebElement header = driver.findElement(By.xpath("//h1/a"));
		String actual1 = getTextFromWebEle(header, "Header");
		String expected1 = "Preethi LastName";

		Assert.assertEquals(actual1, expected1);

	}

	@Test(priority = 29, alwaysRun = true)
	public static void verifyEditedlastName34() {

		loginToSalesforce();

		WebElement homePage = driver.findElement(By.xpath("//li[@id='home_Tab']"));
		clickAction(homePage);
		popupClose();

		waitUntilPageLoads();
		WebElement header = driver.findElement(By.xpath("//h1/a"));
		clickAction(header);

		sleepWait(2000);
		WebElement editProfile = driver.findElement(By.xpath("//a/img[@alt='Edit Profile'][1]"));
		clickAction(editProfile);

		driver.switchTo().frame("contactInfoContentId");
		WebElement aboutTab = driver.findElement(By.xpath("//a[contains(text(),'About')]"));
		clickAction(aboutTab);

		WebElement lastName = driver.findElement(By.xpath("//input[@id ='lastName']"));
		clickAction(lastName);
		enterText(lastName, "Abcd", "Lastname");

		WebElement saveAll = driver.findElement(By.xpath("//input[@value ='Save All']"));
		clickAction(saveAll);

		driver.switchTo().defaultContent();

		WebElement header1 = driver.findElement(By.xpath("//span[@id='tailBreadcrumbNode']"));
		String actual1 = getTextFromWebEle(header1, "Header");
		String expected1 = "Preethi Abcd ";
		Assert.assertEquals(actual1, expected1);

		WebElement userMenu = driver.findElement(By.xpath("//span[@id='userNavLabel']"));
		String actual2 = getTextFromWebEle(userMenu, "User Menu");
		String expected2 = "Preethi Abcd";
		Assert.assertEquals(actual2, expected2);

	}

	@Test(priority = 30, alwaysRun = true)
	public static void verifyTabCustomization35() {

		loginToSalesforce();

		WebElement allTabs = driver.findElement(By.xpath("//img[@alt='All Tabs']"));
		clickAction(allTabs);

		waitUntilPageLoads();
		sleepWait(2000);
		WebElement customizeMyTabs = driver.findElement(By.xpath("//input[@value='Customize My Tabs']"));
		clickAction(customizeMyTabs);

		try {
			sleepWait(2000);
			WebElement selectedTabs = driver.findElement(By.xpath("//select[@id='duel_select_1']"));
			selectFromDropdown(selectedTabs, "Chatter");

			WebElement remove = driver.findElement(By.xpath("//img[@title='Remove']"));
			clickAction(remove);
		} catch (Exception e) {
			logger.error("Element already removed.");
		}
		WebElement save = driver.findElement(By.xpath("//input[@value=' Save ']"));
		clickAction(save);

		WebElement userMenu = driver.findElement(By.xpath("//span[@id='userNavLabel']"));
		clickAction(userMenu);

		WebElement logoutOption = driver.findElement(By.xpath("//div/a[@title='Logout']"));
		waitTime(logoutOption);
		clickAction(logoutOption);

		sleepWait(2000);

		loginToSalesforce();

		WebElement allTabs1 = driver.findElement(By.xpath("//img[@alt='All Tabs']"));
		clickAction(allTabs1);

		sleepWait(2000);
		WebElement customizeMyTabs1 = driver.findElement(By.xpath("//input[@value='Customize My Tabs']"));
		clickAction(customizeMyTabs1);

		sleepWait(2000);
		WebElement selectedTabs1 = driver.findElement(By.xpath("//select[@id='duel_select_1']"));
		try {
			selectFromDropdown(selectedTabs1, "Chatter");
		} catch (Exception e) {
			logger.info("Option not found. Test case Passed");
		}

	}

	@Test(priority = 31, alwaysRun = true)
	public static void blockingEvent36() {

		loginToSalesforce();

		WebElement homePage = driver.findElement(By.xpath("//li[@id='home_Tab']"));
		clickAction(homePage);
		popupClose();
		
		sleepWait(4000);
		WebElement currentDate = driver.findElement(By.xpath("//span/a[1]"));
		clickAction(currentDate);

		sleepWait(2000);
		WebElement eightpm = driver.findElement(By.xpath("//div[@id='p:f:j_id25:j_id61:28:j_id64']/a"));
		clickAction(eightpm);

		WebElement subjectCombo = driver.findElement(By.xpath("//img[@class='comboboxIcon']"));
		clickAction(subjectCombo);

		String mainWindowHandle = driver.getWindowHandle();
		switchToWindowOpened(mainWindowHandle);

		sleepWait(2000);
		WebElement other = driver.findElement(By.xpath("//li[@class='listItem4']/a"));
		clickAction(other);

		driver.switchTo().window(mainWindowHandle);

		sleepWait(2000);

		WebElement endTimeField = driver.findElement(By.xpath("//span/input[@id='EndDateTime_time']"));
		clickAction(endTimeField);

		WebElement ninePm = driver.findElement(By.xpath("//div[@id='timePickerItem_42']"));
		clickAction(ninePm);

		WebElement save = driver.findElement(By.xpath("//td/input[@title='Save']"));
		clickAction(save);

	}

	@Test(priority = 32, alwaysRun = true)
	public static void recurringEvent37() {

		loginToSalesforce();

		WebElement homePage = driver.findElement(By.xpath("//li[@id='home_Tab']"));
		clickAction(homePage);		
		popupClose();

		WebElement currentDate = driver.findElement(By.xpath("//span/a[1]"));
		clickAction(currentDate);

		sleepWait(2000);
		WebElement fourpm = driver.findElement(By.xpath("//div[@id='p:f:j_id25:j_id61:20:j_id64']/a"));
		clickAction(fourpm);

		WebElement subjectCombo = driver.findElement(By.xpath("//img[@class='comboboxIcon']"));
		clickAction(subjectCombo);

		String mainWindowHandle = driver.getWindowHandle();
		switchToWindowOpened(mainWindowHandle);

		sleepWait(4000);
		WebElement other = driver.findElement(By.xpath("//li[@class='listItem4']/a"));
		clickAction(other);

		driver.switchTo().window(mainWindowHandle);

		sleepWait(2000);

		WebElement endTimeField = driver.findElement(By.xpath("//span/input[@id='EndDateTime_time']"));
		clickAction(endTimeField);

		WebElement sevenPm = driver.findElement(By.xpath("//div[@id='timePickerItem_38']"));
		clickAction(sevenPm);

		WebElement recurrence = driver.findElement(By.xpath("//input[@id='IsRecurrence']"));
		clickAction(recurrence);

		WebElement weekly = driver.findElement(By.xpath("//input[@id='rectypeftw']"));
		clickAction(weekly);

		WebElement recurrenceEnd = driver.findElement(By.xpath("//input[@id='RecurrenceEndDateOnly']"));
		clickAction(recurrenceEnd);

		WebElement endDate = driver.findElement(By.xpath("//tr/td[text()='25']"));
		clickAction(endDate);

		WebElement save = driver.findElement(By.xpath("//input[@value=' Save '][1]"));
		clickAction(save);

		WebElement monthView = driver.findElement(By.xpath("//img[@alt='Month View']"));
		clickAction(monthView);

	}

	public static void main(String[] args) throws AWTException {
		// TODO Auto-generated method stub

		// loginErrorMessage1();
		// loginToSalesForce2();
		// checkRememberme3();
		// forgotPassword4A();
		// forgotPassword4B();
		// userMenu5();
		// myProfileOption6();
		// mySettingsOption7();
		// developersConsole8();
		// logoutOption9() ;
		// createAccount10();
		// createNewView11();
		// editView12();
		// mergeAccounts13();
		// createAccountReport14();
		// opportunitiesDropdown15();
		// createNewOpty16();
		// testOptyPipelineReport17();
		// testStuckOptyReport18();
		// testQuaterlySummaryReport19();
		// leadsTab20();
		// leadsSelectView21();
		// defaultView22();
		// todaysLeads23();
		// newLeads24();
		// createNewContact25();
		// contactNewView26();
		// recentlyCreatedContact27();
		// myContacts28();
		// viewContact29();
		// errorNewView30();
		// cancelNewView31();
		// createsaveAndNew32();
		// firstlastName33();
		// verifyEditedlastName34();
		// verifyTabCustomization35();
		// blockingEvent36();
		// recurringEvent37();
	}

}
