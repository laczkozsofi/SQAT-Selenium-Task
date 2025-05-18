import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountPage extends Page {
    private final By logoutMenuLocator = By.cssSelector("li.dropdown.logged-dropdown");
    private final By accountEditLocator = By.cssSelector("a[href*='account/edit']");


    public AccountPage(WebDriver driver, WebDriverWait wait){
        super(driver, wait);
    }

    public LoginPage logout() {
        Actions actions = new Actions(driver);
        WebElement menu = waitVisibiltyAndFindElement(logoutMenuLocator);
        actions.moveToElement(menu).click().perform();
        By logoutLlinkLocator = By.cssSelector("ul.dropdown-hover-menu a[href*='account/logout']");
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(logoutLlinkLocator));
        WebElement logoutLink = wait.until(ExpectedConditions.elementToBeClickable(logoutLlinkLocator));
        logoutLink.click();

        return new LoginPage(driver, wait);
    }

    public EditAccountPage editAccountData() {
        WebElement editAccountLink = waitVisibiltyAndFindElement(accountEditLocator);
        editAccountLink.click();

        return new EditAccountPage(driver, wait);
    }
}
