package com.example.calfcounting;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class IllnessesInfo extends AppCompatActivity {

    TextView textViewName;
    TextView textViewSymptoms;
    TextView textViewDescription;
    TextView textViewTreatment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illnesses_info);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Информация о болезни");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        textViewName = (TextView) findViewById(R.id.textViewIllnessInfoName);
        textViewSymptoms = (TextView) findViewById(R.id.textViewIllnessInfoSymptoms);
        textViewDescription = (TextView) findViewById(R.id.textViewIllnessInfoDescription);
        textViewTreatment = (TextView) findViewById(R.id.textViewIllnessInfoTreatment);

        Intent intent = getIntent();
        textViewName.setText(intent.getStringExtra("name"));
        textViewSymptoms.setText(intent.getStringExtra("symptoms"));
        textViewDescription.setText(intent.getStringExtra("description"));
        textViewTreatment.setText(intent.getStringExtra("treatment"));

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
