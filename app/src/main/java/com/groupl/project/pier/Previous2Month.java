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

public class Previous2Month extends Fragment {

    private ListView mListView;
    private ImageButton goToPrevious1Month;
    private ImageButton goToPrevious3Month;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.previous2_month_fragment_layout, container, false);

        mListView = (ListView) view.findViewById(R.id.ListView);
        goToPrevious1Month = (ImageButton) view.findViewById(R.id.btnGoToPrevious1);
        goToPrevious3Month = (ImageButton) view.findViewById(R.id.btnGoToPrevious3);

        DayOfTheMonthListItem item1 = new DayOfTheMonthListItem("Groceries", "unknown", "23","DEC");
        DayOfTheMonthListItem item2 = new DayOfTheMonthListItem("Groceries", "unknown", "23","DEC");
        DayOfTheMonthListItem item3 = new DayOfTheMonthListItem("Rent", "unknown", "9","DEC");
        DayOfTheMonthListItem item4 = new DayOfTheMonthListItem("Transport", "unknown", "8","DEC");
        DayOfTheMonthListItem item5 = new DayOfTheMonthListItem("Groceries", "unknown", "8","DEC");
        DayOfTheMonthListItem item6 = new DayOfTheMonthListItem("Transport", "unknown", "8","DEC");
        DayOfTheMonthListItem item7 = new DayOfTheMonthListItem("Bills", "unknown", "8","DEC");
        DayOfTheMonthListItem item8 = new DayOfTheMonthListItem("Groceries", "unknown", "8","DEC");
        DayOfTheMonthListItem item9 = new DayOfTheMonthListItem("Transport", "unknown", "7","DEC");
        DayOfTheMonthListItem item10 = new DayOfTheMonthListItem("Groceries", "unknown", "7","DEC");

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

        goToPrevious1Month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //this will give us acces to every method inside of the main activity
                ((ActivityOne)getActivity()).setViewPager(1);
            }
        });
        goToPrevious3Month.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
              //this will give us acces to every method inside of the main activity
             ((ActivityOne)getActivity()).setViewPager(3);
              }
        });



    return view;
    }
}
