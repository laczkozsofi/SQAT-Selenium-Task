import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends Page {

    private final By emailInputLocator = By.id("email_login");
    private final By passwordInputLocator = By.id("password_login");
    private final By loginButtonLocator = By.cssSelector("button.button.btn.btn-lg.btn-xl.btn-primary");


    public LoginPage(WebDriver driver, WebDriverWait wait){
        super(driver, wait);
    }

    public AccountPage login() {
        WebElement emailInput = waitVisibiltyAndFindElement(emailInputLocator);
        emailInput.sendKeys("elekteszt480@gmail.com");

        WebElement passwordInput = this.driver.findElement(passwordInputLocator);
        passwordInput.sendKeys("SecretPassword");

        WebElement loginBtn = this.driver.findElement(loginButtonLocator);
        loginBtn.click();

        return new AccountPage(driver, wait);
    }
}
