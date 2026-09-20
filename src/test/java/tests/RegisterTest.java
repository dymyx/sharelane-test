package tests;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.RegistrationPage;

public class RegisterTest extends BaseTest {
	RegistrationPage registrationPage;

	@BeforeMethod(dependsOnMethods = "setUp")
	public void initPage() {
		registrationPage = new RegistrationPage(driver);
	}

	@Test 
	public void validRegistrationSucceeds(){
		String uniqueEmail = "user" + System.currentTimeMillis() + "@test.com";
		registrationPage.open()
			.sendDataToField(By.name("first_name"), "Dmytro")
			.sendDataToField(By.name("last_name"), "Testov")
			.sendDataToField(By.name("email"), uniqueEmail)
			.sendDataToField(By.name("password1"), "12345")
			.sendDataToField(By.name("password2"), "12345")
			.clickRegister();
		Assert.assertEquals(registrationPage.getConfirmationMessage(), "Account is created!");
	}

	@Test 
	public void emptyFormShowsError() {
		registrationPage.open().clickRegister();
		Assert.assertEquals(registrationPage.getMessage(), "Oops, error on page. Some of your fields have invalid data or email was previously used");
	}

	@Test 
	public void emailWithoutAtRejected(){
		registrationPage.open()
			.sendDataToField(By.name("first_name"), "Dmytro")
			.sendDataToField(By.name("email"), "invalidemail.com")
			.sendDataToField(By.name("password1"), "12345")
			.sendDataToField(By.name("password2"), "12345")
			.clickRegister();
		Assert.assertEquals(registrationPage.getMessage(), "Oops, error on page. Some of your fields have invalid data or email was previously used");
	}

	@Test 
	public void emailWithoutDotRejected(){
		registrationPage.open()
			.sendDataToField(By.name("first_name"), "Dmytro")
			.sendDataToField(By.name("email"), "invalid@emai	lcom")
			.sendDataToField(By.name("password1"), "12345")
			.sendDataToField(By.name("password2"), "12345")
			.clickRegister();
		Assert.assertEquals(registrationPage.getMessage(), "Oops, error on page. Some of your fields have invalid data or email was previously used");
	}

	@Test 
	public void showPasswordRejected(){
		registrationPage.open()
			.sendDataToField(By.name("first_name"), "Dmytro")
			.sendDataToField(By.name("email"), "user" + System.currentTimeMillis() + "@test.com")
			.sendDataToField(By.name("password1"), "123")
			.sendDataToField(By.name("password2"), "123")
			.clickRegister();
		Assert.assertEquals(registrationPage.getMessage(), "Oops, error on page. Some of your fields have invalid data or email was previously used");
	}
}
