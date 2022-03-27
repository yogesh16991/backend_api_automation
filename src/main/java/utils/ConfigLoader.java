package main.java.utils;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ConfigLoader {
    static Properties props;
    String fileLoc = System.getProperty("user.dir") + "/src/test/resources/config/endpoints.properties";

    public ConfigLoader() {
        try {
            props = new Properties();
            File f = new File(fileLoc);
            FileInputStream fis = new FileInputStream(f);
            props.load(fis);
        } catch (FileNotFoundException e) {
            System.out.println("PROPERTIES FILE NOT FOUND EXCEPTION");
        } catch (IOException e) {
            System.out.println("PROPERTIES FILE I/O EXCEPTION");
        }
    }

    public static String getPropertyData(String key) {

        String value = props.getProperty(key);
        if (value != null) {
            return value;
        } else throw new RuntimeException("No such Key: " + key);

    }


    public static ArrayList<Integer> getCommentsData(String username) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("username", username);
        Response response = BaseData.getBaseURI(queryParams).get("users");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, HttpStatus.SC_OK);

        String userResponseBody = response.asPrettyString();
        Services.validateJsonValue(userResponseBody);
        List<JSONObject> users = Services.getJsonArrayList(userResponseBody);

        queryParams.clear();
        int samanthaUserId = (int) (Double.parseDouble(users.get(0).get("id").toString()));
        queryParams.put("userId", String.valueOf(samanthaUserId));
        response = BaseData.getBaseURI(queryParams).get("posts");
        statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, HttpStatus.SC_OK);
        String PostsResponseBody = response.asPrettyString();

        List<JSONObject> posts = Services.getJsonArrayList(PostsResponseBody);
        Set<Integer> postId = Services.getIntegerData(posts, "id");
        return new ArrayList<>(postId);

    }

}
