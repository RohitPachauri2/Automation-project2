package Pages;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import Utility.Browserfactory;
import Utility.dataprovider;

public class BaseTest {
	public WebDriver driver;
	public dataprovider dp = new dataprovider();

	@BeforeClass
	public void setup(ITestContext context) {

		driver = Browserfactory.startApplication(driver, dp.getbrowser(), dp.geturl());
		
	}

	@AfterClass
	public void close() {
		if(driver!=null) {
			Browserfactory.quitapplication(driver);
		}
		else {
			System.out.println("Driver is null, skipping quit.");
			}

	}

}
