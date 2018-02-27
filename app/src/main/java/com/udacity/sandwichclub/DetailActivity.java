package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;


    public TextView alsoKnown;
    public TextView origin ;
    public TextView ingredients ;
    public TextView mDescription ;
    public TextView mAlsoKnownLabel ;
    public TextView mOriginLabel ;
    public TextView mIngredientsLabel ;
    public ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageView = findViewById(R.id.image_iv);

        alsoKnown = findViewById(R.id.also_known);
        origin = findViewById(R.id.origin_tv);
        ingredients = findViewById(R.id.ingredients_tv);
        mDescription = findViewById(R.id.description_tv);
        mAlsoKnownLabel = findViewById(R.id.also_known_as_label);
        mOriginLabel = findViewById(R.id.detail_place_of_origin_label);
        mIngredientsLabel = findViewById(R.id.detail_ingredients_label);


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
        //Populate sandwich object that's used by populateUI

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(imageView);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        if(sandwich.getIngredients()!=null) {
            ingredients.setText(TextUtils.join(" ", sandwich.getIngredients()));
        }else {
            ingredients.setText("no data");
        }
        mDescription.setText(sandwich.getDescription());

        //Display AlsoKnownAs details or else hide it
        if(!TextUtils.join(", ", sandwich.getAlsoKnownAs()).equals("")){
        alsoKnown.setText(TextUtils.join(", ", sandwich.getAlsoKnownAs()));
        } else {
            mAlsoKnownLabel.setVisibility(View.GONE);
            alsoKnown.setVisibility(View.GONE);
        }


        if (!sandwich.getPlaceOfOrigin().equals("")) {
            origin.setText(sandwich.getPlaceOfOrigin());
        } else {
            origin.setText("Unknown");
        }

    }
}
