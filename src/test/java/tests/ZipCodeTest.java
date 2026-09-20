package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ZipCodePage;

public class ZipCodeTest extends BaseTest {
	ZipCodePage zipCodePage;

	@BeforeMethod(dependsOnMethods = "setUp")
	public void initPage() {
		zipCodePage = new ZipCodePage(driver);
	}

	@Test
	public void checkZipCode4Digits(){
		zipCodePage.open().enterZipCode("1234").clickContinue();
		String errorMessage = zipCodePage.getError();
		Assert.assertEquals(errorMessage, "Oops, error on page. ZIP code should have 5 digits");
	}
	@Test
	public void checkZipCode5Digits(){
		zipCodePage.open().enterZipCode("12345").clickContinue();
		Assert.assertTrue(zipCodePage.isRegistrationFormVisible(), "Форма регистрации не отображается");
	}
}