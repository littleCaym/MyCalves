package com.example.calfcounting.dayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.calfcounting.R;

import java.util.ArrayList;

public class DayListArrayAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Plan> objects;


    public DayListArrayAdapter(Context context, ArrayList<Plan> plans) {
        this.context = context;
        this.objects = plans;
        layoutInflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.daylist_listview_layout, parent, false);
        }
        Plan plan = getPlan(position);

        TextView textViewName = (TextView) view.findViewById(R.id.textViewDayListListView_name);
        TextView textViewFoodN = (TextView) view.findViewById(R.id.textViewDayListListView_Food);
        TextView textViewMedicines = (TextView) view.findViewById(R.id.textViewDayListListView_medicines);

        TextView textViewFood = view.findViewById(R.id.textViewFood);
        TextView textViewPortion = view.findViewById(R.id.textViewPor);
        TextView textViewFood1 = view.findViewById(R.id.textViewFood1);
        TextView textViewPortion1 = view.findViewById(R.id.textViewPor1);
        TextView textViewFood2 = view.findViewById(R.id.textViewFood2);
        TextView textViewPortion2 = view.findViewById(R.id.textViewPor2);
        TextView textViewFood3 = view.findViewById(R.id.textViewFood3);
        TextView textViewPortion3 = view.findViewById(R.id.textViewPor3);
        TextView textViewFood4 = view.findViewById(R.id.textViewFood4);
        TextView textViewPortion4 = view.findViewById(R.id.textViewPor4);
        TextView textViewFood5 = view.findViewById(R.id.textViewFood5);
        TextView textViewPortion5 = view.findViewById(R.id.textViewPor5);
        TextView textViewFood6 = view.findViewById(R.id.textViewFood6);
        TextView textViewPortion6 = view.findViewById(R.id.textViewPor6);

        TextView textViewLek1 = view.findViewById(R.id.textViewLek1);
        TextView textViewDose1 = view.findViewById(R.id.textViewDose1);
        TextView textViewLek2 = view.findViewById(R.id.textViewLek2);
        TextView textViewDose2 = view.findViewById(R.id.textViewDose2);

        // заполняем
        textViewName.setText(plan.getAnimalName());


        if (plan.getProd1_portion() != 0 && plan.getProd1() != null){
            textViewFood.setText(plan.getProd1());
            textViewPortion.setText(String.valueOf(plan.getProd1_portion()));
        }
        if (plan.getProd2_portion() != 0 && plan.getProd2() != null){
            textViewFood1.setText(plan.getProd2());
            textViewPortion1.setText(String.valueOf(plan.getProd2_portion()));
        }
        if (plan.getProd3_portion() != 0 && plan.getProd3() != null){
            textViewFood2.setText(plan.getProd3());
            textViewPortion2.setText(String.valueOf(plan.getProd3_portion()));
        }
        if (plan.getProd4_portion() != 0 && plan.getProd4() != null){
            textViewFood3.setText(plan.getProd4());
            textViewPortion3.setText(String.valueOf(plan.getProd4_portion()));
        }
        if (plan.getProd5_portion() != 0 && plan.getProd5() != null){
            textViewFood4.setText(plan.getProd5());
            textViewPortion4.setText(String.valueOf(plan.getProd5_portion()));
        }
        if (plan.getProd6_portion() != 0 && plan.getProd6() != null){
            textViewFood5.setText(plan.getProd6());
            textViewPortion5.setText(String.valueOf(plan.getProd6_portion()));
        }
        if (plan.getProd7_portion() != 0 && plan.getProd7() != null){
            textViewFood6.setText(plan.getProd7());
            textViewPortion6.setText(String.valueOf(plan.getProd7_portion()));
        }

        if (plan.getDose1() != 0 && plan.getLek1() != null){
            textViewLek1.setText(plan.getLek1());
            textViewDose1.setText(String.valueOf(plan.getDose1()));
        }
        if (plan.getDose2() != 0 && plan.getLek2() != null){
            textViewLek2.setText(plan.getLek2());
            textViewDose2.setText(String.valueOf(plan.getDose2()));
        }

        return view;
    }

    Plan getPlan(int position) {
        return ((Plan) getItem(position));
    }

}
