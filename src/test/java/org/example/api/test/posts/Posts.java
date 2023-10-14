package org.example.api.test.posts;

import groovy.json.JsonOutput;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.example.api.test.BaseTest;
import org.example.pojo.Post;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;

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
                baseUri("https://jsonplaceholder.typicode.com/").
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

}
