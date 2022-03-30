package com.example.calfcounting.rations;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calfcounting.MainActivity;
import com.example.calfcounting.R;

public class RationInfo extends AppCompatActivity {

    TextView textViewP;
    TextView textViewName;
    TextView textViewValue;
    TextView textViewName1;
    TextView textViewValue1;
    TextView textViewName2;
    TextView textViewValue2;
    TextView textViewName3;
    TextView textViewValue3;
    TextView textViewName4;
    TextView textViewValue4;
    TextView textViewName5;
    TextView textViewValue5;
    TextView textViewName6;
    TextView textViewValue6;
    //TableLayout tableLayout;

    private final int ROWS = 8;
    private final int COLUMNS = 2;
    private String coloumns;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ration_info);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Информация о рационе");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        textViewP = findViewById(R.id.textViewRationInfoPeriod);
        textViewName = findViewById(R.id.textViewRationInfoName);
        textViewValue = findViewById(R.id.textViewRationInfoValue);
        textViewName1 = findViewById(R.id.textViewRationInfoName1);
        textViewValue1 = findViewById(R.id.textViewRationInfoValue1);
        textViewName2 = findViewById(R.id.textViewRationInfoName2);
        textViewValue2 = findViewById(R.id.textViewRationInfoValue2);
        textViewName3 = findViewById(R.id.textViewRationInfoName3);
        textViewValue3 = findViewById(R.id.textViewRationInfoValue3);
        textViewName4 = findViewById(R.id.textViewRationInfoName4);
        textViewValue4 = findViewById(R.id.textViewRationInfoValue4);
        textViewName5 = findViewById(R.id.textViewRationInfoName5);
        textViewValue5 = findViewById(R.id.textViewRationInfoValue5);
        textViewName6 = findViewById(R.id.textViewRationInfoName6);
        textViewValue6 = findViewById(R.id.textViewRationInfoValue6);

        Intent intent = getIntent();
        textViewP.setText("День "+ intent.getStringExtra("continue"));

        if(intent.getIntExtra("prod1_portion",0) != 0)    {
            textViewName.setText(intent.getStringExtra("prod1"));
            textViewValue.setText(String.valueOf(intent.getIntExtra("prod1_portion",0)));
        }
        if(intent.getIntExtra("prod2_portion",0) != 0){
            textViewName1.setText(intent.getStringExtra("prod2"));
            textViewValue1.setText(String.valueOf(intent.getIntExtra("prod2_portion",0)));
        }
        if(intent.getIntExtra("prod3_portion",0) != 0){
            textViewName2.setText(intent.getStringExtra("prod3"));
            textViewValue2.setText(String.valueOf(intent.getIntExtra("prod3_portion",0)));
        }
        if(intent.getIntExtra("prod4_portion",0) != 0){
            textViewName3.setText(intent.getStringExtra("prod4"));
            textViewValue3.setText(String.valueOf(intent.getIntExtra("prod4_portion",0)));
        }
        if(intent.getIntExtra("prod5_portion",0) != 0){
            textViewName4.setText(intent.getStringExtra("prod5"));
            textViewValue4.setText(String.valueOf(intent.getIntExtra("prod5_portion",0)));
        }
        if(intent.getIntExtra("prod6_portion",0) != 0){
            textViewName5.setText(intent.getStringExtra("prod6"));
            textViewValue5.setText(String.valueOf(intent.getIntExtra("prod6_portion",0)));
        }
        if(intent.getIntExtra("prod7_portion",0) != 0 ){
            textViewName6.setText(intent.getStringExtra("prod7"));
            textViewValue6.setText(String.valueOf(intent.getIntExtra("prod7_portion",0)));
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








/*
                int i = 0;
                TableRow tableRow = new TableRow(this);
                tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                int j = 0;
                TextView textView1 = new TextView(this);
                textView1.setText(intent.getStringExtra("prod1"));
                tableRow.addView(textView1, j++);
                TextView textView2 = new TextView(this);
                textView2.setText(String.valueOf(intent.getIntExtra("prod1_portion",0)));
                tableRow.addView(textView2, j++);
                tableLayout.addView(tableRow, i++);

        i = 0;
        TableRow tableRow2 = new TableRow(this);
        tableRow2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        j = 0;
        TextView textView3 = new TextView(this);
        textView1.setText(intent.getStringExtra("prod2"));
        tableRow2.addView(textView3, j++);
        TextView textView4 = new TextView(this);
        textView4.setText(String.valueOf(intent.getIntExtra("prod2_portion",0)));
        tableRow.addView(textView4, j++);
        tableLayout.addView(tableRow2, i);
*/