package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ZipCodePage {

    private WebDriver driver;
    private Wait<WebDriver> wait;

    private final By zipCodeField = By.name("zip_code");

    private final By continueButton = By.cssSelector("[value='Continue']");

    private final By errorMessage = By.cssSelector(".error_message");

    private final By registrationFormTitle = By.xpath("/html/body/center/table/tbody/tr[5]/td/table/tbody/tr[2]/td/table/tbody/tr[7]/td/span");

    public ZipCodePage (WebDriver driver){
        this.driver=driver;
        this.wait=new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    public ZipCodePage open(){
        driver.get("https://www.sharelane.com/cgi-bin/register.py");
        return this;
    }

    public ZipCodePage enterZipCode(String zip){
        driver.findElement(zipCodeField).clear();
        driver.findElement(zipCodeField).sendKeys(zip);
        return this;
    }
    public ZipCodePage clickContinue(){
        driver.findElement(continueButton).click();
        return this;
    }
    public String getError(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
				return driver.findElement(errorMessage).getText();
    }

    public boolean isRegistrationFormVisible(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(registrationFormTitle));
        return driver.findElement(registrationFormTitle).isDisplayed();
    }
}