package Utility;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ITestListnerClass implements ITestListener{
	ExtentSparkReporter htmlReporter;
	ExtentReports reports;
	ExtentTest test;
	private WebDriver driver;
	public void configureReport() {
		htmlReporter= new ExtentSparkReporter("ExtentListenerReportDemo.html");
		reports= new ExtentReports();
		reports.attachReporter(htmlReporter);
		
		//add system information/environments info to reports
		reports.setSystemInfo("Machine", "PC");
		reports.setSystemInfo("OS","Windows1O");
		
		
		htmlReporter.config().setDocumentTitle("Extent Listener Report Demo");
		htmlReporter.config().setReportName("This is my first Report");
		htmlReporter.config().setTheme(Theme.DARK);
				
	}
	
	@Override
    public void onTestStart(ITestResult result) {
        driver = (WebDriver) result.getTestContext().getAttribute("driver");
        System.out.println("test starting-------");
    }

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("Name of the method: "+result.getName());
		test=reports.createTest(result.getName());
		test.log(Status.PASS, MarkupHelper.createLabel("Name of the skip test case is: "+result.getName(),ExtentColor.GREEN));
	}
	@Override
	public void onTestFailure(ITestResult result) {
		
	    System.out.println("Name of test method failed: " + result.getName());
	    
	    // Create a test entry in the Extent report
	    ExtentTest test = reports.createTest(result.getName());
	    test.log(Status.FAIL, MarkupHelper.createLabel("Test case failed: " + result.getName(), ExtentColor.RED));

	    // Define the screenshot path
	    String screenShotPath = System.getProperty("user.dir") + "\\Screenshots\\" + result.getName() + ".png";
	    System.out.println("Screenshot Path: " + screenShotPath); // Debugging

	    // Create the screenshot file and its parent directories if they don't exist
	    File screenShotFile = new File(screenShotPath);
	    File parentDir = new File(screenShotFile.getParent());
	    
	    if (!parentDir.exists()) {
	        parentDir.mkdirs();  // Create directories if they don't exist
	        System.out.println("Directory created: " + parentDir.getAbsolutePath()); // Debugging
	    }

	    
		// Capture screenshot using Selenium WebDriver
	    File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	    System.out.println("Screenshot captured: " + screenshot.getAbsolutePath()); // Debugging

	    try {
	        // Copy the captured screenshot to the destination
	        FileUtils.copyFile(screenshot, screenShotFile);
	        System.out.println("Screenshot saved to: " + screenShotFile.getAbsolutePath()); // Debugging

	        // Add the screenshot to the Extent report
	        test.fail("Captured Screenshot is below: " + test.addScreenCaptureFromPath(screenShotPath));
	    } catch (Exception e) {
	        // Log error if something goes wrong
	        test.fail("Screenshot could not be captured: " + e.getMessage());
	        System.out.println("Error while saving screenshot: " + e.getMessage()); // Debugging
	    }
	}
	@Override
	public void onTestSkipped(ITestResult result) {
	    System.out.println("Name of test method skipped "+result.getName());
	    test=reports.createTest(result.getName());
	    test.log(Status.SKIP, MarkupHelper.createLabel("Name of the skip test case is: "+result.getName(),ExtentColor.YELLOW));
	  }
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		  
	  }

	@Override
	  public void onTestFailedWithTimeout(ITestResult result) {
	    onTestFailure(result);
	  }

	@Override
	  public void onStart(ITestContext context) {
	    configureReport();
	    System.out.println("On start method invoked.....");
	  }
	@Override
	
	  public void onFinish(ITestContext context) {
	    System.out.println("On Finished method invoked.....");
	    reports.flush();// it is mandatory to call flush method to ensure information is written ti the started reporter. 
	  }

}
