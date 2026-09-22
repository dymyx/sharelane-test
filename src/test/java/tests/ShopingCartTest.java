package tests;

import pages.LoginPage;
import pages.RegistrationPage;
import pages.ShopingCartPage;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ShopingCartTest extends BaseTest {
	ShopingCartPage shopingCartPage;
	RegistrationPage registrationPage;
	LoginPage loginPage;

	@BeforeMethod(dependsOnMethods = "setUp")
	public void initPage() {
		shopingCartPage = new ShopingCartPage(driver);
		registrationPage = new RegistrationPage(driver);
		loginPage = new LoginPage(driver);
	}

	@Test 
	public void purchaseFlowSucceeds() {
		String uniqueEmail = "user" + System.currentTimeMillis() + "@test.com";
		registrationPage.open()
			.sendDataToField(By.name("first_name"), "Dmytro")
			.sendDataToField(By.name("last_name"), "Testov")
			.sendDataToField(By.name("email"), uniqueEmail)
			.sendDataToField(By.name("password1"), "12345")
			.sendDataToField(By.name("password2"), "12345")
			.clickRegister();
		Assert.assertEquals(registrationPage.getConfirmationMessage(), "Account is created!");

		String email = registrationPage.getRegisterEmail();
		String password = registrationPage.getRegisterPassword();

		loginPage.open().login(email, password);
		Assert.assertTrue(loginPage.isLoggedIn(), "Не удалось войти перед оформлением заказа");

		shopingCartPage.addBook()
			.open()
			.updateCart()
			.checkout()
			.addCardNumber()
			.payment();

			Assert.assertFalse(shopingCartPage.getOrderMessage().isEmpty(), "Сообщение об оформлении заказа не отображается");
			Assert.assertFalse(shopingCartPage.getOrderId().isEmpty(), "Order id не отображается");
	}

	@Test
	public void accessCartWithoutLoginShowsError() {
		shopingCartPage.open();
		Assert.assertEquals(shopingCartPage.getErrorMessage(), "Oops, error. You must log in");
	}

	@Test
	public void paymentWithoutCardNumberFails() {
		String uniqueEmail = "user" + System.currentTimeMillis() + "@test.com";
		registrationPage.open()
			.sendDataToField(By.name("first_name"), "Dmytro")
			.sendDataToField(By.name("last_name"), "Testov")
			.sendDataToField(By.name("email"), uniqueEmail)
			.sendDataToField(By.name("password1"), "12345")
			.sendDataToField(By.name("password2"), "12345")
			.clickRegister();
		Assert.assertEquals(registrationPage.getConfirmationMessage(), "Account is created!");

		String email = registrationPage.getRegisterEmail();
		String password = registrationPage.getRegisterPassword();

		loginPage.open().login(email, password);
		Assert.assertTrue(loginPage.isLoggedIn(), "Не удалось войти перед оформлением заказа");

		shopingCartPage.addBook()
			.open()
			.updateCart()
			.checkout()
			.payment();

		Assert.assertTrue(shopingCartPage.isOrderNotPlaced(), "Заказ не должен оформляться без номера карты, но order id отобразился");
	}
}

