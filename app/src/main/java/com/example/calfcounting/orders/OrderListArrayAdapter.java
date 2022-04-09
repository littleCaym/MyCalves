package com.example.calfcounting.orders;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calfcounting.DBHelper;
import com.example.calfcounting.R;

import java.util.ArrayList;

public class OrderListArrayAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Order> objects;

    public OrderListArrayAdapter(Context context, ArrayList<Order> objects) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.order_list_listview_layout, parent, false);
        }

        TextView textViewName = view.findViewById(R.id.textViewOrderListName);
        TextView textViewSeller = view.findViewById(R.id.textViewOrderListSeller);
        RatingBar ratingBarRating = view.findViewById(R.id.ratingBarOrderListRating);
        TextView textViewRating = view.findViewById(R.id.textViewOrderListRating);
        TextView textViewReviewsNum = view.findViewById(R.id.textViewOrderListNum);
        TextView textViewPrice = view.findViewById(R.id.textViewOrderListPrice);
        TextView textViewStatus = view.findViewById(R.id.textViewOrderListStatus);
        ImageButton imageButtonEditStatus = view.findViewById(R.id.imageButtonOrderListEditSatus);
        Button buttonDelete = view.findViewById(R.id.buttonOrderListDelete);

        textViewName.setText(objects.get(position).getName());
        textViewSeller.setText(objects.get(position).getSeller());
        ratingBarRating.setRating(objects.get(position).getRating());
        textViewRating.setText(String.valueOf(objects.get(position).getRating()));
        //todo textViewReviewsNum.setText(String.valueOf(objects.get(position).getReviewsNum));
        textViewPrice.setText(String.format("%d ₽", (int) objects.get(position).getPrice()));

        textViewStatus.setText(objects.get(position).getStatusString());
        switch (objects.get(position).getStatus()){
            case 0:
            case 1:
            case 3:
                textViewStatus.setTextColor(Color.parseColor("#9b870c")); break;
            case 2:
                textViewStatus.setTextColor(Color.parseColor("#00FF00")); break;
            case 4:
                textViewStatus.setTextColor(Color.parseColor("#FF0000")); break;
        }


        LinearLayout listViewClickable = view.findViewById(R.id.linearLayoutOrderListListviewClickable);

        imageButtonEditStatus
                .setOnClickListener(v -> {
                    //TODO: сделать изменение статуса
                    showCustomDialog(position);

                    Toast.makeText(context, "Статус обновлен", Toast.LENGTH_SHORT)
                            .show();

                });
        buttonDelete.setOnClickListener(v -> {
                boolean flag = DBHelper.deleteOrderById(context, objects.get(position).getId());
                if (flag){
                    Toast.makeText(context, "Заказ удален", Toast.LENGTH_SHORT)
                            .show();
                    objects.remove(position);
                    this.notifyDataSetChanged();
                }

        });
        listViewClickable.setOnClickListener(v -> {
            Intent intent = new Intent(context, OrderInfo.class);
            intent.putExtra(Order.class.getSimpleName(), objects.get(position));
            context.startActivity(intent);
        });

        return view;
    }

    @SuppressLint("NonConstantResourceId")
    private void showCustomDialog(int position){

        Dialog dialog = new Dialog(context);
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
        /*
        radioGroup.addView(radioButton0);
        radioGroup.addView(radioButton1);
        radioGroup.addView(radioButton2);
        radioGroup.addView(radioButton3);
        radioGroup.addView(radioButton4);

         */

        switch (objects.get(position).getStatus()){
            case 0: radioButton0.setChecked(true); break;
            case 1: radioButton1.setChecked(true); break;
            case 2: radioButton2.setChecked(true); break;
            case 3: radioButton3.setChecked(true); break;
            case 4: radioButton4.setChecked(true); break;
        }

        final Button buttonAccept = dialog.findViewById(R.id.buttonOrderListDialogAccept);
        final Button buttonCancel = dialog.findViewById(R.id.buttonOrderListDialogCancel);

        buttonAccept.setOnClickListener(v -> {
            int rg_id = 0;

            switch (radioGroup.getCheckedRadioButtonId()){
                case R.id.radioButtonOrderListDialog0: rg_id = 0; break;
                case R.id.radioButtonOrderListDialog1: rg_id = 1; break;
                case R.id.radioButtonOrderListDialog2: rg_id = 2; break;
                case R.id.radioButtonOrderListDialog3: rg_id = 3; break;
                case R.id.radioButtonOrderListDialog4: rg_id = 4; break;
            }
            boolean flag = DBHelper.changeOrderStatusById(context,
                    rg_id,
                    objects.get(position).getId());
            if (flag) {
                objects.get(position).setStatus(radioGroup.getCheckedRadioButtonId());
                this.notifyDataSetChanged();
            }
            dialog.dismiss();

        });
        buttonCancel.setOnClickListener(v -> dialog.cancel());

        dialog.show();
    }
}