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

public class Previous1Month extends Fragment {

    private ListView mListView;
    private ImageButton goToCurrentMonth;
    private ImageButton goToPrevious2Month;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.previous1_month_fragment_layout, container, false);

        mListView = (ListView)view.findViewById(R.id.ListView);
        goToCurrentMonth = (ImageButton) view.findViewById(R.id.btnGoToCurrentMonth);
        goToPrevious2Month = (ImageButton) view.findViewById(R.id.btnGoToPrevious2);

        DayOfTheMonthListItem item1 = new DayOfTheMonthListItem("Groceries", "unknown", "22","JAN");
        DayOfTheMonthListItem item2 = new DayOfTheMonthListItem("Groceries", "unknown", "22","JAN");
        DayOfTheMonthListItem item3 = new DayOfTheMonthListItem("Rent", "unknown", "22","JAN");
        DayOfTheMonthListItem item4 = new DayOfTheMonthListItem("Transport", "unknown", "20","JAN");
        DayOfTheMonthListItem item5 = new DayOfTheMonthListItem("Groceries", "unknown", "20","JAN");
        DayOfTheMonthListItem item6 = new DayOfTheMonthListItem("Transport", "unknown", "19","JAN");
        DayOfTheMonthListItem item7 = new DayOfTheMonthListItem("Bills", "unknown", "19","JAN");
        DayOfTheMonthListItem item8 = new DayOfTheMonthListItem("Groceries", "unknown", "18","JAN");
        DayOfTheMonthListItem item9 = new DayOfTheMonthListItem("Transport", "unknown", "18","JAN");
        DayOfTheMonthListItem item10 = new DayOfTheMonthListItem("Groceries", "unknown", "15","JAN");

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

        //getActivity() is the context for fragment, so in fragments use getActivity() insted of this
        MonthListItemAdapter adapter = new MonthListItemAdapter(getActivity(), R.layout.adapter_view_layout, MontlyList);
        mListView.setAdapter(adapter);

        goToCurrentMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //this will give us acces to every method inside of the main activity
                ((ActivityOne)getActivity()).setViewPager(0);
            }
        });
        goToPrevious2Month.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
              //this will give us acces to every method inside of the main activity
             ((ActivityOne)getActivity()).setViewPager(2);
              }
        });



    return view;
    }
}
