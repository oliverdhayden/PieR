package com.groupl.project.pier;

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
 * Created by alexandra on 27/02/2018.
 */

public class FeedbackListItemAdapter extends ArrayAdapter<FeedbackTagListItem> {

    private Context mContext;
    private int mResource;

    public FeedbackListItemAdapter(@NonNull Context context, int resource, @NonNull List<FeedbackTagListItem> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    static class ViewHolder {
        TextView redLight;
        TextView yellowLight;
        TextView greenLight;
        TextView feedbackTag;
        TextView feedbackMessage;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String stringRedLight = getItem(position).getColorRed();
        String stringYellowLight = getItem(position).getColorYellow();
        String stringGreenLight = getItem(position).getColorGreen();
        String stringFeedbackTag = getItem(position).getFeedbackTag();
        String stringFeedbackMessage = getItem(position).getFeedbackMessage();

        FeedbackListItemAdapter.ViewHolder holder= new FeedbackListItemAdapter.ViewHolder();

        //this logic means that: store some objects before time
        if(convertView == null) {  //if that posision hasn't been visited yet
            LayoutInflater inflater = LayoutInflater.from(mContext);

            convertView = inflater.inflate(mResource, parent, false);

            holder.redLight = (TextView) convertView.findViewById(R.id.textViewRedLight);
            holder.yellowLight = (TextView) convertView.findViewById(R.id.textViewYellowLight);
            holder.greenLight = (TextView) convertView.findViewById(R.id.textViewGreenLight);
            holder.feedbackTag = (TextView) convertView.findViewById(R.id.textViewFeedbackTag);
            holder.feedbackMessage = (TextView) convertView.findViewById(R.id.textViewFeedbackMessage);
            //a TAG is gest a way to store a view in memory
            //here the view will be stored in memory
            convertView.setTag(holder);
        }
        else {
            //here the view will be referenced from memory
            holder = (FeedbackListItemAdapter.ViewHolder) convertView.getTag();
        }

        int redLightResID = mContext.getResources().getIdentifier(stringRedLight , "drawable", Feedback.PACKAGE_NAME);
        int yellowLightResID = mContext.getResources().getIdentifier(stringYellowLight , "drawable", Feedback.PACKAGE_NAME);
        int greenLightResID = mContext.getResources().getIdentifier(stringGreenLight , "drawable", Feedback.PACKAGE_NAME);

        holder.redLight.setBackgroundResource(redLightResID);
        holder.yellowLight.setBackgroundResource(yellowLightResID);
        holder.greenLight.setBackgroundResource(greenLightResID);
        holder.feedbackTag.setText(stringFeedbackTag);
        holder.feedbackMessage.setText(stringFeedbackMessage);

        return convertView;


    }

}
