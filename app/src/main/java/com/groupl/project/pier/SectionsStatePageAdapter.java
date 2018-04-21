package com.groupl.project.pier;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class SectionsStatePageAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mFragmentTitleList = new ArrayList<>();  //this var is to keep a record of our fragments names

    public SectionsStatePageAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    //we was forced to override the next two methods

    //this method will return the item in question that we are looking for
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    //return the size of the list
    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
