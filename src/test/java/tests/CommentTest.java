package test.java.tests;

import main.java.dataProvider.DataInputSet;
import main.java.utils.BaseData;
import main.java.utils.Services;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CommentTest extends BaseData {
    final static String commentsJsonSchema = System.getProperty("user.dir") + "/src/test/resources/jsonSchemas/comments.json";

    @Test(groups = {"Smoke"}, enabled = true)
    public void verifyCommentAPITest() {

        Response response = getBaseURI().get(COMMENTS_ENDPOINT);
        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asPrettyString();

//    Basic Assertion for complete response
        Assert.assertEquals(statusCode, HttpStatus.SC_OK);
        Assert.assertTrue(Services.validateJsonSchema(response, commentsJsonSchema), "Schema Validation failed");
        List<JSONObject> comments = Services.getJsonArrayList(responseBody);
        Assert.assertTrue(comments.size() > 0, "Response Empty");


    }

    //  Negative
    @Test(groups = {"Smoke"}, enabled = true)
    public void verifyInvalidIdCommentAPITest() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("id", "501");
        Response response = getBaseURI(queryParams).get(COMMENTS_ENDPOINT);
        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asPrettyString();

        Assert.assertEquals(statusCode, HttpStatus.SC_OK);
        Assert.assertTrue(Services.validateJsonSchema(response, commentsJsonSchema), "Schema Validation failed");
        List<JSONObject> comments = Services.getJsonArrayList(responseBody);
        Assert.assertTrue(comments.size() == 0, "Response should be Empty, PostId " + queryParams.get("id") + " present");

    }

    // Negative
    @Test(groups = {"Smoke"}, enabled = true)
    public void verifyValidCommentIdWithInvalidPostIdAPITest() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("id", "246");
        queryParams.put("postId", "101");
        Response response = getBaseURI(queryParams).get(COMMENTS_ENDPOINT);
        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asPrettyString();

        Assert.assertEquals(statusCode, HttpStatus.SC_OK);
        Assert.assertTrue(Services.validateJsonSchema(response, commentsJsonSchema), "Schema Validation failed");
        List<JSONObject> comments = Services.getJsonArrayList(responseBody);
        Assert.assertTrue(comments.size() == 0, "Response should be Empty, PostId " + queryParams.get("id") + " present");


    }


    @Test(groups = {"Smoke"}, enabled = true)
    public void verifyAllPostsAgainstCommentsAPITest() {
        Response postResponse = getBaseURI().get(POSTS_ENDPOINT);
        Response commentsResponse = getBaseURI().get(COMMENTS_ENDPOINT);

        int postStatusCode = postResponse.getStatusCode();
        int commentsStatusCode = commentsResponse.getStatusCode();

        String postsResponseBody = postResponse.getBody().asPrettyString();
        String commentsResponseBody = commentsResponse.getBody().asPrettyString();

        Assert.assertEquals(postStatusCode, HttpStatus.SC_OK);
        Assert.assertEquals(commentsStatusCode, HttpStatus.SC_OK);

        List<JSONObject> posts = Services.getJsonArrayList(postsResponseBody);
        List<JSONObject> comments = Services.getJsonArrayList(commentsResponseBody);

        Set<Integer> postIdInPosts = Services.getIntegerData(posts, "id");
        Set<Integer> postIdInComments = Services.getIntegerData(comments, "postId");

//    Validate Post ids in Posts API and Post ids in Comments API are matching
        Assert.assertEquals(postIdInPosts, postIdInComments, "Posts id in Post and Post ids in Comments does not match");

    }

    @Test(groups = {"Regression"}, enabled = true, dataProvider = "Comments_GetPostIds", dataProviderClass = DataInputSet.class)
    public void verifyEmailIdsInCommentsAPITest(int postId) {
        Map<String, String> queryParams = new HashMap<>();
        SoftAssert softAssert = new SoftAssert();

        queryParams.put("postId", String.valueOf(postId));
        Response response = getBaseURI(queryParams).get(COMMENTS_ENDPOINT);
        String commentsResponseBody = response.asPrettyString();
        int statusCode = response.getStatusCode();

        Assert.assertEquals(statusCode, HttpStatus.SC_OK, "Failed for Comments");
        List<JSONObject> comments = Services.getJsonArrayList(commentsResponseBody);
        for (int j = 0; j < comments.size(); j++) {

            String email = comments.get(j).get("email").toString();
            boolean isValidEmail = Services.validateEmail(email);
            softAssert.assertTrue(isValidEmail, email + ": is Invalid for post id: " + comments.get(j).get("name"));

        }

        softAssert.assertAll();
    }

}
