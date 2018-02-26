package com.groupl.project.pier;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by alexandra on 14/02/2018.
 */

public class CurrentMonth extends Fragment {

    private ListView mListView;
    private ImageButton goToPrevious1Month;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.current_month_fragment_layout, container, false);

        mListView = (ListView) view.findViewById(R.id.ListView);
        goToPrevious1Month = (ImageButton) view.findViewById(R.id.btnGoToPrevious1);

        DayOfTheMonthListItem item1 = new DayOfTheMonthListItem("Groceries", "Lidl", "£12", "12","FEB");
        DayOfTheMonthListItem item2 = new DayOfTheMonthListItem("Groceries", "Tesco", "£12", "12","FEB");
        DayOfTheMonthListItem item3 = new DayOfTheMonthListItem("Rent", "Unknown", "£120", "12","FEB");
        DayOfTheMonthListItem item4 = new DayOfTheMonthListItem("Transport", "Tfl", "£10","10","FEB");
        DayOfTheMonthListItem item5 = new DayOfTheMonthListItem("Groceries", "Tesco", "£10","10","FEB");
        DayOfTheMonthListItem item6 = new DayOfTheMonthListItem("Transport", "Tfl", "£9","9","FEB");
        DayOfTheMonthListItem item7 = new DayOfTheMonthListItem("Bills", "British Gas", "£9","9","FEB");
        DayOfTheMonthListItem item8 = new DayOfTheMonthListItem("Groceries", "Sainsburys", "£8","8","FEB");
        DayOfTheMonthListItem item9 = new DayOfTheMonthListItem("Transport", "Tfl", "£83","8","FEB");
        DayOfTheMonthListItem item10 = new DayOfTheMonthListItem("Groceries", "Lidl", "£54","8","FEB");
        DayOfTheMonthListItem item11 = new DayOfTheMonthListItem("Bills", "BritishGas", "£93","8","FEB");
        DayOfTheMonthListItem item12 = new DayOfTheMonthListItem("Groceries", "Lidl", "£8","8","FEB");
        DayOfTheMonthListItem item13 = new DayOfTheMonthListItem("Transport", "Tfl", "£81","5","FEB");
        DayOfTheMonthListItem item14 = new DayOfTheMonthListItem("Groceries", "Lidl", "£5","5","FEB");

        ArrayList<DayOfTheMonthListItem> MontlyList = new ArrayList<>();
        MontlyList.add(item1);
        MontlyList.add(item2);
        MontlyList.add(item3);
        MontlyList.add(item4);
        MontlyList.add(item5);
        MontlyList.add(item6);
        MontlyList.add(item7);
        MontlyList.add(item8);
        MontlyList.add(item9);
        MontlyList.add(item10);
        MontlyList.add(item11);
        MontlyList.add(item12);
        MontlyList.add(item13);
        MontlyList.add(item14);

        //getActivity() is the context for fragment, so in fragments use getActivity() insted of this
        MonthListItemAdapter adapter = new MonthListItemAdapter(getActivity(), R.layout.adapter_view_layout, MontlyList);
        mListView.setAdapter(adapter);

         goToPrevious1Month.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
             //this will give us acces to every method inside of the main activity
             ((FullStatement)getActivity()).setViewPager(1);
             }
    });



    return view;
    }
}
