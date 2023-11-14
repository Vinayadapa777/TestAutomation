package NaukriTestcases;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import PageObjects.HomePage;
import PageObjects.LoginPage;
import Utilities.ActionFunctions;

public class NaukriTestcases extends ActionFunctions {
    HomePage hp;
    LoginPage lp;

    @Test(priority = 1)
    public void LaunchUrl() {
	String url = prop.getProperty("url");
	launchUrl(url);
	System.out.println("Actual Url :" + url + " Expected Url : " + driver.getCurrentUrl() + "");
	Assert.assertTrue(url.contains(driver.getCurrentUrl()));
    }

    @Test(priority = 2, dependsOnMethods = ("LaunchUrl"), dataProvider = "userCredentials")
    public void user_Login(String userName, String password, String expectedUserName, String resume)
	    throws InterruptedException {
	lp = new LoginPage(driver);
	hp = new HomePage(driver);
	lp.userLogin(userName, password);
	Thread.sleep(3000);
	String actualuserName = hp.userName.getText();
	System.out.println("Actual Name :" + actualuserName + " Expected userName : " + expectedUserName + "");
	Assert.assertTrue(actualuserName.equalsIgnoreCase(expectedUserName));
	Thread.sleep(3000);
	// Auto It Function
	driver.findElement(By.xpath("//div[@class='view-profile-wrapper']/child::a")).click();

	// WebElement
	// uploadresume=driver.findElement(By.xpath("//div[@class='uploadContainer']/child::input[@id='attachCV']"));

	WebElement uploadresume = driver
		.findElement(By.xpath("//span[contains(text(),'Resume')]/following-sibling::a"));
	System.out.println(uploadresume.isDisplayed());
	uploadresume.click();
	Thread.sleep(2000);
//	WebElement crosspopup = driver.findElement(By.xpath("//i[contains(@class,'naukicon cross')]"));
//	if (crosspopup.isDisplayed()) {
//	    crosspopup.click();
//	}
	String fileuploadPath = resume;
	try {
	    Runtime.getRuntime().exec(fileuploadPath);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	Thread.sleep(3000);
	driver.findElement(By.xpath("//span[@class='icon-wrap']/child::i[contains(text(),'download')]")).click();
	File f = new File(downloadPath + "\\Resume.pdf");
	Thread.sleep(3000);
	Assert.assertTrue(f.exists());
	if (f.exists()) {
//	    f.delete();
	}
	hp.Logout();
    }

    @DataProvider(name = "userCredentials")
    public Object[][] usercrdentials() {
	String userName = prop.getProperty("emailid");
	String password = prop.getProperty("password");
	String user = prop.getProperty("userName");
	String fileuploadPath = System.getProperty("user.dir") + "\\InputFiles" + "\\vinayresume.exe";

	String userName1 = prop.getProperty("emailid1");
	String password1 = prop.getProperty("password1");
	String user1 = prop.getProperty("userName1");
	String fileuploadPath1 = System.getProperty("user.dir") + "\\InputFiles" + "\\rajeswariresume.exe";
	return new Object[][] { { userName, password, user, fileuploadPath },
		{ userName1, password1, user1, fileuploadPath1 } };
    }

}