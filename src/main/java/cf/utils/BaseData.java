package main.java.cf.utils;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeSuite;

import java.util.Map;

public class BaseData {
    protected static String BASE_URI;
    protected static String USERS_ENDPOINT;
    protected static String POSTS_ENDPOINT;
    protected static String COMMENTS_ENDPOINT;

    @BeforeSuite(alwaysRun = true)
    public void loadConfig() {
        ConfigLoader config = new ConfigLoader();
        BASE_URI = config.getPropertyData("baseUri");
        USERS_ENDPOINT = config.getPropertyData("users_endpoint");
        POSTS_ENDPOINT = config.getPropertyData("posts_endpoint");
        COMMENTS_ENDPOINT = config.getPropertyData("comments_endpoint");
    }


    public static RequestSpecification getBaseURI() {
        RestAssured.baseURI = BASE_URI;
        RequestSpecification httpRequest = RestAssured.given();

        return httpRequest;
    }

    public static RequestSpecification getBaseURI(Map<String, String> queryParams) {
        RestAssured.baseURI = BASE_URI;
        RequestSpecification httpRequest = RestAssured.given().when().queryParams(queryParams);
        return httpRequest;
    }


}
