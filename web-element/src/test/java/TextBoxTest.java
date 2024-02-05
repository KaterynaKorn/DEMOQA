
import io.qameta.allure.Description;
import org.example.ConfigProvider;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import textbox.OutputInfo;
import textbox.TextBox;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;


public class TextBoxTest {
    private WebDriver browser;
    private TextBox page;

    @BeforeEach
    public void setUp(){
        browser = ConfigProvider.getDriver();
        page = new TextBox(browser);
        page.openPage();
    }

    @AfterEach
    public void tearDown(){
        browser.quit();
    }

    @Test
    @Description("Compare field data - Test 002")
public void compareFieldDataTest002(){
       String fillName = "Katya";
       String fillEmail = "katya@gmail.com";
       String fillCurrent = "123";
       String fillPermanent = "321";

        TextBox sendData = new TextBox(browser);
        sendData.fillData(fillName, fillEmail, fillCurrent, fillPermanent);

        JavascriptExecutor js = (JavascriptExecutor) browser;
        js.executeScript("window.scrollBy(0,500);");

        sendData.clickSubmitButton();

        OutputInfo outputInfo = sendData.getOutputInfo();
        Assertions.assertEquals("Name:" + fillName,outputInfo.getName());
        Assertions.assertEquals("Email:" + fillEmail,outputInfo.getEmail());
        Assertions.assertEquals("Current Address :" + fillCurrent,outputInfo.getAddressCurrent());
        Assertions.assertEquals("Permananet Address :" + fillPermanent,outputInfo.getAddressPermanent());

    }

    @Test
    @Description ("Not Correct Email - Test003")
    public void notCorrectEmailTest003(){
        String fillName = "Katya";
        String fillEmail = "mas@gmai";
        String fillCurrent = "123";
        String fillPermanent = "321";

        TextBox sendData = new TextBox(browser);
        sendData.fillData(fillName, fillEmail, fillCurrent, fillPermanent);

        JavascriptExecutor js = (JavascriptExecutor) browser;
        js.executeScript("window.scrollBy(0,500);");

        sendData.clickSubmitButton();
        Assertions.assertThrows(NoSuchElementException.class, () ->{sendData.getOutputInfo();
        });




    }
}
