import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EditAccountPage extends Page {

    private final By lastNameLocator = By.name("lastname");
    private final By forwardBtnLocator = By.xpath("//div//button[@type='submit' and contains(text(), 'Tov')]");

    public EditAccountPage(WebDriver driver, WebDriverWait wait){
        super(driver, wait);
    }

    public AccountPage editUserName(){
        String randomString = new Random().ints(97, 123)
            .limit(10)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
        WebElement lastNameInput = waitVisibiltyAndFindElement(lastNameLocator);
        lastNameInput.clear();
        lastNameInput.sendKeys(randomString);

        WebElement forwardBtn = this.driver.findElement(forwardBtnLocator);
        forwardBtn.click();

        return new AccountPage(driver,wait);
    }
}
