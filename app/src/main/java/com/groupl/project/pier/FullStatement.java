package com.groupl.project.pier;

import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
        import android.view.Menu;
        import android.view.MenuItem;

/**
 * Created by ollie on 28/01/2018.

-------------READ THIS TO UNDERSTAND WHICH CLASSES AND LAYOUTS ARE BEING USED FOR SPENDINGS TAB---------------

The following classes are only for Spendings:
DayOfTheMonthListItem, MonthListItemAdaptor, Previous1Month, Previous2Month, Previous3Month, SectionsStatePagerAdapter, FullStatement

The following layouts are for Spendings:
activity_full_statement, adapter_view_layout, current_month_fragment_layout, previous1_month_fragment_layout, previous2_month_fragment_layout, previous3_month_fragment_layout

 */
public class FullStatement extends AppCompatActivity {

    private SectionsStatePageAdapter mSectionsStatePageAdapter;
    private ViewPager mViewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        int navBar = R.id.bottomBar
//        Transition fade = new Fade();
//        fade.excludeTarget(navBar, true);
//        fade.excludeTarget(android.R.id.navigationBarBackground, true);
//        getWindow().setExitTransition(fade);
//        getWindow().setEnterTransition(fade);

        setContentView(R.layout.activity_full_statement);


        mSectionsStatePageAdapter = new SectionsStatePageAdapter(getSupportFragmentManager());  //code for fragments
        mViewPager = (ViewPager) findViewById(R.id.container);  //code for fragments
        //setup the pager
        setupViewPager(mViewPager);  //code for fragments


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_home:
                        Intent intent0 = new Intent(FullStatement.this, MainActivity.class);
                        startActivity(intent0);
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.ic_pie:
                        break;

                    case R.id.ic_money:
                        Intent intent2 = new Intent(FullStatement.this, Tagging.class);
                        startActivity(intent2);
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.ic_settings:
                        Intent intent3 = new Intent(FullStatement.this, Feedback.class);
                        startActivity(intent3);
                        overridePendingTransition(0, 0);
                        break;
                }
                return false;
            }
        });
    }

    public void setupViewPager(ViewPager viewPager) {
        SectionsStatePageAdapter adapter = new SectionsStatePageAdapter(getSupportFragmentManager());

        adapter.addFragment(new CurrentMonth(), "CurrentMonth");  //by default this is the fragment that will be displayed
        adapter.addFragment(new Previous1Month(), "Previous1Month");
        adapter.addFragment(new Previous2Month(), "Previous2Month");
        adapter.addFragment(new Previous3Month(), "Previous3Month");

        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber) {
        mViewPager.setCurrentItem(fragmentNumber);
    }
}
