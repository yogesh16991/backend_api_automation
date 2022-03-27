package test.java.tests;

import main.java.utils.BaseData;
import main.java.utils.Services;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserTest extends BaseData {
    final static String userJsonSchema = System.getProperty("user.dir") + "/src/test/resources/jsonSchemas/users.json";

    @Test(groups = {"Smoke"})
    public void verifyUserAPITest() {

        Response response = getBaseURI().get(USERS_ENDPOINT);
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, HttpStatus.SC_OK);
        String responseBody = response.getBody().asPrettyString();
        Assert.assertTrue(Services.validateJsonSchema(response, userJsonSchema), "Schema Validation failed");
        List<JSONObject> users = Services.getJsonArrayList(responseBody);
        Assert.assertTrue(users.size() > 0, "Response Empty");
        // Check if all usernames are unique
        Set<String> usernames = Services.getTextData(users, "username");
        Assert.assertEquals(usernames.size(), users.size(), "Duplicate Username present");

    }

    @Test(groups = {"Smoke"})
    public void verifyValidUserTest() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("username", "Samantha");
        Response response = getBaseURI(queryParams).get(USERS_ENDPOINT);
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, HttpStatus.SC_OK);
        String responseBody = response.getBody().asPrettyString();
        Assert.assertTrue(Services.validateJsonSchema(response, userJsonSchema), "Schema Validation failed");
        List<JSONObject> users = Services.getJsonArrayList(responseBody);
        Assert.assertTrue(users.size() > 0, "Response should be Empty, Username " + queryParams.get("username") + " present");

    }

    @Test(groups = {"Smoke"})
    public void verifyInvalidUserTest() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("username", "Samanth");
        Response response = getBaseURI(queryParams).get(USERS_ENDPOINT);
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, HttpStatus.SC_OK);
        String responseBody = response.getBody().asPrettyString();
        Assert.assertTrue(Services.validateJsonSchema(response, userJsonSchema), "Schema Validation failed");
        List<JSONObject> users = Services.getJsonArrayList(responseBody);
        Assert.assertTrue(users.size() == 0, "Response should be Empty, Username " + queryParams.get("username") + " present");

    }


}
