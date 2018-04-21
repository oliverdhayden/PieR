package com.groupl.project.pier;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;


import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;



public class FeedbackListItemAdapter extends ArrayAdapter<FeedbackTagListItem> {

    private Context mContext;
    private int mResource;

    public FeedbackListItemAdapter(@NonNull Context context, int resource, @NonNull List<FeedbackTagListItem> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    static class ViewHolder {
        ImageView trafficLight;
        TextView feedbackTag;
        TextView feedbackMessage;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String stringTrafficLight = getItem(position).getTrafficLight();
        String stringFeedbackTag = getItem(position).getFeedbackTag();
        String stringFeedbackMessage = getItem(position).getFeedbackMessage();

        ViewHolder holder = new ViewHolder();

        //this logic means that: store some objects before time
        if (convertView == null) {  //if that posision hasn't been visited yet
            LayoutInflater inflater = LayoutInflater.from(mContext);

            convertView = inflater.inflate(mResource, parent, false);

            holder.trafficLight = (ImageView) convertView.findViewById(R.id.light);
            holder.feedbackTag = (TextView) convertView.findViewById(R.id.textViewFeedbackTag);
            holder.feedbackMessage = (TextView) convertView.findViewById(R.id.textViewFeedbackMessage);
            //a TAG is gest a way to store a view in memory
            //here the view will be stored in memory
            convertView.setTag(holder);
        } else {
            //here the view will be referenced from memory
            holder = (ViewHolder) convertView.getTag();
        }

        int defaultImage = mContext.getResources().getIdentifier("@drawable/image_failed", null, mContext.getPackageName());
        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(defaultImage)
                .showImageOnFail(defaultImage)
                .showImageOnLoading(defaultImage).build();

        //download and display image from url
        imageLoader.displayImage(stringTrafficLight, holder.trafficLight, options);

        holder.feedbackTag.setText(stringFeedbackTag);
        holder.feedbackMessage.setText(stringFeedbackMessage);

        return convertView;


    }

}
