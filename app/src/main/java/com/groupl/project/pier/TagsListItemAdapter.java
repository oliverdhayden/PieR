package com.groupl.project.pier;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by alexandra on 18/02/2018.
 */

public class TagsListItemAdapter extends ArrayAdapter<ListItemForTags> {
    public static int positionOfTheItemInTheList;

    private Context mContext;
    private int mResource;

    public TagsListItemAdapter(@NonNull Context context, int resource, ArrayList<ListItemForTags> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    static class ViewHolder {
        TextView transactionAmount;
        TextView transactionBrand;
        TextView transactionDate;
        TextView tagMessage;
        ImageButton tagTransactionButton;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String transactionAmount = getItem(position).getTransactionAmount();
        String transactionBrand = getItem(position).getTransactionBrand();
        String transactionDate = getItem(position).getTransactionDate();
        String tagMessage = getItem(position).getTagMessage();

        ViewHolder holder= new ViewHolder();

        //this logic means that: store some objects before time
        if(convertView == null) {  //if that posision hasn't been visited yet
            LayoutInflater inflater = LayoutInflater.from(mContext);

            convertView = inflater.inflate(mResource, parent, false);

            holder.transactionAmount = (TextView) convertView.findViewById(R.id.textViewTransactionAmount);
            holder.transactionBrand = (TextView) convertView.findViewById(R.id.textViewTransactionBrand);
            holder.transactionDate =  (TextView) convertView.findViewById(R.id.textViewTransactionDate);
            holder.tagMessage = (TextView) convertView.findViewById(R.id.textViewTagMessage);
            holder.tagTransactionButton = (ImageButton) convertView.findViewById(R.id.btnForTag);

            //a TAG is gest a way to store a view in memory
            //here the view will be stored in memory
            convertView.setTag(holder);
        }
        else {
            //here the view will be referenced from memory
            holder = (ViewHolder) convertView.getTag();
        }

        holder.transactionAmount.setText(transactionAmount);
        holder.transactionBrand.setText(transactionBrand);
        holder.transactionDate.setText(transactionDate);
        holder.tagMessage.setText(tagMessage);

        //set the current view possition as tag for the button
        //this will help us to delete the item which is tagged
        holder.tagTransactionButton.setTag(position);

        holder.tagTransactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get the tag of the button
                positionOfTheItemInTheList = (Integer) view.getTag();

                //open the dialog when the tag button is pressed
                if(mContext instanceof ActivityTwo){
                    ((ActivityTwo)mContext).openDialog();
                }


            }


        });

        return convertView;


    }

}
