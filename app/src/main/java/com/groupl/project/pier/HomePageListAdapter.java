package com.groupl.project.pier;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static com.amazonaws.util.ClassLoaderHelper.getResource;

/**
 * Created by alexandra on 04/02/2018.
 */

public class HomePageListAdapter extends ArrayAdapter<HomePageListItem>{

    private Context mContext;
    private int mResource;

    public HomePageListAdapter(@NonNull Context context, int resource, ArrayList<HomePageListItem> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    static class ViewHolder {
        TextView spendingType;
        TextView spendingMoney;
        ImageView img;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String spendingType = getItem(position).getSpendingType();
        String money = getItem(position).getSpendingMoney();
        String imgURL = getItem(position).getImgURL();
        ViewHolder holder= new ViewHolder();

        //this logic means that: store some objects before time
        if(convertView == null) {  //if that posision hasn't been visited yet
            LayoutInflater inflater = LayoutInflater.from(mContext);

            convertView = inflater.inflate(mResource, parent, false);

            holder.spendingType = (TextView) convertView.findViewById(R.id.textViewSpentType);
            holder.spendingMoney = (TextView) convertView.findViewById(R.id.textViewMoneySpent);
            holder.img =  (ImageView) convertView.findViewById(R.id.image);


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
        imageLoader.displayImage(imgURL, holder.img, options);

        holder.spendingType.setText(spendingType);
        holder.spendingMoney.setText(money);

        // change colour of the category texts
        if(spendingType.toLowerCase().equals("rent")){
            holder.spendingType.setTextColor(convertView.getResources().getColor(R.color.b3));
        }
        if(spendingType.toLowerCase().equals("transport")){
            holder.spendingType.setTextColor(convertView.getResources().getColor(R.color.indian_red));
        }
        if(spendingType.toLowerCase().equals("shopping")){
            holder.spendingType.setTextColor(convertView.getResources().getColor(R.color.b5));
        }
        if(spendingType.toLowerCase().equals("bills")){
            holder.spendingType.setTextColor(convertView.getResources().getColor(R.color.b4));
        }
        if(spendingType.toLowerCase().equals("groceries")){
            holder.spendingType.setTextColor(convertView.getResources().getColor(R.color.b7));
        }
        if(spendingType.toLowerCase().equals("eating out")){
            holder.spendingType.setTextColor(convertView.getResources().getColor(R.color.b2));
        }
        if(spendingType.toLowerCase().equals("general")){
            holder.spendingType.setTextColor(convertView.getResources().getColor(R.color.sandy_brown));
        }

        return convertView;


    }
}
