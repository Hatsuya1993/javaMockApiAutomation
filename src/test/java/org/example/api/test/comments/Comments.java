package org.example.api.test.comments;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.example.api.test.BaseTest;
import org.example.api.test.Route;
import org.testng.annotations.Test;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Comments extends BaseTest {

    @Test(description = "Should be able to get all comments")
    private void testGetComments() {

        Response res =
                CommentRequestHelper.getCommentRequestHelper(Route.postsRoute
                        , "/comments", 200);

        assertThat("Comments are empty", res.asString().length() > 0);

        JsonPath jp = res.jsonPath();
        for (int i = 0; i < jp.getInt("Comments.size()"); i++) {
            assertThat("postId is invalid", jp.getInt("postId[" + i + "]") > 0);
            assertThat("id is invalid", jp.getInt("id[" + i + "]") > 1);
            assertThat("name is invalid", jp.getString("name[" + i + "]") !=
                    "");
            assertThat("email is invalid",
                    jp.getString("email[" + i + "]") != "");
            assertThat("body is invalid", jp.getString("body[" + i + "]") !=
                    "");
        }
    }

    @Test(description = "Should be able to get all postId of 1")
    private void testGetPostIdOne() {

        Response res =
                CommentRequestHelper.getCommentRequestHelper(Route.postsRoute
                        , "/comments?postId=1", 200);

        assertThat("Comments for postid of 1 is empty",
                res.asString().length() > 0);

        JsonPath jp = res.jsonPath();
        for (int i = 0; i < jp.getInt("Comments.size()"); i++) {
            assertThat("postId is incorrect", jp.getInt("postId[" + i +
                    "]") > 0);
            assertThat("id is incorrect", jp.getInt("id[" + i + "]") > 0);
            assertThat("name is incorrect",
                    jp.getString("name[" + i + "]") != "");
            assertThat("email is incorrect",
                    jp.getString("email[" + i + "]") != "");
            assertThat("body is incorrect",
                    jp.getString("body[" + i + "]") != "");
        }

    }

    @Test(description = "Should be able to post a comment")
    private void testPostComment() {

        HashMap<String, Object> body = new HashMap<>();
        body.put("userId", 1);
        body.put("title", "title");
        body.put("body", "body");
        body.put("id", 501);

        CommentRequestHelper.postCommentRequestHelper(body,
                Route.postsRoute, "/comments", 201).
                then().
                assertThat().
                statusCode(201).
                body("userId", is(instanceOf(Integer.class)));



    }

}
