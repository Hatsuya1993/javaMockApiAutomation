package org.example.api.test.comments;

import io.restassured.response.Response;

import java.util.HashMap;

public class CommentRequestHelper {

    static Response getCommentRequestHelper(String baseUri, String path,
                                            Integer statusCode) {
        Response res = CommentRequest.getCommentRequest(baseUri, path);

        Integer responseStatusCode = res.statusCode();

        if (!responseStatusCode.equals(statusCode)) {
            try {
                throw new Exception("Status code expecting " + statusCode +
                        " but" +
                        " is " + responseStatusCode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return res;
    }

    static Response postCommentRequestHelper(HashMap<String, Object> body,
                                             String baseUri, String path,
                                             Integer statusCode) {

        Response res = CommentRequest.postCommentRequest(body, baseUri, path);

        Integer responseStatusCode = res.then().extract().statusCode();

        if (!responseStatusCode.equals(statusCode)) {
            try {
                throw new Exception("Status code expecting " + statusCode + " but" +
                        " is " + responseStatusCode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return res;

    }

}
