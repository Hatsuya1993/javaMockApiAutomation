package org.example.api.test.comments;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.example.api.test.BaseTest;
import org.example.api.test.Route;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class Comments extends BaseTest {

    @Test(description = "Should be able to get all comments")
    private void testGetComments() {

        Response res =
                CommentRequestHelper.getCommentRequestHelper(Route.postsRoute
                        , "/comments", 200);

        assertThat("Comments are empty", res.asString().length() > 0);

        JsonPath jp = res.jsonPath();
        for (int i = 0; i < jp.getInt("Comments.size()"); i++) {
            assertThat("PostId is invalid", jp.getInt("postId[" + i + "]") > 0);
        }
    }

}
