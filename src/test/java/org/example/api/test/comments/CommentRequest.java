package org.example.api.test.comments;

import io.restassured.response.Response;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class CommentRequest {

    static Response getCommentRequest(String baseUri, String path) {
        return given().
                baseUri(baseUri).
                when().
                get(path);
    }

    static Response postCommentRequest(HashMap<String, Object> body,
                                       String baseUri, String path) {
        return given().
                body(body).
                baseUri(baseUri).
                header("Content-type", "application/json; charset=UTF-8").
                when().
                post(path);
    }

}
