package com.groupl.project.pier;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by alexandra on 14/02/2018.
 */

public class Previous1Month extends Fragment {

    private ListView mListView;
    private ImageButton goToCurrentMonth;
    private ImageButton goToPrevious2Month;
    private TextView previous1MonthTV;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.previous1_month_fragment_layout, container, false);

        mListView = (ListView)view.findViewById(R.id.ListView);
        goToCurrentMonth = (ImageButton) view.findViewById(R.id.btnGoToCurrentMonth);
        goToPrevious2Month = (ImageButton) view.findViewById(R.id.btnGoToPrevious2);
        previous1MonthTV = (TextView)view.findViewById(R.id.previous1MonthTextView);

        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getActivity().getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP

        DayOfTheMonthListItem item1 = new DayOfTheMonthListItem("drawable://" + R.drawable.groceries, "Tesco", "£22","22","JAN");
        DayOfTheMonthListItem item2 = new DayOfTheMonthListItem("drawable://" + R.drawable.groceries, "Tesco", "£22","22","JAN");
        DayOfTheMonthListItem item3 = new DayOfTheMonthListItem("drawable://" + R.drawable.rent, "unknown", "£22","22","JAN");
        DayOfTheMonthListItem item4 = new DayOfTheMonthListItem("drawable://" + R.drawable.transportation, "Tfl", "£20","20","JAN");
        DayOfTheMonthListItem item5 = new DayOfTheMonthListItem("drawable://" + R.drawable.groceries, "Tesco", "£20","19","JAN");
        DayOfTheMonthListItem item6 = new DayOfTheMonthListItem("drawable://" + R.drawable.transportation, "Tfl", "£19","19","JAN");
        DayOfTheMonthListItem item7 = new DayOfTheMonthListItem("drawable://" + R.drawable.bills, "British Gas", "£19","19","JAN");
        DayOfTheMonthListItem item8 = new DayOfTheMonthListItem("drawable://" + R.drawable.groceries, "Tesco", "£18","18","JAN");
        DayOfTheMonthListItem item9 = new DayOfTheMonthListItem("drawable://" + R.drawable.transportation, "Tfl", "£18","18","JAN");
        DayOfTheMonthListItem item10 = new DayOfTheMonthListItem("drawable://" + R.drawable.groceries, "Tesco", "£15","15","JAN");

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

        previous1MonthTV.setText("March \n 2018");

        goToCurrentMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //this will give us acces to every method inside of the main activity
                ((FullStatement)getActivity()).setViewPager(0);
            }
        });
        goToPrevious2Month.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
              //this will give us acces to every method inside of the main activity
             ((FullStatement)getActivity()).setViewPager(2);
              }
        });



    return view;
    }
}
