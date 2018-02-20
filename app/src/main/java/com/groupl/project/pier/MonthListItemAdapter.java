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
import android.widget.TextView;

import java.util.List;

/**
 * Created by alexandra on 14/02/2018.
 */

public class MonthListItemAdapter extends ArrayAdapter<DayOfTheMonthListItem> {

    private Context mContext;
    private int mResource;

    public MonthListItemAdapter(@NonNull Context context, int resource, @NonNull List<DayOfTheMonthListItem> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    static class ViewHolder {
        TextView tagName;
        TextView unknown;
        TextView dayOfTheMonth;
        TextView month;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String tagName = getItem(position).getTagName();
        String unknown = getItem(position).getUnknown();
        String dayOfTheMonth = getItem(position).getDayOfTheMonth();
        String month = getItem(position).getMonth();

        ViewHolder holder= new ViewHolder();

        //this logic means that: store some objects before time
        if(convertView == null) {  //if that posision hasn't been visited yet
            LayoutInflater inflater = LayoutInflater.from(mContext);

            convertView = inflater.inflate(mResource, parent, false);

            holder.tagName = (TextView) convertView.findViewById(R.id.textViewTagName);
            holder.unknown = (TextView) convertView.findViewById(R.id.textViewUnknown);
            holder.dayOfTheMonth = (TextView) convertView.findViewById(R.id.textViewDayOfTheMonth);
            holder.month = (TextView) convertView.findViewById(R.id.textViewMonth);

            //a TAG is gest a way to store a view in memory
            //here the view will be stored in memory
            convertView.setTag(holder);
        }
        else {
            //here the view will be referenced from memory
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tagName.setText(tagName);
        holder.unknown.setText(unknown);
        holder.dayOfTheMonth.setText(dayOfTheMonth);
        holder.month.setText(month);

        return convertView;


    }
}