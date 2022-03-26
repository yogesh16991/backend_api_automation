package test.java.cf.tests;

import main.java.cf.utils.BaseData;
import main.java.cf.utils.Services;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PostsTest extends BaseData {
    final static String postsJsonSchema = System.getProperty("user.dir") + "/src/test/resources/jsonSchemas/posts.json";


    @Test(groups = {"Smoke"}, enabled = true)
    public void verifyPostsAPITest() {

        Response response = getBaseURI().get(POSTS_ENDPOINT);
        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asPrettyString();
//    Basic Assertion for complete response
        Assert.assertEquals(statusCode, HttpStatus.SC_OK);
        Assert.assertTrue(Services.validateJsonSchema(response, postsJsonSchema), "Schema Validation failed");
        List<JSONObject> users = Services.getJsonArrayList(responseBody);
        Assert.assertTrue(users.size() > 0, "Response Empty");

    }

    //Negative
    @Test(groups = {"Smoke"}, enabled = true)
    public void verifyInValidPostsAPITest() {

        Map<String, String> queryParams = new HashMap<>();

        queryParams.put("id", "1289");
        Response response = getBaseURI(queryParams).get(POSTS_ENDPOINT);

        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asPrettyString();

        Assert.assertEquals(statusCode, HttpStatus.SC_OK);
        Assert.assertTrue(Services.validateJsonSchema(response, postsJsonSchema), "Schema Validation failed");
        List<JSONObject> users = Services.getJsonArrayList(responseBody);

//        Validate Response should be empty
        Assert.assertTrue(users.size() == 0, "Response should be Empty, PostId " + queryParams.get("id") + " present");

    }


    //Negative
    @Test(groups = {"Smoke"}, enabled = true)
    public void verifyInvalidUserWithValidPostIdPostsAPITest() {

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("id", "10");
        queryParams.put("userId", "11");
        Response response = getBaseURI(queryParams).get(POSTS_ENDPOINT);

        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asPrettyString();

        Assert.assertEquals(statusCode, HttpStatus.SC_OK);
        Assert.assertTrue(Services.validateJsonSchema(response, postsJsonSchema), "Schema Validation failed");
        List<JSONObject> users = Services.getJsonArrayList(responseBody);

//        Validate Response should be empty
        Assert.assertTrue(users.size() == 0, "Response should be Empty, PostId " + queryParams.get("id") + " present");

    }

    @Test(groups = {"Smoke"}, enabled = true)
    public void verifyAllUserAreValidInPostsAPITest() {
        Response postResponse = getBaseURI().get(POSTS_ENDPOINT);
        Response usersResponse = getBaseURI().get(USERS_ENDPOINT);

        int postStatusCode = postResponse.getStatusCode();
        int userStatusCode = usersResponse.getStatusCode();

        String postsResponseBody = postResponse.getBody().asPrettyString();
        String usersResponseBody = usersResponse.getBody().asPrettyString();

        Assert.assertEquals(postStatusCode, HttpStatus.SC_OK);
        Assert.assertEquals(userStatusCode, HttpStatus.SC_OK);

        List<JSONObject> posts = Services.getJsonArrayList(postsResponseBody);
        List<JSONObject> users = Services.getJsonArrayList(usersResponseBody);

        Set<Integer> userIdInPosts = Services.getIntegerData(posts, "userId");
        Set<Integer> userIdInUsers = Services.getIntegerData(users, "id");

//    Validate User ids in Posts API and User ids in User API are matching
        Assert.assertEquals(userIdInPosts, userIdInUsers, "User id in Users and user ids in Posts does not match");

    }
}
