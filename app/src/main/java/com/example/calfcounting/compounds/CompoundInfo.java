package com.example.calfcounting.compounds;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calfcounting.DBHelper;
import com.example.calfcounting.MainActivity;
import com.example.calfcounting.R;
import com.example.calfcounting.orders.Order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class CompoundInfo extends AppCompatActivity implements View.OnClickListener {

    TextView textViewName;
    TextView textViewSeller;
    TextView textViewPrice;
    RatingBar ratingBarRating;
    TextView textViewRating;
    Button buttonLinkToAdvert;
    TextView textViewDescription;
    TextView textViewUploadAdvertDate;
    Button buttonToPurchase;

    String avitoUrl;
    Compound compound;
    Dialog dialog;


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

        buttonLinkToAdvert.setVisibility(View.GONE);
        buttonToPurchase.setVisibility(View.GONE);

        compound = getIntent().getParcelableExtra(Compound.class.getSimpleName());
        if (compound != null) {

            textViewName.setText(compound.getName());
            textViewSeller.setText(compound.getSeller());
            textViewPrice.setText(String.format("%d ₽", (int) compound.getPrice()));
            textViewRating.setText(String.valueOf(compound.getPrice()));
            ratingBarRating.setRating(compound.getRating());
            textViewRating.setText(String.valueOf(compound.getRating()));

            avitoUrl = compound.getLink_to_advert();
            buttonLinkToAdvert.setVisibility(View.VISIBLE);
            buttonLinkToAdvert.setOnClickListener(this);

            textViewDescription.setText(compound.getDescription());
            textViewUploadAdvertDate.setText(compound.getUpload_advert_date());

            buttonToPurchase.setVisibility(View.VISIBLE);
            buttonToPurchase.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonCompoundInfoLinkToAdvert:
                String url = avitoUrl;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(avitoUrl));
                startActivity(intent);
                break;
            case R.id.buttonCompoundInfoToPurchase:
                System.out.println("buttonCompoundInfoToPurchase pressed");
                showCustomDialog();
                break;
        }
    }

    void showCustomDialog() {
        dialog = new Dialog(CompoundInfo.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.compound_info_dialog);

        final EditText editTextAmount = dialog.findViewById(R.id.editTextCompoundInfoDialogAmount);
        final EditText editTextPrice = dialog.findViewById(R.id.editTextCompoundInfoDialogPrice);
        final EditText editTextDateOfArrival = dialog.findViewById(R.id.editTextCompoundInfoDialogDateOfArrival);
        final Button buttonAdd = dialog.findViewById(R.id.buttonCompoundInfoDialogAdd);
        final Button buttonCancel = dialog.findViewById(R.id.buttonCompoundInfoDialogCancel);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addToOrders(
                            editTextAmount.getText().toString(),
                            editTextPrice.getText().toString(),
                            editTextDateOfArrival.getText().toString()
                    );
                    dialog.dismiss();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    void addToOrders(String etAmountString, String etPriceString, String etDateString) throws ParseException {



        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //не было ли уже добавлено?
        Cursor cursor = db.query("ORDERS", new String[]{Order.ID},
                Order.ID + "= ?", new String[] {Order.ID},
                null, null, null);

        //если не было
        if (!cursor.moveToFirst()){
            ContentValues cv = new ContentValues();
            cv.put(Order.ID, compound.getId());
            cv.put(Order.NAME, compound.getName());
            cv.put(Order.SELLER, compound.getSeller());
            cv.put(Order.RATING, compound.getRating());
            cv.put(Order.REVIEWS_NUM, compound.getReviews_num());
            cv.put(Order.DESCRIPTION, compound.getDescription());
            cv.put(Order.LOCATION, compound.getLocation());
            cv.put(Order.UPLOAD_ADVERT_DATE, compound.getUpload_advert_date());
            cv.put(Order.LINK_TO_ADVERT, compound.getLink_to_advert());

            cv.put(Order.PRICE, Float.parseFloat(etAmountString));
            //current time of adding:
            cv.put(Order.DATE_ADDED, String.valueOf(Calendar.getInstance().getTime()));
            cv.put(Order.AMOUNT, Float.parseFloat(etPriceString));
            //time of arrival:
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            cv.put(Order.DATE_OF_ARRIVAL, String.valueOf(dateFormat.parse(etDateString)));
            //status: добавлен
            cv.put(Order.STATUS, 0);

            db.insert("ORDERS",null, cv);

            db.close();

            Toast.makeText(
                    CompoundInfo.this, "Комбикорм добавлен в заказы!", 3000
            ).show();
        }
        //если уже было
        else{
            Toast.makeText(
                    CompoundInfo.this, "Комбикорм уже есть в заказах!", 3000)
                    .show();
        }




    }
}
