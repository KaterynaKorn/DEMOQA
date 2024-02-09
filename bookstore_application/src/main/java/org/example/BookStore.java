package org.example;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

public class BookStore {
    private SelenideElement searchBox = Selenide.$(By.id("searchBox"));
    private SelenideElement bookLink = Selenide.$(By.id("see-book-Git Pocket Guide"));
    private SelenideElement userNameValue = Selenide.$("#title-wrapper #userName-value");
    public void openPage() {Selenide.open("/books");}

    public void fillData(String data) {
        searchBox.sendKeys(data);
    }

    public void bookFindLink(){
    bookLink.shouldBe(Condition.visible).click();
    }

    public String openData(){
        String bookName = userNameValue.getText();
        return bookName;
    }

//    bookGit.scrollIntoView("");
//    bookGit.pressEnter();
//    bookGit.shouldBe();

}
