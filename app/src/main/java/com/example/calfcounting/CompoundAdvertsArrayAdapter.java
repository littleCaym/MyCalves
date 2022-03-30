package com.example.calfcounting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class CompoundAdvertsArrayAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Compound> objects;

    public CompoundAdvertsArrayAdapter(Context context, ArrayList<Compound> objects) {
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

    @SuppressLint("DefaultLocale") //??????
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.compound_adverts_listview_layout, parent, false);
        }

        TextView textViewName = view.findViewById(R.id.textViewCompoundAdvertsName);
        TextView textViewSeller = view.findViewById(R.id.textViewCompoundAdvertsSeller);
        RatingBar ratingBarRating = view.findViewById(R.id.ratingBarCompoundAdvertsRating);
        TextView textViewRating = view.findViewById(R.id.textViewCompoundAdvertsRating);
        TextView textViewReviewsNum = view.findViewById(R.id.textViewCompoundAdvertsReviewsNum);
        TextView textViewPrice = view.findViewById(R.id.textViewCompoundAdvertsPrice);

        textViewName.setText(objects.get(position).getName());
        textViewSeller.setText(objects.get(position).getSeller());
        ratingBarRating.setRating(objects.get(position).getRating());
        textViewRating.setText(String.valueOf(objects.get(position).getRating()));
        //todo textViewReviewsNum.setText(String.valueOf(objects.get(position).getReviewsNum));
        textViewPrice.setText(String.format("%d ₽", (int) objects.get(position).getPrice()));

        return view;
    }
}