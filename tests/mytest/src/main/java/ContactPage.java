import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ContactPage extends Page {

    private final By contactFormLocator = By.id("contact");
    private final By nameInputLocator = By.name("name");
    private final By emailInputLocator = By.name("email");
    private final By interestTextAreaLocator = By.name("enquiry");
    private final By checkBoxLocator = By.id("form-element-gdpr_consent");

    public ContactPage(WebDriver driver, WebDriverWait wait){
        super(driver, wait);
    }

    private void fillInput(By locator, String text) {
        WebElement input = this.driver.findElement(locator);
        input.sendKeys(text);
    }

    private void fillCheckBox(By locator) {
        WebElement checkbox = driver.findElement(locator);
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    public void fillContactForm(String name, String email, String text) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(contactFormLocator));
        this.fillInput(nameInputLocator, name);
        this.fillInput(emailInputLocator, email);
        this.fillInput(interestTextAreaLocator, text);
        this.fillCheckBox(checkBoxLocator);
    }
}
