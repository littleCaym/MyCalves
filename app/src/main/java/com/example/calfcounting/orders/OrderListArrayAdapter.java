package com.example.calfcounting.orders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
                    Toast.makeText(context, "imageButtonOrderListEditSatus", Toast.LENGTH_SHORT)
                            .show();
                });
        buttonDelete.setOnClickListener(v -> {
            //TODO: сделать удаление
                boolean flag = DBHelper.deleteOrder(context, position);
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

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.imageButtonOrderListEditSatus:
//                System.out.println("imageButtonOrderListEditSatus");
//                break;
//            case R.id.buttonOrderListDelete://TODO: сделать удаление
//                System.out.println("buttonOrderListDelete");
//                break;
//            case R.id.linearLayoutOrderListListviewClickable:
////                System.out.println("linearLayoutOrderListListviewClickable");
////                Intent intent = new Intent(v.getContext(), OrderInfo.class);
////
////                intent.putExtra(Order.class.getSimpleName(), objects.get(this.position));
////                startActivity(intent);
//                break;
//        }
//    }
}