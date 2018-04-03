package com.groupl.project.pier;

/**
 * Created by alexandra on 14/02/2018.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Calendar;
import java.util.List;

/**
 * Created by alexandra on 14/02/2018.
 */

public class MonthListItemAdapter extends ArrayAdapter<DayOfTheMonthListItem> {
    Calendar c = Calendar.getInstance();
    //previous month
    int currentMonth = c.get(Calendar.MONTH);
    private Context mContext;
    private int mResource;

    public MonthListItemAdapter(@NonNull Context context, int resource, @NonNull List<DayOfTheMonthListItem> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    static class ViewHolder {
        ImageView imgUrl;
        TextView brandName;
        TextView dayOfTheMonth;
        TextView month;
        TextView amountOfTheTransaction;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String[] fullMonthArray = new String[]{"sdafsdf", "January", "February","March","April","May","June","July","August","September","October","November","December"};
        String img = getItem(position).getImgUrl();
        String brandName = getItem(position).getBrandName();
        String dayOfTheMonth = getItem(position).getDayOfTheMonth();
        String amountOfTheTransaction = getItem(position).getAmountOfTheTransaction();
        String month = getItem(position).getMonth();

        ViewHolder holder= new ViewHolder();

        //this logic means that: store some objects before time
        if(convertView == null) {  //if that posision hasn't been visited yet
            LayoutInflater inflater = LayoutInflater.from(mContext);

            convertView = inflater.inflate(mResource, parent, false);


            holder.imgUrl = (ImageView) convertView.findViewById(R.id.imageForSpendings);
            holder.brandName = (TextView) convertView.findViewById(R.id.textViewBrandName);
            holder.dayOfTheMonth = (TextView) convertView.findViewById(R.id.textViewDayOfTheMonth);
            holder.month = (TextView) convertView.findViewById(R.id.textViewMonth);
            holder.amountOfTheTransaction = (TextView) convertView.findViewById(R.id.textViewAmountOfTheTransaction);
            //a TAG is gest a way to store a view in memory
            //here the view will be stored in memory
            convertView.setTag(holder);
        }
        else {
            //here the view will be referenced from memory
            holder = (ViewHolder) convertView.getTag();
        }

        int  defaultImage = mContext.getResources().getIdentifier("@drawable/image_failed", null, mContext.getPackageName());
        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(defaultImage)
                .showImageOnFail(defaultImage)
                .showImageOnLoading(defaultImage).build();

        //download and display image from url
        imageLoader.displayImage(img, holder.imgUrl, options);

        holder.brandName.setText(brandName);
        holder.dayOfTheMonth.setText(dayOfTheMonth);
        holder.month.setText(month);
        holder.amountOfTheTransaction.setText(amountOfTheTransaction);

        return convertView;


    }
}