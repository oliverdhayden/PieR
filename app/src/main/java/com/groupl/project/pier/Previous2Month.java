package com.groupl.project.pier;

import android.database.Cursor;
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
import java.util.Calendar;

/**
 * Created by alexandra on 14/02/2018.
 */

public class Previous2Month extends Fragment {

    private ListView mListView;
    private ImageButton goToCurrentMonth;
    private ImageButton goToPrevious2Month;
    Calendar c = Calendar.getInstance();
    //previous month
    int month = c.get(Calendar.MONTH)-2;
    String[] fullMonthArray = new String[]{"sdafsdf", "January", "February","March","April","May","June","July","August","September","October","November","December"};
    private TextView currentMonthChange;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(FileUpload.uploadButtonWasPressed == false) {
            View view = inflater.inflate(R.layout.previous1_month_fragment_layout, container, false);
            return view;
        }
        else {
            if (month == 0) {
                month = 12;
            }

            //************************* ACCESS TO THE DATABASE **************************
            SQLiteDatabase pierDatabase = getActivity().openOrCreateDatabase("Statement", android.content.Context.MODE_PRIVATE, null);
            Cursor cursor = pierDatabase.rawQuery("SELECT * FROM statement WHERE month = '" + month + "'", null);

            View view = inflater.inflate(R.layout.previous1_month_fragment_layout, container, false);
            currentMonthChange = (TextView) view.findViewById(R.id.currentMonthTextView);

            mListView = (ListView) view.findViewById(R.id.ListView);
            goToCurrentMonth = (ImageButton) view.findViewById(R.id.btnGoToCurrentMonth);
            goToPrevious2Month = (ImageButton) view.findViewById(R.id.btnGoToPrevious2);

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

            ArrayList<DayOfTheMonthListItem> MontlyList = new ArrayList<>();
            String[] monthArray = new String[]{"0", "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
            String monthString = monthArray[month];


            try {
                int description = cursor.getColumnIndex("description");
                int category = cursor.getColumnIndex("category");
                int value = cursor.getColumnIndex("value");
                int day = cursor.getColumnIndex("day");
                int year = cursor.getColumnIndex("year");


                cursor.moveToFirst();


                while (cursor != null) {
                    DayOfTheMonthListItem item = new DayOfTheMonthListItem("drawable://" + R.drawable.general, cursor.getString(description), cursor.getString(value), cursor.getString(day), monthString);
                    ;
                    if (cursor.getString(category).toLowerCase().equals("groceries")) {
                        item = new DayOfTheMonthListItem("drawable://" + R.drawable.groceries, cursor.getString(description), cursor.getString(value), cursor.getString(day), monthString);
                    }
                    if (cursor.getString(category).toLowerCase().equals("eating out")) {
                        item = new DayOfTheMonthListItem("drawable://" + R.drawable.eating_out, cursor.getString(description), cursor.getString(value), cursor.getString(day), monthString);
                    }
                    if (cursor.getString(category).toLowerCase().equals("transport")) {
                        item = new DayOfTheMonthListItem("drawable://" + R.drawable.transport, cursor.getString(description), cursor.getString(value), cursor.getString(day), monthString);
                    }
                    if (cursor.getString(category).toLowerCase().equals("bills")) {
                        item = new DayOfTheMonthListItem("drawable://" + R.drawable.bills, cursor.getString(description), cursor.getString(value), cursor.getString(day), monthString);
                    }
                    if (cursor.getString(category).toLowerCase().equals("rent")) {
                        item = new DayOfTheMonthListItem("drawable://" + R.drawable.rent, cursor.getString(description), cursor.getString(value), cursor.getString(day), monthString);
                    }
                    if (cursor.getString(category).toLowerCase().equals("shopping")) {
                        item = new DayOfTheMonthListItem("drawable://" + R.drawable.shopping, cursor.getString(description), cursor.getString(value), cursor.getString(day), monthString);
                    }
                    currentMonthChange.setText(fullMonthArray[month] + "\n" + cursor.getString(year));
                    MontlyList.add(item);
                    cursor.moveToNext();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            //getActivity() is the context for fragment, so in fragments use getActivity() insted of this
            MonthListItemAdapter adapter = new MonthListItemAdapter(getActivity(), R.layout.adapter_view_layout, MontlyList);
            mListView.setAdapter(adapter);

            goToCurrentMonth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //this will give us acces to every method inside of the main activity
                    ((FullStatement) getActivity()).setViewPager(0);
                }
            });
            goToPrevious2Month.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //this will give us acces to every method inside of the main activity
                    ((FullStatement) getActivity()).setViewPager(2);
                }
            });


            return view;
        }
    }
}
