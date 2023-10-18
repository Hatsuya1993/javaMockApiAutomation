package org.example.api.test.albums;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AlbumRequest {

    static Response getAlbumsRequest(String baseUri, String path){
        return given().
                baseUri(baseUri).
                when().
                get(path);
    }

}
