package com.example.calfcounting.orders;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calfcounting.DBHelper;
import com.example.calfcounting.R;

import java.text.SimpleDateFormat;

public class OrderInfo extends AppCompatActivity implements View.OnClickListener {

    Order order;
    TextView textViewStatus;

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Информация о заказе");

        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView textViewName = findViewById(R.id.textViewOrderInfoName);
        TextView textViewSeller = findViewById(R.id.textViewOrderInfoSeller);
        RatingBar ratingBarRating = findViewById(R.id.ratingBarOrderInfoRating);
        TextView textViewRating = findViewById(R.id.textViewOrderInfoRating);
        TextView textViewReviewsNum = findViewById(R.id.textViewOrderInfoReviewsNum);
        TextView textViewPrice = findViewById(R.id.textViewOrderInfoPrice);
        Button buttonLinkToAdvert = findViewById(R.id.buttonOrderInfoLinkToAdvert);
        EditText editTextDate = findViewById(R.id.editTextOrderInfoDate);
        TextView textViewDescription = findViewById(R.id.textViewOrderInfoDescription);
        textViewStatus = findViewById(R.id.textViewOrderInfoStatus);
        ImageButton imageButtonEditStatus = findViewById(R.id.imageButtonOrderInfoEditSatus);
        Button buttonDelete = findViewById(R.id.buttonOrderInfoDelete);
        TextView textViewDateAdded = findViewById(R.id.textViewOrderInfoDateAdded);

        order = getIntent().getParcelableExtra(Order.class.getSimpleName());
        if (order != null){
            textViewName.setText(order.getName());
            textViewSeller.setText(order.getSeller());
            ratingBarRating.setRating(order.getRating());
            textViewRating.setText(String.valueOf(order.getRating()));
            textViewReviewsNum.setText(String.valueOf(order.getReviews_num()));
            textViewPrice.setText(String.format("%d ₽", (int) order.getPrice()));
            buttonLinkToAdvert.setOnClickListener(this);
            editTextDate.setText(
                    new SimpleDateFormat("dd-MM-yyyy")
                            .format(order.getDate_of_arrival())
            );
            textViewDescription.setText(order.getDescription());

            setTextViewStatus(order.getStatus());

            imageButtonEditStatus.setOnClickListener(this);
            buttonDelete.setOnClickListener(this);

//            Calendar calendarDateAdded = Calendar.getInstance();
//            calendarDateAdded.setTime(order.getDate_added());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            textViewDateAdded.setText("Добавлено в заказы: \n"
                    + dateFormat.format(order.getDate_added())
            );
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonOrderInfoLinkToAdvert:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(
                        Uri.parse(
                                order.getLink_to_advert()));
                startActivity(intent);
                break;
            case R.id.imageButtonOrderInfoEditSatus:
                showCustomDialog();
                break;
            case R.id.buttonOrderInfoDelete:
                boolean flag = DBHelper.deleteOrderById(this, order.getId());
                if (flag){
                    Toast.makeText(this, "Заказ удален", Toast.LENGTH_SHORT)
                            .show();
                    Intent intent1 = new Intent(this, OrderList.class);
                    startActivity(intent1);
                }
                break;
        }
    }

    @SuppressLint("NonConstantResourceId")
    private void showCustomDialog(){

        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.order_list_dialog);

        //0 - добавлен в список 1 - в пути 2 - прибыл 3 - отложен 4 - отменен
        final RadioButton radioButton0 = dialog.findViewById(R.id.radioButtonOrderListDialog0);
        final RadioButton radioButton1 = dialog.findViewById(R.id.radioButtonOrderListDialog1);
        final RadioButton radioButton2 = dialog.findViewById(R.id.radioButtonOrderListDialog2);
        final RadioButton radioButton3 = dialog.findViewById(R.id.radioButtonOrderListDialog3);
        final RadioButton radioButton4 = dialog.findViewById(R.id.radioButtonOrderListDialog4);

        final RadioGroup radioGroup = dialog.findViewById(R.id.radioGroupOrderListDialog);

        switch (order.getStatus()){
            case 0: radioButton0.setChecked(true); break;
            case 1: radioButton1.setChecked(true); break;
            case 2: radioButton2.setChecked(true); break;
            case 3: radioButton3.setChecked(true); break;
            case 4: radioButton4.setChecked(true); break;
        }

        final Button buttonAccept = dialog.findViewById(R.id.buttonOrderListDialogAccept);
        final Button buttonCancel = dialog.findViewById(R.id.buttonOrderListDialogCancel);

        buttonAccept.setOnClickListener(v -> {
            int status = 0;

            switch (radioGroup.getCheckedRadioButtonId()){
                case R.id.radioButtonOrderListDialog0: status = 0; break;
                case R.id.radioButtonOrderListDialog1: status = 1; break;
                case R.id.radioButtonOrderListDialog2: status = 2; break;
                case R.id.radioButtonOrderListDialog3: status = 3; break;
                case R.id.radioButtonOrderListDialog4: status = 4; break;
            }
            boolean flag = DBHelper.changeOrderStatusById(this,
                    status,
                    order.getId());
            if (flag) {
//                order.setStatus(radioGroup.getCheckedRadioButtonId());
                Toast.makeText(this, "Статус обновлен", Toast.LENGTH_SHORT)
                        .show();
                //обновлеям информацию на странице
                setTextViewStatus(status);
            }
            dialog.dismiss();

        });
        buttonCancel.setOnClickListener(v -> dialog.cancel());

        dialog.show();
    }

    private void setTextViewStatus(int status){
        switch (status){
            case 0:
                textViewStatus.setText("Добавлен в список");
                textViewStatus.setTextColor(Color.parseColor("#9b870c"));
                break;
            case 1:
                textViewStatus.setText("В пути");
                textViewStatus.setTextColor(Color.parseColor("#9b870c"));
                break;
            case 2:
                textViewStatus.setText("Прибыл");
                textViewStatus.setTextColor(Color.parseColor("#00FF00"));
                break;
            case 3:
                textViewStatus.setText("Отложен");
                textViewStatus.setTextColor(Color.parseColor("#9b870c"));

                break;
            case 4:
                textViewStatus.setText("Отменен");
                textViewStatus.setTextColor(Color.parseColor("#FF0000"));
        }
    }
}