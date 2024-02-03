
import org.example.ConfigProvider;
import org.junit.jupiter.api.extension.ExtendWith;
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

//    @Test
    public void notCorrectEmailTest003(){
        String fillName = "Katya";
        String fillEmail = "katya@gm";
        String fillCurrent = "123";
        String fillPermanent = "321";

        TextBox sendData = new TextBox(browser);
        sendData.fillData(fillName, fillEmail, fillCurrent, fillPermanent);

        JavascriptExecutor js = (JavascriptExecutor) browser;
        js.executeScript("window.scrollBy(0,500);");

        sendData.clickSubmitButton();


    }
}
