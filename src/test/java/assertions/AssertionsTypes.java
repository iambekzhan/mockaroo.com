package assertions;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AssertionsTypes {
	
	WebDriver driver;
	
	@BeforeClass
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().fullscreen();
	}
	
	@Test
	public void navigator() throws InterruptedException {
		driver.get("https://statista.com/");
		List<WebElement> navigators = driver.findElements(By.xpath("//div[@id='navigation']/nav/ul/li"));
		
		Actions action = new Actions(driver);
		
		for(int i = 0; i < (navigators.size())-2; i++) {
			action.moveToElement(navigators.get(i));
			Thread.sleep(1000);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
//	@Test
//	public void test1() {
//		String expectedName = "James";
//		int expectedAge = 28;
//		
//		SoftAssert soft = new SoftAssert();
//		soft.assertEquals("Ronaldo", expectedName);
//		soft.assertEquals(32, expectedAge);
//		System.out.println("Ending the test");
//		soft.assertAll();
//	}
//	
//	@Test
//	public void w() {
//		System.out.println("secont test method starting");
//		String s = "Hi";
//		
//		SoftAssert soft = new SoftAssert();
//		soft.assertEquals("Hello", s);
//		soft.assertAll();
//	}
//	
//	@AfterClass
//	public void ending() {
//		
//	}
	
	
	
}
