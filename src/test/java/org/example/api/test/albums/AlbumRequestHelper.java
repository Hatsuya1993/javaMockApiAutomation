package org.example.api.test.albums;

import io.restassured.response.Response;

public class AlbumRequestHelper {

    static Response getAlbumsRequestHelper(String baseUri, String path,
                                           Integer statusCode) {
        Response res = AlbumRequest.getAlbumsRequest(baseUri, path);

        Integer responseStatusCode = res.statusCode();

        if (!responseStatusCode.equals(statusCode)) {
            try {
                throw new Exception("Status code expecting " + statusCode + " but" +
                        " got " + responseStatusCode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return res;
    }

}
