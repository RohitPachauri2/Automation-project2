package TestCases;

import java.time.Duration;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import Pages.BaseTest;
import Pages.CartPage;
import Pages.CheckoutPage;
import Pages.Loginpage;
import Utility.ReadExcelFile;

@Test
public class CheckoutTestCase extends BaseTest {
    String filepath = System.getProperty("user.dir") + "\\Testdata\\Book1.xlsx";
    SoftAssert assert4 = new SoftAssert();

    public void checkoutcase() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        Loginpage lp = new Loginpage(driver);
        
        for (int i = 0; i < 4; i++) {
            String username = ReadExcelFile.getcellvalue(filepath, "Logindata", i, 0);
            String password = ReadExcelFile.getcellvalue(filepath, "Logindata", i, 1);

            // Try to login
            boolean loginSuccessful = lp.login(username, password);

            if (loginSuccessful) {
                // Proceed if login is successful
                CartPage cp = new CartPage(driver);
                try {
                    cp.addtocart();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                CheckoutPage cp1 = new CheckoutPage(driver);
                cp1.checkout();
                assert4.assertTrue(driver.getCurrentUrl().contains("checkout-complete"));
                System.out.println("Checkout completed successfully for: " + username);
                lp.logout();
            } else {
                // If login fails, log the failure and continue to the next iteration
                System.out.println("Login failed for user: " + username);
            }

            // You can also add a small wait here if you want to avoid rapid successive logins
            Thread.sleep(1000);
        }
    }
}
