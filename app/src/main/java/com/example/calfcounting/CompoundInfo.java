package com.example.calfcounting;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class CompoundInfo extends AppCompatActivity {

    TextView textViewName;
    TextView textViewSeller;
    TextView textViewPrice;
    RatingBar ratingBarRating;
    TextView textViewRating;
    Button buttonLinkToAdvert;
    TextView textViewDescription;
    TextView textViewUploadAdvertDate;
    Button buttonToPurchase;



    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compound_info);

        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setTitle("Описание комбикорма");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        textViewName = findViewById(R.id.textViewCompoundInfoName);
        textViewSeller = findViewById(R.id.textViewCompoundInfoSeller);
        textViewPrice = findViewById(R.id.textViewCompoundInfoPrice);
        ratingBarRating = findViewById(R.id.ratingBarCompoundInfoRating);
        textViewRating = findViewById(R.id.textViewCompoundInfoRating);
        buttonLinkToAdvert = findViewById(R.id.buttonCompoundInfoLinkToAdvert);
        textViewDescription = findViewById(R.id.textViewCompoundInfoDescription);
        textViewUploadAdvertDate = findViewById(R.id.textViewCompoundInfoUploadAdvertDate);
        buttonToPurchase = findViewById(R.id.buttonCompoundInfoToPurchase);


        Compound compound = getIntent().getParcelableExtra(Compound.class.getSimpleName());
        if (compound != null) {

            textViewName.setText(compound.getName());
            textViewSeller.setText(compound.getSeller());
            textViewPrice.setText(String.format("%d ₽", (int) compound.getPrice()));
            textViewRating.setText(String.valueOf(compound.getPrice()));
            ratingBarRating.setRating(compound.getRating());
            textViewRating.setText(String.valueOf(compound.getRating()));
            //todo buttonLinkToAdvert
            textViewDescription.setText(compound.getDescription());
            textViewUploadAdvertDate.setText(compound.getUpload_advert_date());
            //todo buttonToPurchase

        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.button_home:
                Intent intent = new Intent(this, MainActivity.class);
                finish();
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
