package org.example.api.test.albums;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.example.api.test.Route;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class Albums {

    @Test(description = "Should be able to get all albums")
    private void testGetAlbums() {

        Response res =
                AlbumRequestHelper.getAlbumsRequestHelper(Route.postsRoute,
                        "/albums", 200);

        assertThat("Albums are empty", res.asString().length() > 0);

        JsonPath jp = res.jsonPath();

        for (int i = 0; i < jp.getInt("albums.size()"); i++) {
            assertThat("userId is invalid", jp.getInt("userId[" + i + "]") > 0);
        }

    }

}
