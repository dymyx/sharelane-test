package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationPage {
	private WebDriver driver;
	private Wait<WebDriver> wait;

	private final By errorMessage = By.cssSelector(".error_message");
	private final By confirmationMessage = By.cssSelector(".confirmation_message");

	private final By registerBtn = By.cssSelector("[value='Register']");

	public RegistrationPage (WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public RegistrationPage open() {
		driver.get("https://www.sharelane.com/cgi-bin/register.py?page=1&zip_code=12345");
		return this;
	}

	public RegistrationPage sendDataToField(By element, String text) {
		driver.findElement(element).clear();
		driver.findElement(element).sendKeys(text);
		return this;
	}

	public RegistrationPage clickRegister(){
		driver.findElement(registerBtn).click();
		return this;
	}

	public String getMessage() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
		return driver.findElement(errorMessage).getText();
	}

	public String getConfirmationMessage() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationMessage));
		return driver.findElement(confirmationMessage).getText();
	}
}
