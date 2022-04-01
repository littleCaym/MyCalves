package com.example.calfcounting.orders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.calfcounting.R;

import java.util.ArrayList;

public class OrderListArrayAdapter extends BaseAdapter implements View.OnClickListener {

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
    public View getView(int position, View convertView, ViewGroup parent) {
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
        imageButtonEditStatus.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageButtonOrderListEditSatus://TODO: сделать изменение статуса
                System.out.println("imageButtonOrderListEditSatus");
                break;
            case R.id.buttonOrderListDelete://TODO: сделать удаление
                System.out.println("buttonOrderListDelete");
                break;
        }
    }
}