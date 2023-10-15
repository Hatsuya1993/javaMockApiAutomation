package org.example.api.test.posts;

import io.restassured.response.Response;
import org.example.api.test.Route;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class PostsRequestHelper {

    static Response getPostRequestHelper(String baseUri, String path,
                                         Integer statusCode) {

        Response res = PostsRequest.getPostRequest(baseUri, path);

        Integer responseStatusCode = res.then().extract().statusCode();

        if (!responseStatusCode.equals(statusCode)) {
            try {
                throw new Exception("Status code expecting " + statusCode +
                        " but is " + responseStatusCode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return res;

    }

    static Response postPostRequestHelper(HashMap<String, Object> body,
                                          String baseUri, String path,
                                          Integer statusCode) {

        Response res = PostsRequest.postPostRequest(body, baseUri, path);

        Integer responseStatusCode = res.then().extract().statusCode();

        if(!responseStatusCode.equals(statusCode)){
            try {
                throw new Exception("Status code expecting " + statusCode + " but is " + responseStatusCode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return res;

    }

    static Response putPostRequestHelper(HashMap<String, Object> body,
                                          String baseUri, String path,
                                          Integer statusCode) {

        Response res = PostsRequest.putPostRequest(body, baseUri, path);

        Integer responseStatusCode = res.then().extract().statusCode();

        if(!responseStatusCode.equals(statusCode)){
            try {
                throw new Exception("Status code expecting " + statusCode + " but is " + responseStatusCode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return res;

    }

    static Response patchPostRequestHelper(HashMap<String, Object> body,
                                         String baseUri, String path,
                                         Integer statusCode) {

        Response res = PostsRequest.patchPostRequest(body, baseUri, path);

        Integer responseStatusCode = res.then().extract().statusCode();

        if(!responseStatusCode.equals(statusCode)){
            try {
                throw new Exception("Status code expecting " + statusCode + " but is " + responseStatusCode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return res;

    }

    static Response deletePostRequestHelper(String baseUri, String path,
                                           Integer statusCode) {

        Response res = PostsRequest.deletePostRequest(baseUri, path);

        Integer responseStatusCode = res.then().extract().statusCode();

        if(!responseStatusCode.equals(statusCode)){
            try {
                throw new Exception("Status code expecting " + statusCode + " but is " + responseStatusCode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return res;

    }

}
