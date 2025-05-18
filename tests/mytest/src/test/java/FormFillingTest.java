import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
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

    private final By nameInputLocator = By.name("name");
    private final By emailInputLocator = By.name("email");
    private final By interestTextAreaLocator = By.name("enquiry");
    private final By checkBoxLocator = By.id("form-element-gdpr_consent");

    @Test
    public void testSendForm() {
        BasePage basePage = new BasePage(driver, wait);
        basePage.open();
        basePage.acceptCookies();
        
        ContactPage contactPage = basePage.openContact();
        contactPage.fillContactForm("Teszt Elek", "elekteszt@480gmail.com", "This is a test.");

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
