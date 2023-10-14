package org.example.api.test.posts;

import io.restassured.response.Response;
import org.example.api.test.Route;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class PostsRequest {

    static Response getPostRequest(String baseUri, String path) {
        return given().
                baseUri(baseUri).
                when().
                get(path);
    }

    static Response postPostRequest(HashMap<String, Object> body,
                                    String baseUri, String path) {
        return given().
                body(body).
                baseUri(baseUri).
                header("Content-type", "application/json; charset=UTF-8").
                when().
                post(path);
    }

}
