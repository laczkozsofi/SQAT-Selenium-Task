import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class BasePage extends Page {

    private final By cookieBtn1Locator = By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll");
    private final By cookieBtn2Locator = By.cssSelector("a.js-nanobar-close-cookies");
    private final By loginLinkLocator = By.cssSelector("a.nav-link.btn[href*='account/login']");
    private final By contactLinkLocator = By.cssSelector("a[href*='information/contact']");

    public BasePage(WebDriver driver, WebDriverWait wait){
        super(driver, wait);
    }

    public void open() {
        this.driver.get("https://21.szazadkiado.hu");
    }

    public void acceptCookies() {
        WebElement cookieBtn1 = waitVisibiltyAndFindElement(cookieBtn1Locator);
        cookieBtn1.click();

        WebElement cookieBtn2 = wait.until(ExpectedConditions.elementToBeClickable(cookieBtn2Locator));
        cookieBtn2.click();
    }

    public LoginPage openLogin() {
        WebElement link = waitVisibiltyAndFindElement(loginLinkLocator);
        link.click();

        return new LoginPage(driver, wait);
    }

    public ContactPage openContact() {
        WebElement contactLink = waitVisibiltyAndFindElement(contactLinkLocator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", contactLink);
        contactLink.click();

        return new ContactPage(driver, wait);
    }
}