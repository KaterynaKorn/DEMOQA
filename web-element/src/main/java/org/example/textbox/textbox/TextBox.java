package org.example.textbox.textbox;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class TextBox extends AbstractPage {

    @FindBy(id = "userName")
    private WebElement userName;
    @FindBy(id = "userEmail")
    private WebElement userEmail;
    @FindBy(id = "currentAddress")
    private WebElement currentAddress;
    @FindBy(id = "permanentAddress")
    private WebElement permanentAddress;
    @FindBy(id = "submit")
    private WebElement submitButton;
    @FindBy(id = "output")
    private WebElement outputInfo;

//    @FindBy(id = "name")
//    private WebElement name;


    public TextBox(WebDriver driver) {
        super(driver);
    }

    @Override
    public void openPage() {
        openPage(Path.TEXTBOX_URL);
    }

    public void clickSubmitButton(){
        submitButton.click();
    }

    public void fillData(String fillName, String fillEmail, String fillCurrentAddress, String fillPermanentAddress){
        userName.sendKeys(fillName);
        userEmail.sendKeys(fillEmail);
        currentAddress.sendKeys(fillCurrentAddress);
        permanentAddress.sendKeys(fillPermanentAddress);
    }

    public OutputInfo getOutputInfo(){

        OutputInfo output = new OutputInfo();
//        output.setName(name.getText());
//        System.out.println(name.getText());
        output.setName(outputInfo.findElement(By.id("name")).getText());
        output.setEmail(outputInfo.findElement(By.id("email")).getText());
        output.setAddressCurrent(outputInfo.findElement(By.id("currentAddress")).getText());
        output.setAddressPermanent(outputInfo.findElement(By.id("permanentAddress")).getText());

        return output;
    }
}
