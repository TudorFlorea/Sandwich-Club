package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        JSONObject sandwichJson;
        JSONObject name;
        String mainName;
        JSONArray alsoKnownAsArray;
        List<String> alsoKnownAs;
        String placeOfOrigin;
        String description;
        String image;
        JSONArray ingredientsArray;
        List<String> ingredients;

        try {
            sandwichJson = new JSONObject(json);
        } catch (JSONException jse) {
            jse.printStackTrace();
            return null;
        }

        try {
            name = sandwichJson.getJSONObject("name");
            mainName = name.getString("mainName");
            alsoKnownAsArray = name.getJSONArray("alsoKnownAs");
            alsoKnownAs = jsonArrayToList(alsoKnownAsArray);
            placeOfOrigin = sandwichJson.getString("placeOfOrigin");
            description = sandwichJson.getString("description");
            image = sandwichJson.getString("image");
            ingredientsArray = sandwichJson.getJSONArray("ingredients");
            ingredients = jsonArrayToList(ingredientsArray);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }

    private static List<String> jsonArrayToList (JSONArray jsonArray) throws JSONException {
        ArrayList<String> arrayList = new ArrayList<String>();
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                arrayList.add(jsonArray.getString(i));
            }
            return arrayList;
        } else {
            return null;
        }
    }

}
