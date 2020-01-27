package stepdefinition;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.exec.StreamPumper;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import com.google.common.io.Files;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;



@SuppressWarnings("deprecation")
public class iiqsd {

	static DateFormat dateformat = new SimpleDateFormat("MM-dd-yy-hhmm");
	static Date date = new Date();
	static String tstamp = dateformat.format(date).toString();

	WebDriver driver;
	Properties prop = new Properties();

	String extentReportFile = System.getProperty("user.dir")+ "\\logs\\extentReportFile"+tstamp+".html";
	String extentReportImage = System.getProperty("user.dir")+ "\\logs\\extentReportImage.png";

	// Create object of extent report and specify the report file path.
	ExtentReports extent = new ExtentReports(extentReportFile, false);

	// Start the test using the ExtentTest class object.
	ExtentTest extentTest = extent.startTest("ANZ IIQ Application","Verify Role Details");


	@Given("^User launch the ANZ IIQ application$")
	public void userLaunchtheANZIIQApplication() throws Throwable {

		FileInputStream fileInput = new FileInputStream("C:\\Users\\moharas2\\eclipse-workspace\\iiqbdd\\iiqprop.properties");
		prop.load(fileInput);

		//System.setProperty("webdriver.chrome.driver","C:\\Users\\moharas2\\eclipse-workspace\\iiqbdd\\chromedriver\\chromedriver.exe");
		System.setProperty("webdriver.chrome.driver",prop.getProperty("ChromeDriver"));
		driver = new ChromeDriver();
		driver.manage().deleteAllCookies();

		driver.get(prop.getProperty("URL"));
		driver.manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		Assert.assertEquals("Welcome to IdentityIQ", driver.findElement(By.xpath("//header/span")).getText());
		extentTest.log(LogStatus.INFO, "Browser lanuches the IIQ application");

	}


	@And("^enter Userid and Password and click on Submit button$")
	public void theUserEntersUseridAndPassword() throws Throwable {

		driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys(prop.getProperty("Userid"));
		driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys(prop.getProperty("Password"));
		driver.findElement(By.xpath("//input[@value='Login']")).click();
		extentTest.log(LogStatus.INFO, "Logged into the IIQ application");

	}


	@When("^User clicks on Setup tab and choose Roles option$")
	public void userClicksOnSetupTabAndChooseRoles() throws Throwable {

		boolean setupflag = selectitem("Setup");
		if(setupflag == true)
		{
			boolean rolesflag = selectitem("Roles");
			if(rolesflag != true)
			{
				driver.quit();
			}
		}

		Assert.assertEquals("Role Management", driver.findElement(By.xpath("//h1[contains(text(),'Role Management')]")).getText());
		extentTest.log(LogStatus.INFO, "Selecting the Role option");
	}


	@And("^enter (.*) in the search field$")
	public void enterTheRoleName(String rolename) throws Throwable {
		driver.findElement(By.xpath("//input[@placeholder='Enter a Role Name']")).sendKeys(rolename);

		extentTest.log(LogStatus.INFO, "Searching for the Role Name");
		WebElement stritem = driver.findElement(By.xpath("//div[@class='sectionHeader']"));
		if(stritem.getText().contains(rolename))
		{
			stritem.click();
			Thread.sleep(3000);

			System.out.println("\n");
			System.out.println("================== Role Name : "+rolename+" ==================");
		}		
	}


	@Then("^verify the (.*),(.*),(.*) and (.*) details$")
	public void verifyTheAttributesSection(String strDescrip,String strBusiRoleName, String strBusiDispName, String strOwner )
			throws Throwable{
		extentTest.log(LogStatus.INFO, "Verifying the Attribute details");

		String strRolepath = "//div[@id='roleDataSection']";
		String strapplvalue = null;
		int i=0;
		String[] strapplparams = {"Description","Name","Display Name","Owner"};
		String[] strattrschemaparams = {"Description","Business Role Name","Business Role Display Name","Delegate EMP ID"};
		String[] strschemavalues = {strDescrip,strBusiRoleName,strBusiDispName,strOwner};

		System.out.println("================== Attribute section validation  ==================");

		while(i<strapplparams.length)
		{
			strapplvalue = fetchattributelist(strRolepath, strapplparams[i]);
			matchlistcheck(strapplvalue, strattrschemaparams[i], strschemavalues[i]);
			i++;
		}
	}


	@And("^verify the (.*),(.*),(.*),(.*),(.*),(.*) and (.*) details for the (.*) in RoleInformation section$")
	public void verifyTheJobcodeOrg_Hrchy_LvlOrgBus_UnitRBITier_AndLending_FundamentalsDetailsForTheRoleNameInTheRoleInformationSection(
			String strJobcode,String strOrg_Hrchy_Lvl6, String strOrganization, String strBusinessUnit, 
			String strRBI, String strTier2, String strLndgfmtls, String strrolename) throws Throwable {

		String strRolepath = "//div[@id='roleAssignmentRule']";
		int j=0;
		String[] strmtchltschemaparams = {"Job Code","Organization Hierarchy Level6","Organization","Business Unit","RBI","Tier 2","Lending Fundamentals"};
		String[] strmtchltschemavalues = {strJobcode,strOrg_Hrchy_Lvl6,strOrganization,strBusinessUnit,strRBI,strTier2,strLndgfmtls,strrolename};

		extentTest.log(LogStatus.PASS, "Verifying the Matchlist details");
		System.out.println("================== MatchList section validation  ==================");

		String strmatchlisttext = fetchmatchlist(strRolepath);

		while(j<strmtchltschemaparams.length)
		{
			matchlistcheck(strmatchlisttext, strmtchltschemaparams[j], strmtchltschemavalues[j]);
			j++;
		}
	}


	@And("^User clicks on Logout button$")
	public void userClicksOnLogoutButton() throws Throwable {

		System.out.println("=================================================================");
		System.out.println("\n");

		WebElement maintab = driver.findElement(By.xpath("//div[@id='usernameMenu']"));
		if(maintab.isDisplayed())
		{
			maintab.click();
			boolean logoutflag = selectitem("Logout");
			if(logoutflag != true)
			{
				driver.quit();
			}
			else if(logoutflag == true)
			{
				driver.quit();
			}
		}
		extentTest.log(LogStatus.INFO, "Logout from IIQ application");
		// close report.
		extent.endTest(extentTest);

		// writing everything to document.
		extent.flush();
	}


	public String fetchmatchlist(String strRoledtlspath)
	{
		String strmatchlisttext = null;
		List<WebElement> ltmatchlist = driver.findElements(By.xpath(strRoledtlspath));
		Iterator<WebElement> itrmatch = ltmatchlist.iterator();
		while (itrmatch.hasNext()) 
		{
			WebElement webmatchtext = itrmatch.next();
			List<WebElement> ltmatchlisttext = webmatchtext.findElements(By.tagName("td"));
			Iterator<WebElement> itrmatchtext = ltmatchlisttext.iterator();
			while (itrmatchtext.hasNext()) 
			{
				WebElement webmatchlisttext = itrmatchtext.next();
				webmatchlisttext.click();
				strmatchlisttext = webmatchlisttext.getText();
			}
		}
		return strmatchlisttext;
	}


	@SuppressWarnings("unused")
	public void matchlistcheck(String strmatchtext1,String strcode, String strmatchitem) throws IOException, InterruptedException
	{
		try
		{
			if(strmatchtext1.contains(strmatchitem))
			{
				System.out.println("The given "+strcode+" : "+strmatchitem+" in schema MATCHED with "+strmatchtext1+"");
				extentTest.log(LogStatus.PASS, "The given "+strcode+" : "+strmatchitem+" in schema MATCHED with "+strmatchtext1+"");
			}
			else
			{
				System.out.println("The given "+strcode+" : "+strmatchitem+" in schema NOT MATCHED with "+strmatchtext1+"");
				extentTest.log(LogStatus.FAIL, "The given "+strcode+" : "+strmatchitem+" in schema NOT MATCHED with "+strmatchtext1+"");
				screenshot(strmatchitem);
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}


	public boolean selectitem(String selecttab)
	{
		List<WebElement> litem = driver.findElements(By.tagName("a"));
		Iterator<WebElement> iitem = litem.iterator();
		while(iitem.hasNext())
		{
			WebElement witem = iitem.next();
			String stritem = witem.getText();
			if(stritem.contains(selecttab))
			{
				witem.click();
				break;
			}
		}
		return true;
	}


	public void screenshot(String strerror) throws IOException 
	{
		TakesScreenshot scrshot = ((TakesScreenshot)driver);
		File scrfile = scrshot.getScreenshotAs(OutputType.FILE);

		File destfile = new File(prop.getProperty("Screenshot")+"\\logs\\extentReportImage.png");
		Files.copy(scrfile,destfile);

		extentTest.log(
				LogStatus.INFO,
				"Error Snapshot : "
						+ extentTest.addScreenCapture(extentReportImage));
	}


	public String fetchattributelist(String strRoledtlspath, String strattfld) throws InterruptedException
	{
		boolean flag = false;
		String strmatchlisttext;
		String strmatchlistsubtext = null;
		List<WebElement> ltmatchlist = driver.findElements(By.xpath(strRoledtlspath));
		Iterator<WebElement> itrmatch = ltmatchlist.iterator();
		while (itrmatch.hasNext()) 
		{
			WebElement webmatchtext = itrmatch.next();
			List<WebElement> ltmatchlisttext = webmatchtext.findElements(By.tagName("td"));
			Iterator<WebElement> itrmatchtext = ltmatchlisttext.iterator();
			while (itrmatchtext.hasNext()) 
			{
				WebElement webmatchlisttext = itrmatchtext.next();
				strmatchlisttext = webmatchlisttext.getText();
				if(strmatchlisttext.equalsIgnoreCase(strattfld))
				{
					WebElement webmatchlisttextval = itrmatchtext.next();
					webmatchlisttextval.click();
					strmatchlistsubtext = webmatchlisttextval.getText();
					flag=true;
					if(flag==true) {						
						break;}
				}
			}
		}
		return strmatchlistsubtext;
	}


	@And("^click on Required Roles section and verify the IT and AD roles(.*)$")
	public void clickOnRequiredRolesTab(String strITAD) throws Throwable 
	{
		extentTest.log(LogStatus.INFO, "Verifying the IT & AD records");
		String[] stritad1 = strITAD.split(";");
		//System.out.println("stritad1 :"+stritad1);
		String stritem = " ";
		String stritadschemaparams = "Name";

		WebElement wrerolehdr = driver.findElement(By.xpath("//span[text()='Required Roles']/../../div/img"));
		if(wrerolehdr.isDisplayed())
		{
			wrerolehdr.click();
		}

		System.out.println("================== IT & AD section validation  ==================");
		WebElement webitem;

		WebElement nextpage = driver.findElement(By.xpath("//div[@id='button-1224']"));
		WebElement wreqrolsection = driver.findElement(By.xpath("//div[@id='requiredRolesSectionPanel']"));
		ArrayList<String> ab = new ArrayList<String>();
		
		int y=0;
		while(y<3)
		{
			List<WebElement> listreqrole = wreqrolsection.findElements(By.tagName("td"));
			Iterator<WebElement> itrreqrole = listreqrole.iterator();
			while(itrreqrole.hasNext())
			{
				webitem = itrreqrole.next();
				stritem = webitem.getText();
	//			ab.add(stritem);

				int s=0;
				while(s<stritad1.length)
				{
					//itadrecordcheck(ab.set(s, stritem),stritadschemaparams,stritad1[s]);
					itadrecordcheck(stritem,stritadschemaparams,stritad1[s]);
					s++;
				}

				Thread.sleep(500);

				try
				{
					int j=0;
					while(j<2)
					{
						webitem = itrreqrole.next();
						j++;
					}
				}
				catch(NoSuchElementException e) {

				}
				catch(IndexOutOfBoundsException e1)
				{
					
				}
			}
			if(nextpage.isDisplayed())
			{
				nextpage.click();
				Thread.sleep(1000);
			}
			y++;
		}


	}


	private void itadrecordcheck(String stritem, String stritadschemaparams, String strfitad) {
		// TODO Auto-generated method stub
		
		try
		{
			if(stritem.contains(strfitad))
			{
				System.out.println("The given "+stritadschemaparams+" : "+strfitad+" in schema MATCHED with "+stritem+"");
				extentTest.log(LogStatus.PASS, "The given "+stritadschemaparams+" : "+strfitad+" in schema MATCHED with "+stritem+"");
			}
			else
			{
				System.out.println("The given "+stritadschemaparams+" : "+strfitad+" in schema NOT MATCHED with "+stritem+"");
				extentTest.log(LogStatus.FAIL, "The given "+stritadschemaparams+" : "+strfitad+" in schema NOT MATCHED with "+stritem+"");

				//screenshot(strfitad);
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}

		
		
	}


	@And("^click on Determine tab to verify application details(.*),(.*)$")
	public void clickOnDeterineTab(String DetRolename,String Application) throws Throwable {

		extentTest.log(LogStatus.INFO, "Verifying the Determine section");
		driver.navigate().refresh();

		WebElement wrerolehdr = driver.findElement(By.xpath("//span[text()='Required Roles']/../../div/img"));
		WebElement wreqrolsection = driver.findElement(By.xpath("//div[@id='requiredRolesSectionPanel']"));

		wrerolehdr.click();
		WebElement webreqroletext;
		String strreqrole1;

		System.out.println("================== Determine section validation  ==================");
		List<WebElement> listreqrole = wreqrolsection.findElements(By.tagName("td"));
		Iterator<WebElement> itrreqrole = listreqrole.iterator();

		try
		{
			while(itrreqrole.hasNext())
			{
				webreqroletext = itrreqrole.next();
				if(webreqroletext != null)
				{
					strreqrole1 = webreqroletext.getText();
					if(strreqrole1 == null)
					{
						break;
					}
					else if(strreqrole1 !=null)
					{
						//System.out.println("required role deter :"+strreqrole1);
						if(strreqrole1.equals(DetRolename))
						{
							webreqroletext.click();
							Thread.sleep(1000);
							break;
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		WebElement wreqrolsection13 = driver.findElement(By.xpath("//div[@id='gridview-1256']"));


		WebElement nextpage = driver.findElement(By.xpath("//div[@id='button-1162']"));
		int z=0;
		WebElement webitem13,webitem14,webitem15;
		String stritem13,stritem14,stritem15;
		while(z<3)
		{
			List<WebElement> listreqrole13 = wreqrolsection13.findElements(By.tagName("td"));
			Iterator<WebElement> itrreqrole13 = listreqrole13.iterator();
			while(itrreqrole13.hasNext())
			{
				webitem13 = itrreqrole13.next();
				stritem13 = webitem13.getText();

				//System.out.println("direct appl :"+stritem13);
				if(stritem13.contains(Application))
				{
					webitem14 = itrreqrole13.next();
					stritem14 = webitem14.getText();

					//System.out.println("Property val : "+stritem14);
					webitem15 = itrreqrole13.next();
					stritem15 = webitem15.getText();
					//System.out.println("Value val : "+stritem15);

					determinecheck(Application, stritem13,stritem14,stritem15);

				}
				/*if(fg==true)
			{
				break;
			}*/

			}
			if(nextpage.isDisplayed())
			{
				nextpage.click();
				Thread.sleep(1000);
			}	
			z++;

		}
	}

	private void determinecheck(String strfappl,String strappl, String strprop, String strval) {
		// TODO Auto-generated method stub



		try
		{
			if(strappl.contains(strfappl))
			{
				//System.out.println("The given "+strfappl+" : "+strmatchitem+" in schema MATCHED with "+strmatchtext1+"");

				System.out.println("The given "+strfappl+" in schema Matched with "+strappl+", property code "+strprop+" and value "+strval+"");
				extentTest.log(LogStatus.PASS, "The given "+strfappl+" in schema Matched with "+strappl+", property code "+strprop+" and value "+strval+"");
			}
			else
			{
				System.out.println("The given "+strfappl+" in schema NOT Matched with "+strappl+", property code "+strprop+" and value "+strval+"");
				extentTest.log(LogStatus.FAIL, "The given "+strfappl+" in schema NOT Matched with "+strappl+", property code "+strprop+" and value "+strval+"");

				screenshot(strfappl);
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}



	}



}


