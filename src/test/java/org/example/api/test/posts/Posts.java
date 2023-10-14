package org.example.api.test.posts;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.example.api.test.BaseTest;
import org.example.api.test.Route;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Posts extends BaseTest {

    @Test(description = "Should be able to get all post")
    private void testGetPosts() {

        Response res =
                PostsRequestHelper.getPostRequestHelper(Route.postsRoute,
                        "/posts", 200);

        assertThat("Data is empty", res.asString().length() > 0);
        JsonPath jp = res.jsonPath();
        for (int i = 0; i < jp.getInt("Posts.size()"); i++) {
            assertThat("UserId invalid", jp.getInt("userId[" + i + "]") > 0);
            assertThat("Id invalid", jp.getInt("id[" + i + "]") > 0);
            assertThat("Title invalid", jp.getString("title[" + i + "]") != "");
            assertThat("Body", jp.getString("body[" + i + "]") != "");
        }

    }

    @Test(description = "Should be able to get a single post")
    private void testGetSinglePost() {
        PostsRequestHelper.getPostRequestHelper(Route.postsRoute,
                "/posts/1", 200).
                then().
                assertThat().
                statusCode(200).
                body("userId", is(instanceOf(Integer.class)),
                        "userId", is(greaterThan(0)),
                        "id", is(instanceOf(Integer.class)),
                        "id", is(greaterThan(0)),
                        "title", is(instanceOf(String.class)),
                        "title", is(not(equalTo(""))),
                        "body", is(instanceOf(String.class)),
                        "body", is(not(equalTo("")))
                );
    }

    @Test(description = "Shoild be able to get the comments for a post")
    private void testGetSinglePostComment() {
        Response res =
                PostsRequestHelper.getPostRequestHelper(Route.postsRoute,
                        "/posts/1/comments", 200);

        assertThat("There are no comments in this post",
                res.asString().length() > 0);

        JsonPath js = res.jsonPath();
        for (int i = 0; i < js.getInt("Posts.size()"); i++) {
            assertThat("postId is incorrect",
                    js.getInt("postId[" + i + "]") > 0);
            assertThat("id is incorrect", js.getInt("id[" + i + "]") > 0);
            assertThat("name is empty", js.getString("name[" + i + "]") != "");
            assertThat("email is empty", js.getString("email[" + i + "]") !=
                    "");
            assertThat("body is empty", js.getString("body[" + i + "]") != "");
        }
    }

    @Test(description = "Should be able to get a comment of the post")
    private void testGetSinglePostCommentId() {
        Response res =
                PostsRequestHelper.getPostRequestHelper(Route.postsRoute,
                        "/comments?postId=1", 200);


        assertThat("This post has no comments", res.toString().length() > 0);
        JsonPath js = res.jsonPath();
        for (int i = 0; i < js.getInt("posts.size()"); i++) {
            assertThat("postId is incorrect",
                    js.getInt("postId[" + i + "]") > 0);
            assertThat("id is incorrect", js.getInt("id[" + i + "]") > 0);
            assertThat("name is incorrect",
                    js.getString("name[" + i + "]") != "");
            assertThat("email is incorrect",
                    js.getString("email[" + i + "]") != "");
            assertThat("body is incorrect",
                    js.getString("body[" + i + "]") != "");
        }
    }

    @Test(description = "Should be able to create a new post")
    private void testPostPosts() {

        HashMap<String, Object> body = new HashMap<String, Object>();
        body.put("title", "foo");
        body.put("body", "bar");
        body.put("userId", 1);

        PostsRequestHelper.postPostRequestHelper(body, Route.postsRoute,
                "/posts", 201).
                then().
                assertThat().
                statusCode(201).
                body("id", is(greaterThan(0)),
                        "id", is(instanceOf(Integer.class)),
                        "title", is(instanceOf(String.class)),
                        "title", is(equalTo("foo")),
                        "body", is(instanceOf(String.class)),
                        "body", is(equalTo("bar")),
                        "userId", is(instanceOf(Integer.class)),
                        "userId", is(equalTo(1)));
    }

    @Test(description = "Should be able to update a single post")
    private void testPutPostOne() {

        HashMap<String, Object> body = new HashMap<>();
        body.put("id", 1);
        body.put("title", "foo");
        body.put("body", "bar");
        body.put("userId", 1);

        given().
                body(body).
                baseUri(Route.postsRoute).
                header("Content-type", "application/json; charset=UTF-8").
                when().
                put("/posts/1").
                then().
                assertThat().
                statusCode(200).
                body("id", is(instanceOf(Integer.class)), "id",
                        is(equalTo(1)), "title", is(instanceOf(String.class))
                        , "title", is(equalTo("foo")), "body",
                        is(instanceOf(String.class)), "body", is(equalTo("bar"
                        )), "userId",
                        is(instanceOf(Integer.class)), "userId",
                        is((equalTo(1))));
    }

    @Test(description = "Should be able to update a single post")
    private void testPatchPostOne() {

        HashMap<String, Object> body = new HashMap<>();
        body.put("id", 1);

        given().
                body(body).
                baseUri(Route.postsRoute).
                header("Content-type", "application/json; charset=UTF-8").
                when().
                patch("/posts/1").
                then().
                assertThat().
                statusCode(200).
                body("userId", is(instanceOf(Integer.class)), "userId",
                        is(not(equalTo(""))), "id",
                        is(instanceOf(Integer.class)), "id",
                        is(not(equalTo(Integer.class))), "title",
                        is(instanceOf(String.class)), "title",
                        is(not(equalTo(""))), "body",
                        is(instanceOf(String.class)), "body", is(not(equalTo(
                                ""))));

    }

    @Test(description = "Should be able to delete a single post request")
    private void testDeletePostOne() {

        given().
                baseUri(Route.postsRoute).
                when().
                delete("/posts/1").
                then().
                assertThat().
                statusCode(200);

    }


}
