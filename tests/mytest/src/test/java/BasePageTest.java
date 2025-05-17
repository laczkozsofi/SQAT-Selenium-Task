import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;
import java.net.MalformedURLException;

public class BasePageTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setup() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        HashMap<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        prefs.put("profile.default_content_setting_values.popups", 2);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, 10);
    }

    private final By bodyLocator = By.tagName("body");
    private final By cookieBtn1Locator = By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll");
    private final By cookieBtn2Locator = By.cssSelector("a.js-nanobar-close-cookies");
    private final By loginLinkLocator = By.cssSelector("a.nav-link.btn[href*='account/login']");
    private final By emailInputLocator = By.id("email_login");
    private final By passwordInputLocator = By.id("password_login");
    private final By loginButtonLocator = By.cssSelector("button.button.btn.btn-lg.btn-xl.btn-primary");
    private final By accountMenuLocator = By.className("account-account-menu");
    private final By logoutMenuLocator = By.cssSelector("li.dropdown.logged-dropdown");
    private final By logoutLinkLocator = By.cssSelector("ul.dropdown-hover-menu a[href*='account/logout']");
    private final By accountEditLocator = By.cssSelector("a[href*='account/edit']");
    private final By lastNameLocator = By.name("lastname");
    private final By forwardBtnLocator = By.xpath("//div//button[@type='submit' and contains(text(), 'Tov')]");


    private WebElement waitVisibiltyAndFindElement(By locator) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this.driver.findElement(locator);
    }

    private void acceptCookies() {
        WebElement cookieBtn1 = waitVisibiltyAndFindElement(cookieBtn1Locator);
        cookieBtn1.click();

        WebElement cookieBtn2 = wait.until(ExpectedConditions.elementToBeClickable(cookieBtn2Locator));
        cookieBtn2.click();
    }

    private void login() {
        WebElement link = waitVisibiltyAndFindElement(loginLinkLocator);
        link.click();
        
        WebElement emailInput = waitVisibiltyAndFindElement(emailInputLocator);
        emailInput.sendKeys("elekteszt480@gmail.com");

        WebElement passwordInput = this.driver.findElement(passwordInputLocator);
        passwordInput.sendKeys("SecretPassword");

        WebElement loginBtn = this.driver.findElement(loginButtonLocator);
        loginBtn.click();
    }

    private void logout() {
        Actions actions = new Actions(driver);
        WebElement menu = waitVisibiltyAndFindElement(logoutMenuLocator);
        actions.moveToElement(menu).click().perform();
        WebElement logoutLink = wait.until(ExpectedConditions.elementToBeClickable(logoutLinkLocator));
        logoutLink.click();
    }

    @Test
    public void testLoginLogout() {
        this.driver.get("https://21.szazadkiado.hu");
        acceptCookies();

        login();
        
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(accountMenuLocator));
        Assert.assertTrue(driver.getTitle().matches("Fi.kom"));

        logout();

        WebElement loginBtn = this.driver.findElement(loginButtonLocator);
        Assert.assertTrue(loginBtn.isDisplayed());

    }

    @Test
    public void testSendForm() {
        this.driver.get("https://21.szazadkiado.hu");
        acceptCookies();
        login();

        WebElement editAccountLink = waitVisibiltyAndFindElement(accountEditLocator);
        editAccountLink.click();

        String randomString = new Random().ints(97, 123)
            .limit(10)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
        WebElement lastNameInput = waitVisibiltyAndFindElement(lastNameLocator);
        lastNameInput.clear();
        lastNameInput.sendKeys(randomString);

        WebElement forwardBtn = this.driver.findElement(forwardBtnLocator);
        forwardBtn.click();

        this.wait.until(ExpectedConditions.visibilityOfElementLocated(accountMenuLocator));
        WebElement bodyElement = this.driver.findElement(bodyLocator);

        Assert.assertTrue(bodyElement.getText().contains("Siker"));

        logout();
    }

    

    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
