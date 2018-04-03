package com.groupl.project.pier;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.groupl.project.pier.R;

/**
 * Created by kremi on 22/02/2018.
 */

public class SlideAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;

    // list of img
    public int[] lst_img = {R.drawable.slide_1,R.drawable.slide_2,R.drawable.slide_3,};

    // list of titles
    public String[] lst_title = {"Categorize","Machine Learning","Plugins"};
    // list description
    public String[] lst_description ={
            "Catagorise your spending to keep tabs on where your hard earned money ends up.",
            "Using machine learning techniques, our app will create a predictive model based on your income and expenditure so you don\\'t have to worry about working out if you can afford to get the next round in.",
            "We also offer a range of additional plugins that show you where and how you can save on everything from grocerie to rent."
    };
    // list of background colors
    public int[] lst_bgcolor = {
            Color.rgb(239,85,85),
            Color.rgb(51,149,255),
            Color.rgb(242,237,216)
    };

    public SlideAdapter(Context context){
        this.context = context;
    }



    @Override
    public int getCount() {
        return lst_title.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_welcome_page,container,false);
        LinearLayout layoutslide = (LinearLayout) view.findViewById(R.id.sliderlayout);
        Button button = (Button) view.findViewById(R.id.slider_button);
        ImageView imgslide = (ImageView) view.findViewById(R.id.slideimg);
        TextView txtTitle = (TextView) view.findViewById(R.id.slidetitle);
        TextView txtLeft = (TextView) view.findViewById(R.id.slideleft);
        TextView txtDescription = (TextView) view.findViewById(R.id.slidedesciption);
        layoutslide.setBackgroundColor(lst_bgcolor[position]);
        imgslide.setImageResource(lst_img[position]);
        txtTitle.setText(lst_title[position]);
        txtDescription.setText(lst_description[position]);
        if(position == lst_title.length -1){
            button.setVisibility(View.VISIBLE);
            txtLeft.setVisibility(View.GONE);
        }
        else{
            button.setVisibility(View.GONE);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
