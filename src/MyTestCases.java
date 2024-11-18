import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class MyTestCases {

	DesiredCapabilities caps = new DesiredCapabilities();

	String AppiumURL = "http://127.0.0.1:4723/wd/hub";

	AndroidDriver driver;
	
	Random rand = new Random();

	@BeforeTest
	public void mySetup() {

		caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		caps.setCapability(MobileCapabilityType.DEVICE_NAME, "phone");

		File myApplication = new File("src/MyApplications/calculator.apk");

		caps.setCapability(MobileCapabilityType.APP, myApplication.getAbsolutePath());
	}

	@Test(priority = 1, enabled = true)
	public void addTwoRandomNumbers() throws MalformedURLException {
		driver = new AndroidDriver(new URL(AppiumURL), caps);

		// Wait until the element is visible before clicking
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		String RandomNumber1 = Integer.toString(rand.nextInt(0,10));
		String RandomNumber2 = Integer.toString(rand.nextInt(0,10));
		driver.findElement(By.id("com.google.android.calculator:id/digit_"+RandomNumber1)).click();

		driver.findElement(By.id("com.google.android.calculator:id/op_add")).click();
		driver.findElement(By.id("com.google.android.calculator:id/digit_"+RandomNumber2)).click();

		String actual = driver.findElement(By.id("com.google.android.calculator:id/result_preview")).getText();
		String expected = Integer.toString(Integer.parseInt(RandomNumber1)+Integer.parseInt(RandomNumber2));

		System.out.println(actual + " is the actual value from the app");
		System.out.println(expected + " is the expected value");

		Assert.assertEquals(actual, expected);
	}

	@Test(priority = 2, enabled = false)
	public void clickOnAllNumbers() throws MalformedURLException {
		driver = new AndroidDriver(new URL(AppiumURL), caps);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(2));

		List<WebElement> allButtons = driver.findElements(By.className("android.widget.ImageButton"));

		for (WebElement button : allButtons) {
			if (button.getAttribute("resource-id").contains("digit")) {
				button.click();
			}
		}

		String actual = driver.findElement(By.id("com.google.android.calculator:id/formula")).getText();
		String expected = "7894561230";

		Assert.assertEquals(actual, expected);
	}

}
