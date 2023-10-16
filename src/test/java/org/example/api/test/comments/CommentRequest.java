package org.example.api.test.comments;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class CommentRequest {

    static Response getCommentRequest(String baseUri, String path){
        return given().
                baseUri(baseUri).
                when().
                get(path);
    }

}
