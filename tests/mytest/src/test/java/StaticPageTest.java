import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class StaticPageTest {

    private WebDriver driver;

    private String pageUrl;
    private String pageTitle;

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "https://21.szazadkiado.hu", "21. Sz.zad Kiad." }, 
                { "https://21.szazadkiado.hu/elofizeteses-termekek-kategoria", "El.fizet.ses k.nyvklub" },
                { "https://21.szazadkiado.hu/konyvek-53/idegennyelvu-konyv", "IDEGENNYELV. K.NYVEK"},
                { "https://21.szazadkiado.hu/index.php?route=wishlist/wishlist", "K.v.ns.glist.m"}
        });
    }

    public StaticPageTest(String pageUrl, String pageTitle) {
        this.pageUrl = pageUrl;
        this.pageTitle = pageTitle;
    }

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
    }

    @Test
    public void staticPageTest() {
        this.driver.get(pageUrl);
        Assert.assertTrue(this.driver.getTitle().matches(pageTitle));
    }

    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
