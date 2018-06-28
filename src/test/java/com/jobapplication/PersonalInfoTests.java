package com.jobapplication;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PersonalInfoTests {
	
	WebDriver driver;
	String firstName;
	String lastName;
	int gender;
	String dateOfBirth;
	String email;
	String phoneNumber;
	String city;
	String state;
	String country;
	int annualSalary;
	List<String> technologies;
	int yearsOfExperience;
	String education;
	String github;
	List<String> certifications;
	String additionalSkills;
	Faker data = new Faker();
	Random random = new Random();
	
	@BeforeClass    // runs once for all tests
	public void setup() {
		System.out.println("Setting up WebDriver in @BeforeClass");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().fullscreen();
	}
	
	@BeforeMethod    // runs before each @Test
	public void navigateToHomePage() {
		System.out.println("Navigating to homepage in @BeforeMethod");
		driver.get("https://forms.zohopublic.com/murodil/form/JobApplicationForm/formperma/kOqgtfkv1dMJ4Df6k4_mekBNfNLIconAHvfdIk3CJSQ");
		firstName = data.name().firstName();
		lastName = data.name().lastName();
		gender = data.number().numberBetween(1, 3);
		dateOfBirth = data.date().birthday().toString();
		email = "sediz@creazionisa.com";
		phoneNumber = data.phoneNumber().cellPhone().replace(".", "");
		city = data.address().cityName();
		state = data.address().state();
		country = data.address().country();
		annualSalary = data.number().numberBetween(60000, 150000);
		technologies = new ArrayList<>();
		technologies.add("Java-" + data.number().numberBetween(1,4));
		technologies.add("HTML-" + data.number().numberBetween(1,4));
		technologies.add("Selenium WebDriver-" + data.number().numberBetween(1,4));
		technologies.add("TestNG-"+ data.number().numberBetween(1,4));
		technologies.add("Git-"+ data.number().numberBetween(1,4));
		technologies.add("Maven-"+ data.number().numberBetween(1,4));
		technologies.add("JUnit-"+ data.number().numberBetween(1,4));
		technologies.add("Cucumber-"+ data.number().numberBetween(1,4));
		technologies.add("API Automation-"+ data.number().numberBetween(1,4));
		technologies.add("JDBC-"+ data.number().numberBetween(1,4));
		technologies.add("SQL-"+ data.number().numberBetween(1,4));
		
		yearsOfExperience = data.number().numberBetween(0, 11);
		education = data.number().numberBetween(1, 4) + "";
		github = "https://github.com/iambekzhan/testing-automation.git";
		certifications = new ArrayList<>();
		certifications.add("Java OCA");
		certifications.add("AWS");
		additionalSkills = data.job().keySkills();
	}
	
	@Test
	public void submitFullApplication() {
		driver.findElement(By.xpath("//input[@name='Name_First']")).sendKeys(firstName);
		driver.findElement(By.xpath("//input[@name='Name_Last']")).sendKeys(lastName);
		setGender(gender);
		setDateOfBirth(dateOfBirth);
		driver.findElement(By.xpath("//input[@name='Email']")).sendKeys(email);
		driver.findElement(By.xpath("//input[@name='countrycode']")).sendKeys(phoneNumber);
		driver.findElement(By.xpath("//input[@name='Address_City']")).sendKeys(city);
		driver.findElement(By.xpath("//input[@name='Address_Region']")).sendKeys(state);
		Select countryElem = new Select(driver.findElement(By.xpath("//select[@id='Address_Country']")));
		countryElem.selectByIndex(data.number().numberBetween(1, countryElem.getOptions().size()));
		driver.findElement(By.xpath("//input[@name='Number']")).sendKeys(String.valueOf(annualSalary) + Keys.TAB);
		verifySalaryCalculation(annualSalary);
		driver.findElement(By.xpath("//em[.=' Next ']")).click();
		
		// SECOND PAGE ACTIONS
		
		setSkillset(technologies);
		if(yearsOfExperience > 0) {
			driver.findElement(By.xpath("//a[@rating_value='"+ yearsOfExperience +"']")).click();
		}
		Select educationList = new Select(driver.findElement(By.xpath("//select[@name='Dropdown']")));
		educationList.selectByIndex(data.number().numberBetween(1, educationList.getOptions().size()));
		driver.findElement(By.xpath("//input[@name='Website']")).sendKeys(github);
		setCertifications(certifications);
		driver.findElement(By.xpath("//em[.='Apply']")).click();
		
	}

	
	public void setCertifications(List<String> cert) {
		for(String certificate : cert) {
			driver.findElement(By.xpath("//input[@value='" + certificate + "']")).click();
		}
	}
	
	public void setSkillset(List<String> tech) {
		
		for (String skill : tech) {
			String technology = skill.substring(0, skill.length()-2);
			int rate = Integer.parseInt(skill.substring(skill.length()-1));
			
			String level = "";
			
			switch(rate) {
				case 1:
					level = "Expert";
					break;
				case 2:
					level = "Proficient";
					break;
				case 3:
					level = "Beginner";
					break;
				default:
					fail(rate + " is not a valid level");
			}
			
			String xpath = "//input[@rowvalue='"+ technology +"' and @columnvalue='"+ level +"']";
			driver.findElement(By.xpath(xpath)).click();
		}
	}
	
	public void verifySalaryCalculation(int annual) {
		String monthly = driver.findElement(By.xpath("//input[@name='Formula']")).getAttribute("value");
		String weekly = driver.findElement(By.xpath("//input[@name='Formula1']")).getAttribute("value");
		String hourly = driver.findElement(By.xpath("//input[@name='Formula2']")).getAttribute("value");
		
		System.out.println(monthly);
		System.out.println(weekly);
		System.out.println(hourly);
		
		DecimalFormat formatter = new DecimalFormat("#.##");
		
		assertEquals(Double.parseDouble(monthly), Double.parseDouble(formatter.format((double)annual / 12.0)));
		assertEquals(Double.parseDouble(weekly), Double.parseDouble(formatter.format((double)annual / 52.0)));
		assertEquals(Double.parseDouble(hourly), Double.parseDouble(formatter.format((double)annual / 52.0 / 40.0)));
	}
	
	public void setDateOfBirth(String bday) {
		String[] pieces = bday.split(" ");
		String birthDay = pieces[2] + "-" + pieces[1] + "-" + pieces[5];
		driver.findElement(By.xpath("//input[@id='Date-date']")).sendKeys(birthDay);
	}
	
	public void setGender(int n) {
		if(n == 1) {
			driver.findElement(By.xpath("//input[@value='Male']")).click();
		}else {
			driver.findElement(By.xpath("//input[@value='Female']")).click();
		}
	}
	
	@Test
	public void fullNameEmptyTest() {
		//firstly assert that you are on the correct page
		assertEquals(driver.getTitle(), "SDET Job Application");
		driver.findElement(By.xpath("//input[@name='Name_First']")).clear();
		driver.findElement(By.xpath("//input[@name='Name_Last']")).clear();
		driver.findElement(By.xpath("//em[.=' Next ']")).click();
	
		// write xpath with tagname +id
		// get the text and assert text equals to Enter a value for this field.
		String nameError = driver.findElement(By.xpath("//p[@id='error-Name']")).getText();
		assertEquals(nameError, "Enter a value for this field.");
	}
	
	@AfterClass
	public void finis() throws InterruptedException {
		Thread.sleep(10000);
//		driver.quit();
	}
	
	
}
