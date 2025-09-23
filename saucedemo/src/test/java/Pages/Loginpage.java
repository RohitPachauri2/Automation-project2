package Pages;


import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Loginpage {

	public WebDriver driver;
	public Loginpage(WebDriver driver) {
		
		this.driver=driver;
		PageFactory.initElements(driver, this);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		
	}
	
	@FindBy(xpath="//input[@id=\"user-name\"]") WebElement username;
	@FindBy(css="input[id='password']")WebElement pass;
	@FindBy(id="login-button") WebElement submit;
	@FindBy(css="div.bm-burger-button>button") WebElement menu;
	@FindBy(css="a[id=\"logout_sidebar_link\"]") WebElement logout;
	public void login(String t_user,String t_pass) throws InterruptedException {
		username.sendKeys(t_user);
		pass.sendKeys(t_pass);
		submit.click();
		Thread.sleep(5000);
		
	}
	
	public void logout() {
		
		menu.click();
		logout.click();
		try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
