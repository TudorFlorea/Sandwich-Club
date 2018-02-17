package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView mOriginTextView;
    private TextView mLabelOriginTextView;
    private TextView mAlsoKnownTextView;
    private TextView mLabelAlsoKnownTextView;
    private TextView mDescriptionTextView;
    private TextView mLabelDescriptionTextView;
    private TextView mIngredientsTextView;
    private TextView mLabelIngredientsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mOriginTextView = findViewById(R.id.origin_tv);
        mLabelOriginTextView = findViewById(R.id.label_origin_tv);
        mAlsoKnownTextView = findViewById(R.id.also_known_tv);
        mLabelAlsoKnownTextView = findViewById(R.id.label_also_known_tv);
        mDescriptionTextView = findViewById(R.id.description_tv);
        mLabelDescriptionTextView = findViewById(R.id.label_description_tv);
        mIngredientsTextView = findViewById(R.id.ingredients_tv);
        mLabelIngredientsTextView = findViewById(R.id.label_ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     *@param sandwich -a Sandwich object
     *Checks if any of the properties of the Sandwich are null or empty and if so it hides the textviews responsable
     *for displaying that data otherwise it populates the textviews with the data from the Sandwich object
     */

    private void populateUI(Sandwich sandwich) {

        if (sandwich.getPlaceOfOrigin() == null || sandwich.getPlaceOfOrigin().equals("")) {
            mLabelOriginTextView.setVisibility(View.GONE);
            mOriginTextView.setVisibility(View.GONE);
        } else {
            mOriginTextView.setText(sandwich.getPlaceOfOrigin());
        }

        if (sandwich.getAlsoKnownAs() == null || sandwich.getAlsoKnownAs().size() == 0) {
            mLabelAlsoKnownTextView.setVisibility(View.GONE);
            mAlsoKnownTextView.setVisibility(View.GONE);
        } else {
            mAlsoKnownTextView.setText(setupAlsoKnownAsText(sandwich.getAlsoKnownAs()));
        }

        if (sandwich.getDescription() == null || sandwich.getDescription().equals("")) {
            mLabelDescriptionTextView.setVisibility(View.GONE);
            mDescriptionTextView.setVisibility(View.GONE);
        } else {
            mDescriptionTextView.setText(sandwich.getDescription());
        }

        if (sandwich.getIngredients() == null || sandwich.getIngredients().size() == 0) {
            mLabelIngredientsTextView.setVisibility(View.GONE);
            mIngredientsTextView.setVisibility(View.GONE);
        } else {
            mIngredientsTextView.setText(setupIngredientsText(sandwich.getIngredients()));
        }


    }

    /**
     *@param ingredients - a list of sandwich ingredients
     *Method that takes a list of ingredients as parameter and returns a string representation of a bullet point list
     *with each ingredient on a row
     *@return string representation of a bullet point list to be used as text in a TextView
     */

    private String setupIngredientsText(List<String> ingredients) {
        StringBuilder sb = new StringBuilder();
        if (ingredients != null) {
            for (int i = 0; i < ingredients.size(); i++ ) {
                sb.append("\u25CF ");
                sb.append(ingredients.get(i));
                sb.append("\n");
            }
            return sb.toString();
        } else {
            return null;
        }
    }

    /**
     *@param names - a list of alternative names for a sandwich
     *Method that takes a list of names as parameter and returns a string with all the concatenated names separated by coma
     *@return string of concatenated names to be used as text in a TextView
     */

    private String setupAlsoKnownAsText(List<String> names) {
        StringBuilder sb = new StringBuilder();
        if (names != null) {
            for (int i = 0; i < names.size(); i++ ) {
                sb.append(names.get(i));
                sb.append(", ");
            }
            return sb.toString();
        } else {
            return null;
        }
    }
}
