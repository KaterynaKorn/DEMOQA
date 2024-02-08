package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Data
public class BookStoreAPI{
    private ObjectMapper objectMapper;
//    private HttpClient httpClient;

    public BookStoreAPI() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        httpClient = HttpClient.newBuilder().build();
    }

//    public TokenModel getUserToken(User user) throws InterruptedException, IOException {
//        HttpRequest request = HttpRequest.newBuilder()
//                .POST(HttpRequest.BodyPublishers.ofString(getRequestBody(user)))
//                .uri(URI.create("https://demoqa.com/Account/v1/GenerateToken"))
//                .header("Content-Type", "application/json" )
//                .build();
//
//        HttpResponse<String> response = httpClient.send(request,HttpResponse.BodyHandlers.ofString());
//        TokenModel responseModel = objectMapper.readValue(response.body(), TokenModel.class);
//        return objectMapper.readValue(response.body(), TokenModel.class);
//    }
//
//    private String getRequestBody(Object o) {
//        try {
//            return objectMapper.writeValueAsString(o);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
