import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.example.BookStoreAPI;
//import org.example.TokenModel;
import org.example.User;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BookStoreAPItest {
//    BookStoreAPI bookStoreAPI;
    RequestSpecification requestSpecification;
    @BeforeTest
    public void setup() {
//    bookStoreAPI = new BookStoreAPI();
        requestSpecification =  RestAssured.given()
                .baseUri("https://demoqa.com")
                .contentType(ContentType.JSON);
    }

    private User generateUser(){
        return new User("testUser6", "testPassw0rd!" );
    }

    private String getRequestBody(Object o) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getToken(){
         requestSpecification
                 .when()
                 .body(getRequestBody(generateUser()))
                 .post("/Account/v1/GenerateToken")

                 .then()
                 .statusCode(200)
                 .body("status", Matchers.equalTo("Success"))
                 .body("result", Matchers.containsString("User authorized successfully."));
    }

    @Test
    public void getAllBookTest(){
        requestSpecification
                .when()
                .get("/BookStore/v1/Books")

                .then()
                .statusCode(200)
                .body("books[0].isbn", Matchers.equalTo("9781449325862"))
                .body("books[0].title", Matchers.equalTo("Git Pocket Guide"));
    }
}
