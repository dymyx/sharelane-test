package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ShopingCartPage {
	private WebDriver driver;
	private Wait<WebDriver> wait;

	private final By updateBtn = By.cssSelector("[value='Update']");
	private final By checkoutBtn = By.cssSelector("[value='Proceed to Checkout']");
	private final By paymentBtn = By.cssSelector("[value='Make Payment']");

	private final By cardNumberField = By.name("card_number");

	private final By orderMessage = By.xpath("//font[@color='green']/b");
	private final By errorMessage = By.cssSelector(".error_message");

	private final By orderId = By.xpath("//td/p[contains(text(), 'Order id:')]/b");

	private final By fieldCarNumber = By.xpath("//td[contains(text(), 'Visa')]/following-sibling::td[1]/span/b");

	public ShopingCartPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}

	public ShopingCartPage open() {
		driver.get("https://www.sharelane.com/cgi-bin/shopping_cart.py");
		return this;
	}

	public ShopingCartPage addBook() {
		driver.get("https://www.sharelane.com/cgi-bin/add_to_cart.py?book_id=9");
		return this;
	}

	public ShopingCartPage updateCart() {
		wait.until(ExpectedConditions.elementToBeClickable(updateBtn)).click();
		wait.until(ExpectedConditions.elementToBeClickable(checkoutBtn));
		return this;
	}

	public ShopingCartPage checkout() {
    wait.until(ExpectedConditions.elementToBeClickable(checkoutBtn)).click();
    wait.until(ExpectedConditions.urlContains("checkout.py"));
    return this;
}


	public ShopingCartPage createCard() {
		driver.get("https://www.sharelane.com/cgi-bin/get_credit_card.py?type=1");
		return this;
	}

	public String getCardNumber() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(fieldCarNumber));
		return driver.findElement(fieldCarNumber).getText().trim();
	}

	public ShopingCartPage addCardNumber(String cardNumber) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(cardNumberField)).clear();
		driver.findElement(cardNumberField).sendKeys(cardNumber);
		return this;
	}

	public ShopingCartPage payment() {
		wait.until(ExpectedConditions.elementToBeClickable(paymentBtn)).click();
		return this;
	}

	public String getOrderMessage() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(orderMessage));
		return driver.findElement(orderMessage).getText().trim();
	}

	public String getOrderId() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(orderId));
		return driver.findElement(orderId).getText().trim();
	}

	public String getErrorMessage() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
		return driver.findElement(errorMessage).getText().trim();
	}

	public boolean isOrderNotPlaced() {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(orderId));
			return false;
		} catch (Exception e) {
			return true;
		}
	}
}
