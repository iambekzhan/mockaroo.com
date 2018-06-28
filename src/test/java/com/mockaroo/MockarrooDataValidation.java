package com.mockaroo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MockarrooDataValidation {

	WebDriver driver;
	String url = "https://mockaroo.com/";
	JavascriptExecutor js = (JavascriptExecutor) driver;
	List<String> lst;
	List<String> cities;
	List<String> countries;
	BufferedReader br;
	String firstRow;
	String line;

	@BeforeClass
	public void setup() {
		// set up chrome driver path
		WebDriverManager.chromedriver().setup();

		// instanciate an object of webdriver
		driver = new ChromeDriver();

		// set up to open fullscreen window of chrome
		driver.manage().window().fullscreen();

		// getting to url
		driver.get(url);
		
	}

//	@BeforeMethod
//	public void bMethod() {
//		
//	}

	@Test (priority = 1)
	public void verifyTitle() {
		// getting the actual title and comparing with expected title
		String actualTitle = driver.getTitle();
		String expectedTitle = "Mockaroo - Random Data Generator and API Mocking Tool | JSON / CSV / SQL / Excel";
		Assert.assertEquals(actualTitle, expectedTitle, "Title is not same");
	}

	@Test (priority = 2)
	public void verifyText() {
		// getting the actual brand and tagline, then comparing with the expected
		// String actualBrand =
		// driver.findElement(By.xpath("//div[@class='brand']")).getText();
		// String actualTagline =
		// driver.findElement(By.xpath("//div[@class='tagline']")).getText();
		//
		// String expectedBrand = "mockaroo";
		// String expectedTagline = "realistic data generator";

		boolean brand = driver.findElement(By.xpath("//div[@class='brand']")).isDisplayed();
		boolean tagLine = driver.findElement(By.xpath("//div[@class='tagline']")).isDisplayed();

		Assert.assertTrue(brand, "Brand name is not displayed");
		Assert.assertTrue(tagLine, "Tag Line is not displayed");
		// Assert.assertEquals(actualBrand, expectedBrand);
		// Assert.assertEquals(actualTagline, expectedTagline);
	}

	@Test (priority = 3)
	public void removeAllField() throws InterruptedException {
		// getting each element to remove
		List<WebElement> elements = driver
				.findElements(By.xpath("//a[@class='close remove-field remove_nested_fields']"));

		// removing each element by clicking "x"
		for (int i = 0; i < elements.size(); i++) {
			elements.get(i).click();
			Thread.sleep(500);
		}
		

	}

	@Test (priority = 4)
	public void checkColumnsNames() {
		// getting each columns has the text
		boolean fieldName = driver.findElement(By.xpath("//div[@class='column column-header column-name']"))
				.isDisplayed();
		boolean type = driver.findElement(By.xpath("//div[@class='column column-header column-type']")).isDisplayed();
		boolean options = driver.findElement(By.xpath("//div[@class='column column-header column-options']"))
				.isDisplayed();

		// checking each columns has name
		Assert.assertTrue(fieldName, "Field Name - is displayed");
		Assert.assertTrue(type, "Type - is displayed");
		Assert.assertTrue(options, "Options - is displayed");
	}

	@Test (priority = 5)
	public void addAnotherFieldButtonEnabled() {
		// finding and checking that there is a button 'add another field'
		boolean addAnotherField = driver
				.findElement(By.xpath("//a[@class='btn btn-default add-column-btn add_nested_fields']")).isEnabled();
		Assert.assertTrue(addAnotherField, "Add Another Field - is enabled");
	}

	@Test (priority = 6)
	public void rowsNumber1000() {
		// checking that inside rows values 1000
		String rowValue = driver.findElement(By.id("num_rows")).getAttribute("value");
		Assert.assertEquals(rowValue, "1000", "Row has value of 1000");
	}

	@Test (priority = 7)
	public void formatIsCSV() {
		// checking that format is CSV
		WebElement format = driver.findElement(By.id("schema_file_format"));
		Select formatSelect = new Select(format);
		String form = formatSelect.getFirstSelectedOption().getText();
		Assert.assertEquals(form, "CSV", "Format is not CSV");
	}

	@Test (priority = 8)
	public void lineEndingUnix() {
		// checking that line ending: Unix (LF)
		WebElement lineEnding = driver.findElement(By.id("schema_line_ending"));
		Select lineEndingSelect = new Select(lineEnding);
		String lineEn = lineEndingSelect.getFirstSelectedOption().getText();
		Assert.assertEquals(lineEn, "Unix (LF)", "Line ending is not Unix (LF)");
	}
	
	@Test (priority = 9)
	public void checkboxCheckedOrNot() {
		// finding and checking if checkbox is checked or not
		boolean header = driver.findElement(By.id("schema_include_header")).isSelected();
		boolean BOM = driver.findElement(By.id("schema_bom")).isSelected();
		
		Assert.assertTrue(header, "Header is not selected");
		Assert.assertFalse(BOM, "BOM is selected");
		
	}
	
	@Test (priority = 10)
	public void enterNameCity() {
		//clicking to add another field
		WebElement button = driver.findElement(By.xpath("//div[@class='column-fields']//a[@class='btn btn-default add-column-btn add_nested_fields']"));
		button.click();
		
		// finding and send key to box - "City"
		List<WebElement> cityBox = driver.findElements(By.xpath("//input[@placeholder='enter name...']"));
		for(int i = 0; i < cityBox.size(); i++) {
			cityBox.get(cityBox.size()-1).sendKeys("City");
			break;
		}

	}
	
	@Test (priority = 11)
	public void chooseTypeDialogIsDisplayedForCity() throws InterruptedException {
		
		// choose and click choose Type
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@class='btn btn-default' and @value='']")).click();
		
		// switch the window
		Page.SwitchWindowToNext(driver);
		
		// finding and comparing popup windows name
		Thread.sleep(2000);
		String actualType = driver.findElement(By.xpath("//h3[@class='modal-title'][contains(text(),'Choose a Type')]")).getText();
		String expectedType = "Choose a Type";
		
		Assert.assertEquals(actualType, expectedType, "Name of Type is different");
		
	}
	
	@Test (priority = 12)
	public void searchCity() throws InterruptedException {
		// searching for city
		driver.findElement(By.id("type_search_field")).sendKeys("city");
		Thread.sleep(500);
		// and clicking cities
		driver.findElement(By.xpath("//div[@class='type-name']")).click();
	
	}
	
	// same as Tests 10-12, but only for country
	@Test (priority = 13)
	public void enterNameCountry() throws InterruptedException {
		Thread.sleep(500);
		WebElement button = driver.findElement(By.xpath("//a[@class='btn btn-default add-column-btn add_nested_fields']"));
		button.click();
		
		List<WebElement> countryBox = driver.findElements(By.xpath("//input[@placeholder='enter name...']"));
		for(int i = 0; i < countryBox.size(); i++) {
			countryBox.get(countryBox.size()-1).clear();
			countryBox.get(countryBox.size()-1).sendKeys("Country" + Keys.TAB + Keys.ENTER);
			break;
		}
//		Thread.sleep(1000);

	}
	
	@Test (priority = 14)
	public void chooseTypeDialogIsDisplayedForCountry() throws InterruptedException {
		
		Page.SwitchWindowToNext(driver);
		
		Thread.sleep(1000);
		String actualType = driver.findElement(By.xpath("//h3[@class='modal-title'][contains(text(),'Choose a Type')]")).getText();
		String expectedType = "Choose a Type";
		
		Assert.assertEquals(actualType, expectedType, "Name of Type is different");
		
	}
	
	@Test (priority = 15)
	public void searchCountry() throws InterruptedException {
		WebElement box = driver.findElement(By.xpath("//input[@id='type_search_field']"));
		box.clear();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@id='type_search_field' and @tabindex='1']")).sendKeys("country");
//		Page.SwitchWindowToNext(driver);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@tabindex='1']//div[@class='examples']")).click();
		
	}
	
	@Test (priority = 16)
	public void downloadData() throws InterruptedException {
		// downloading data
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[@id='download']")).click();
	}
	
	@Test (priority = 17)
	public void openDownloadedData() throws IOException, InterruptedException {
		// open downloaded CSV data file
		Thread.sleep(5000);
		br = new BufferedReader(new FileReader("/Users/iambekzhan/Downloads/MOCK_DATA.csv"));

	}
	
	@Test (priority = 18)
	public void chkTheFirstRowInData() throws IOException {
		// Check that first Row is City,Country
		
		while ((firstRow = br.readLine()) != null) {
			Assert.assertEquals(firstRow, "City,Country", "The First Row is not matchin to our requiring");
			break;
		}
//		System.out.println(firstRow);
	}
	
	@Test (priority = 19) 
	public void chkThat1000Rows() throws IOException {
		int row = 1000;
		int count = 0;
		lst = new ArrayList<>();
		
		while ((line = br.readLine()) != null) {
			lst.add(line);
			count++;
		}
//		System.out.println(count);
		Assert.assertEquals(count, row, "The Row is not 1000");
		
	}
	
	@Test (priority = 20)
	public void addCitiesToList() throws IOException {
		cities = new ArrayList<>();
		for(int i = 0; i < lst.size(); i++) {
			String[] a = lst.get(i).split(",");
			for(int j = 0; j < a.length; j+=2) {
				cities.add(a[j]);
			}
		}
//		System.out.println(cities);
	}
	
	@Test (priority = 20)
	public void addCountriesToList() throws IOException {
		countries = new ArrayList<>();
		for(int i = 0; i < lst.size(); i++) {
			String[] a = lst.get(i).split(",");
			for(int j = 1; j < a.length; j+=2) {
				countries.add(a[j]);
			}
		}
	}
	

	@AfterClass
	public void close() throws InterruptedException {
		Thread.sleep(8000);
//		 driver.quit();
	}

}
