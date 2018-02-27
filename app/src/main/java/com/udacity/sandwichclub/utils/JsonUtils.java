package com.udacity.sandwichclub.utils;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        if (TextUtils.isEmpty(json)) {
            return null;
        }

        String mainName;
        List<String> alsoKnownAs = new ArrayList<>();
        String placeOfOrigin;
        String description;
        String image;
        List<String> ingredients = new ArrayList<>();
        Sandwich sandwich=new Sandwich();

        try {
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(json);
            JSONObject sandwichName = baseJsonResponse.getJSONObject("name");
            mainName=sandwichName.optString("mainName");
            sandwich.setMainName(mainName);

            JSONArray jsonArrayNames=sandwichName.getJSONArray("alsoKnownAs");

            alsoKnownAs.add(jsonArrayNames.join(" "));
            sandwich.setAlsoKnownAs(alsoKnownAs);

            placeOfOrigin=baseJsonResponse.optString("placeOfOrigin");
            sandwich.setPlaceOfOrigin(placeOfOrigin);

            description=baseJsonResponse.optString("description");
            sandwich.setDescription(description);

            image=baseJsonResponse.optString("image");
            sandwich.setImage(image);

            JSONArray jsonArrayIngredients=baseJsonResponse.getJSONArray("ingredients");

            ingredients.add(jsonArrayIngredients.join(","));

            sandwich.setIngredients(ingredients);

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the sandwich JSON results", e);
        }


        return sandwich;
    }
}
