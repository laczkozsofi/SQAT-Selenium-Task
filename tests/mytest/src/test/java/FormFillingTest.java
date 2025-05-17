import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FormFillingTest {

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

    private final By cookieBtn1Locator = By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll");
    private final By cookieBtn2Locator = By.cssSelector("a.js-nanobar-close-cookies");
    private final By contactLinkLocator = By.cssSelector("a[href*='information/contact']");
    private final By contactFormLocator = By.id("contact");
    private final By nameInputLocator = By.name("name");
    private final By emailInputLocator = By.name("email");
    private final By interestTextAreaLocator = By.name("enquiry");
    private final By checkBoxLocator = By.id("form-element-gdpr_consent");

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

    @Test
    public void testSendForm() {
        this.driver.get("https://21.szazadkiado.hu");
        acceptCookies();

        WebElement contactLink = waitVisibiltyAndFindElement(contactLinkLocator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", contactLink);
        contactLink.click();

        this.wait.until(ExpectedConditions.visibilityOfElementLocated(contactFormLocator));
        this.fillInput(nameInputLocator, "Teszt Elek");
        this.fillInput(emailInputLocator, "elekteszt@480gmail.com");
        this.fillInput(interestTextAreaLocator, "This is a test.");
        this.fillCheckBox(checkBoxLocator);
        Assert.assertEquals("Teszt Elek", this.driver.findElement(nameInputLocator).getAttribute("value"));
        Assert.assertEquals("elekteszt@480gmail.com", this.driver.findElement(emailInputLocator).getAttribute("value"));
        Assert.assertEquals("This is a test.", this.driver.findElement(interestTextAreaLocator).getAttribute("value"));
        Assert.assertTrue(this.driver.findElement(checkBoxLocator).isSelected());

    }

    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
