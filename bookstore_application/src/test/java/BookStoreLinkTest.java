import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.BookStore;
import org.testng.Assert;

public class BookStoreLinkTest {
    BookStore fillData = new BookStore();
    @Given("Open Book Store in Book store Application")
    public  void setUp(){
        BookStore openPage = new BookStore();
        openPage.openPage();
    }
    @When("Fill search field")
    public void fillField(){
        String searchBook = "Git";
        fillData.fillData(searchBook);
    }
    @When("Tap Book link")
    public void clickLink(){
        fillData.bookFindLink();
    }

    @Then("Book page is opened")
    public void openedPage(){
        Assert.assertEquals("Git Pocket Guide",fillData.openData());
    }

}
