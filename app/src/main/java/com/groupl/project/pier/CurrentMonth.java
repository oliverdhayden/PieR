package com.groupl.project.pier;

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

/**
 * Created by alexandra on 14/02/2018.
 */

public class CurrentMonth extends Fragment {

    private ListView mListView;
    private ImageButton goToPrevious1Month;
    private TextView currentMonthTV;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.current_month_fragment_layout, container, false);

        mListView = (ListView) view.findViewById(R.id.ListView);
        goToPrevious1Month = (ImageButton) view.findViewById(R.id.btnGoToPrevious1);
        currentMonthTV = (TextView)view.findViewById(R.id.currentMonthTextView);

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

        DayOfTheMonthListItem item1 = new DayOfTheMonthListItem("drawable://" + R.drawable.groceries, "Lidl", "£12", "12","FEB");
        DayOfTheMonthListItem item2 = new DayOfTheMonthListItem("drawable://" + R.drawable.groceries, "Tesco", "£12", "12","FEB");
        DayOfTheMonthListItem item3 = new DayOfTheMonthListItem("drawable://" + R.drawable.rent, "Landlord", "£120", "12","FEB");
        DayOfTheMonthListItem item4 = new DayOfTheMonthListItem("drawable://" + R.drawable.transportation, "Tfl", "£10","10","FEB");
        DayOfTheMonthListItem item5 = new DayOfTheMonthListItem("drawable://" + R.drawable.groceries, "Tesco", "£10","10","FEB");
        DayOfTheMonthListItem item6 = new DayOfTheMonthListItem("drawable://" + R.drawable.transportation, "Tfl", "£9","9","FEB");
        DayOfTheMonthListItem item7 = new DayOfTheMonthListItem("drawable://" + R.drawable.bills, "British Gas", "£9","9","FEB");
        DayOfTheMonthListItem item8 = new DayOfTheMonthListItem("drawable://" + R.drawable.groceries, "Sainsburys", "£8","8","FEB");
        DayOfTheMonthListItem item9 = new DayOfTheMonthListItem("drawable://" + R.drawable.groceries, "Tfl", "£83","8","FEB");
        DayOfTheMonthListItem item10 = new DayOfTheMonthListItem("drawable://" + R.drawable.groceries, "Lidl", "£54","8","FEB");
        DayOfTheMonthListItem item11 = new DayOfTheMonthListItem("drawable://" + R.drawable.bills, "BritishGas", "£93","8","FEB");
        DayOfTheMonthListItem item12 = new DayOfTheMonthListItem("drawable://" + R.drawable.groceries, "Lidl", "£8","8","FEB");
        DayOfTheMonthListItem item13 = new DayOfTheMonthListItem("drawable://" + R.drawable.transportation, "Tfl", "£81","5","FEB");
        DayOfTheMonthListItem item14 = new DayOfTheMonthListItem("drawable://" + R.drawable.groceries, "Lidl", "£5","5","FEB");

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

        currentMonthTV.setText("April \n 2018");

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
