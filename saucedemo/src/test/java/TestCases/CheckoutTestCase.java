package TestCases;

import java.time.Duration;

import org.testng.Assert;
import org.testng.annotations.Test;

import Pages.BaseTest;
import Pages.CartPage;
import Pages.CheckoutPage;
import Pages.Loginpage;
import Utility.ReadExcelFile;
@Test
public class CheckoutTestCase extends BaseTest {
	String filepath=System.getProperty("user.dir")+"\\Testdata\\Book1.xlsx";
	public void checkoutcase() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		Loginpage lp=new Loginpage(driver);
		String username=ReadExcelFile.getcellvalue(filepath, "Logindata", 0, 0);
		String password=ReadExcelFile.getcellvalue(filepath, "Logindata", 0, 1);
		try {
			lp.login(username, password);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CartPage cp=new CartPage(driver);
		cp.addtocart();
		CheckoutPage cp1=new CheckoutPage(driver);
		cp1.checkout();
		Assert.assertTrue(driver.getCurrentUrl().contains("checkout-complete"));
		System.out.println("new change");
		lp.logout();
		
	}

}
