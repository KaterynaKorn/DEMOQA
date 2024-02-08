import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.BookModel;
import org.example.User;
import org.hamcrest.Matchers;
import org.openqa.selenium.devtools.v85.fetch.model.AuthChallengeResponse;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class BookStoreAPItest {
    RequestSpecification requestSpecification;
    @BeforeTest
    public void setup() {
        requestSpecification =  RestAssured.given()
                .baseUri("https://demoqa.com")
                .contentType(ContentType.JSON);
    }

    private User generateUser(){
        return new User("testUser7", "testPassw0rd!");
    }

    private BookModel generateBook(){
        return new BookModel("17f889fa-ebb0-4c38-9ea9-74e475496644", "9781449331818");
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
    public void postToken(){
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

    @Test
    public void postBookNotAuthorizedTest(){
        requestSpecification
                .when()
                .body(getRequestBody(generateBook()))
                .post("/BookStore/v1/Books")

                .then()
                .statusCode(401)
                .body("code", Matchers.equalTo("1200"))
                .body("message", Matchers.equalTo("User not authorized!"));
    }

    @Test
    public void postBookAuthorizedTest(){
        Response authResponse = requestSpecification
                .auth().basic("testUser7","testPassw0rd!")
                .when()
                .body(getRequestBody(generateUser()))
                .post("/Account/v1/Authorized");

        authResponse.then()
                .log().all()
                .statusCode(200)
                .body(Matchers.equalTo("true"));

        if (authResponse.getBody().asString().equals("true")) {
            System.out.println("Authorization successful!");
            // Posting a book
            Response postBookResponse = requestSpecification
                    .when()
                    .body(getRequestBody(generateBook()))
                    .post("/BookStore/v1/Books");
            postBookResponse.then()
                    .log().all()
//                .statusCode(400)
                    .body("message", Matchers.equalTo("Success"));
        }else {
            System.out.println("Authorization failed!");
        }
    }

//        requestSpecification
///               .auth().basic("testUser7","testPassw0rd!")
//                .when()
//                .body(getRequestBody(generateBook()))
//                .post("/BookStore/v1/Books")
//
//                .then()
//                .statusCode(400)
//                .body("message", Matchers.equalTo("Success"));



    @Test
    public void authorizationTest(){
        requestSpecification
                .when()
                .body(getRequestBody(generateUser()))
                .post("/Account/v1/Authorized")

                .then()
                .statusCode(200)
                .body(Matchers.equalTo("true"));
    }


}
