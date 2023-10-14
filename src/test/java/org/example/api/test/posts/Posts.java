package org.example.api.test.posts;

import groovy.json.JsonOutput;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.example.api.test.BaseTest;
import org.example.pojo.Post;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Posts extends BaseTest {

    @Test
    private void testGetPosts() {

        Response res = given().
                baseUri("https://jsonplaceholder.typicode.com").
                when().
                get("/posts").
                then().
                assertThat().
                statusCode(200).
                extract().
                response();

        assertThat("Data is empty", res.asString().length() > 0);
        JsonPath jp = res.jsonPath();
        for (int i = 0; i < jp.getInt("Posts.size()"); i++) {
            assertThat("UserId invalid", jp.getInt("userId[" + i + "]") > 0);
            assertThat("Id invalid", jp.getInt("id[" + i + "]") > 0);
            assertThat("Title invalid", jp.getString("title[" + i + "]") != "");
            assertThat("Body", jp.getString("body[" + i + "]") != "");
        }

    }

    @Test
    private void testGetSinglePost() {
        given().
                baseUri("https://jsonplaceholder.typicode.com").
                when().
                get("posts/1").
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

    @Test
    private void testGetSinglePostComment() {
        Response res = given().
                baseUri("https://jsonplaceholder.typicode.com").
                when().
                get("/posts/1/comments").
                then().
                assertThat().
                statusCode(200).
                extract().
                response();

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

    @Test
    private void testGetSinglePostCommentId() {
        Response res = given().
                baseUri("https://jsonplaceholder.typicode.com").
                when().
                get("/comments?postId=1").
                then().
                assertThat().
                statusCode(200).
                extract().
                response();

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

    @Test
    private void testPostPosts() {

        HashMap<String, Object> body = new HashMap<String, Object>();
        body.put("title", "foo");
        body.put("body", "bar");
        body.put("userId", 1);

        given().
                body(body).
                baseUri("https://jsonplaceholder.typicode.com").
                header("Content-type", "application/json; charset=UTF-8").
                when().
                post("/posts").
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

    @Test
    private void testPutPostOne() {

        HashMap<String, Object> body = new HashMap<>();
        body.put("id", 1);
        body.put("title", "foo");
        body.put("body", "bar");
        body.put("userId", 1);

        given().
                body(body).
                baseUri("https://jsonplaceholder.typicode.com").
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

    @Test
    private void testPatchPostOne() {

        HashMap<String, Object> body = new HashMap<>();
        body.put("id", 1);

        given().
                body(body).
                baseUri("https://jsonplaceholder.typicode.com").
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

    @Test
    private void testDeletePostOne() {

        given().
                baseUri("https://jsonplaceholder.typicode.com").
                when().
                delete("/posts/1").
                then().
                assertThat().
                statusCode(200);

    }


}
