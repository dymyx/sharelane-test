package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;


public class LoginPage {
	private WebDriver driver;
	private Wait<WebDriver> wait;

	private final By emailField = By.name("email");
	private final By passwordField = By.name("password");
	private final By loginButton = By.cssSelector("[value='Login']");
	private final By userGreeting = By.cssSelector(".user");
	private final By logoutLink = By.linkText("Logout");

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public LoginPage open() {
		driver.get("https://www.sharelane.com/cgi-bin/main.py");
		return this;
	}

	public LoginPage login(String email, String password) {
		driver.findElement(emailField).clear();
		driver.findElement(emailField).sendKeys(email);
		driver.findElement(passwordField).clear();
		driver.findElement(passwordField).sendKeys(password);
		driver.findElement(loginButton).click();
		return this;
	}

		public boolean isLoggedIn() {
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(logoutLink));
				return true;
			} catch (Exception e) {
				return false;
			}
	}

	public String getUserGreeting() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(userGreeting));
		return driver.findElement(userGreeting).getText().trim();
	}
}
