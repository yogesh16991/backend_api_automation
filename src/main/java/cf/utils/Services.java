package main.java.cf.utils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.apache.commons.validator.routines.EmailValidator;
import org.json.simple.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Services {

    public static boolean validateEmail(String email) {

        boolean valid = EmailValidator.getInstance().isValid(email);
        return valid;
    }


    public static boolean validateJsonSchema(Response response, String postsJsonSchema) {
        boolean flag = false;
        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File(postsJsonSchema)));
            flag = true;
        } catch (Exception e) {
            e.getMessage();
        }
        return flag;

    }

    public static void validateJsonValue(String responseBody) {

    }

    public static List<JSONObject> getJsonArrayList(String responseBody) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<JSONObject>>() {
        }.getType();
        List<JSONObject> jsonList = gson.fromJson(responseBody, listType);
        return jsonList;
    }

    public static Set<String> getTextData(List<JSONObject> data, String key) {
        Set<String> uniqueUsernames = new HashSet<>();
        for (int i = 0; i < data.size(); i++) {

            uniqueUsernames.add(data.get(i).get(key).toString());
        }
        return uniqueUsernames;
    }

    public static Set<Integer> getIntegerData(List<JSONObject> data, String key) {
        Set<Integer> intDataSet = new TreeSet<>();

        for (int i = 0; i < data.size(); i++) {
            intDataSet.add((int) Double.parseDouble(data.get(i).get(key).toString()));

        }
        return intDataSet;
    }
}
