import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.util.HashMap;
import java.net.MalformedURLException;

public class AccountTest {

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
    private final By loginButtonLocator = By.cssSelector("button.button.btn.btn-lg.btn-xl.btn-primary");
    private final By accountMenuLocator = By.className("account-account-menu");

    @Test
    public void testLoginLogout() {
        BasePage basePage = new BasePage(driver, wait);
        basePage.open();
        basePage.acceptCookies();

        LoginPage loginPage = basePage.openLogin();
        AccountPage accountPage = loginPage.login();
        
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(accountMenuLocator));
        Assert.assertTrue(driver.getTitle().matches("Fi.kom"));

        accountPage.logout();

        WebElement loginBtn = this.driver.findElement(loginButtonLocator);
        Assert.assertTrue(loginBtn.isDisplayed());

    }

    @Test
    public void testSendForm() {
        BasePage basePage = new BasePage(driver, wait);
        basePage.open();
        
        basePage.acceptCookies();

        LoginPage loginPage = basePage.openLogin();
        AccountPage accountPage = loginPage.login();

        EditAccountPage editAccountPage = accountPage.editAccountData();
        AccountPage accountPage2 = editAccountPage.editUserName();

        this.wait.until(ExpectedConditions.visibilityOfElementLocated(accountMenuLocator));
        WebElement bodyElement = this.driver.findElement(bodyLocator);

        Assert.assertTrue(bodyElement.getText().contains("Siker"));

        accountPage2.logout();
    }
    

    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
