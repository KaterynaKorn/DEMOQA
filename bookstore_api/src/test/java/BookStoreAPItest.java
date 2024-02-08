import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.example.BookModel;
import org.example.User;
import org.hamcrest.Matchers;
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
        return new User("testUser6", "testPassw0rd!", "17f889fa-ebb0-4c38-9ea9-74e475496644" );
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
        String authToken = getAuthToken();
        System.out.println("token" + authToken);

        requestSpecification
                .when()
//                .header("accept", "application/json")
                .header("authorization", "Basic dGVzdFVzZXI3OnRlc3RQYXNzdzByZCE=")
                .header("Authorization", "Bearer " + authToken)
                .body(getRequestBody(generateBook()))
                .post("/BookStore/v1/Books")

                .then()
                .statusCode(200)
                .body("message", Matchers.equalTo("Success"))
                .extract()
                .response();
    }

    public static String getAuthToken(){

        RestAssured.baseURI = "https://demoqa.com";
        String token = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("accept", "application/json")
                .header("authorization", "Basic dGVzdFVzZXI3OnRlc3RQYXNzdzByZCE=")
//                .header("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyTmFtZSI6InRlc3RVc2VyNiIsInBhc3N3b3JkIjoidGVzdFBhc3N3MHJkISIsImlhdCI6MTcwNzQwNTQ4Mn0.GzauSH8kmHSh_dNrd316qfJNvyY_9H8nrMDbEWLsF6s")
                .body("{\"userName\": \"testUser7\", \"password\": \"testPassw0rd!\"}")
                .post("/Account/v1/GenerateToken")

                .then()
                .statusCode(200)
                .extract()
                .path("token");
        return token;
    }
}
