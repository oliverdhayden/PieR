package com.groupl.project.pier;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by ollie on 28/01/2018.
 */

public class Tagging extends AppCompatActivity {

    //raju
    private DrawerLayout myDrawerLaout;
    private ActionBarDrawerToggle myToggle;
    NavigationView navigation;

    private ListView mListView;

    int groceries = 0, rent = 0, transport = 0, bills = 0, untagged = 0, eatingOut = 0, general = 0;
    SQLiteDatabase pierDatabase;
    int month = 0;
    int year = 0;

    //I set the transactions details and the adapter as public
    //because in the moment after the user will tag a transaction
    //it will be removed from TAGS TAB
    //TO DO: and should be send to SPENDINGS TAB
    public ArrayList<ListItemForTags> transactionList;  //in DialogForButton.java this var is used
    public TagsListItemAdapter adapter;   //in DialogForButton.java this var is used

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setAllowEnterTransitionOverlap(false);
        getWindow().setAllowReturnTransitionOverlap(false);
        setContentView(R.layout.activity_tagging);

        mListView = (ListView) findViewById(R.id.listViewIdForTag);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.actionbar_logo);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        transactionList = new ArrayList<>();

        try {
            // create a tabase if not exist, if does make it accessable
            pierDatabase = Tagging.this.openOrCreateDatabase("Statement", MODE_PRIVATE, null);
            Cursor cursor = pierDatabase.rawQuery("SELECT * FROM statement WHERE category = ''", null);

            int description = cursor.getColumnIndex("description");
            int value = cursor.getColumnIndex("value");
            int day = cursor.getColumnIndex("day");
            int month = cursor.getColumnIndex("month");
            int year = cursor.getColumnIndex("year");


            cursor.moveToFirst();


            while (cursor != null) {
                ListItemForTags item = new ListItemForTags(cursor.getString(value), cursor.getString(description), cursor.getString(day)+"/"+cursor.getString(month)+"/"+cursor.getString(year), "Please select the adequate TAG for this payment");
                transactionList.add(item);
                cursor.moveToNext();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // get last date of data
        try {
            Cursor getdate = pierDatabase.rawQuery("SELECT * FROM statement;",null);
            getdate.moveToFirst();
            Log.i("Date count", String.valueOf(getdate.getCount()));
            int monthIndex = getdate.getColumnIndex("month");
            int yearIndex = getdate.getColumnIndex("year");
            month = getdate.getInt(monthIndex);
            year = getdate.getInt(yearIndex);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        // add data to preference
        Cursor getmonthdata = pierDatabase.rawQuery("SELECT * FROM statement WHERE year ='" + year + "' and month='" + month + "';", null);
        Log.i("Cursor", "SELECT * FROM statement WHERE year ='" + year + "' and month='" + month + "';");
        Log.i("CursorSelected", String.valueOf(getmonthdata.getCount()));
        int categoryIndex = getmonthdata.getColumnIndex("category");
        int valueIndex = getmonthdata.getColumnIndex("value");
        getmonthdata.moveToFirst();
        int cursor = getmonthdata.getCount();
        try {
            while (cursor != 0 ) {
                Log.i("Category", getmonthdata.getString(categoryIndex));
                if (getmonthdata.getString(categoryIndex).toLowerCase().equals("groceries")) {
                    groceries += getmonthdata.getInt(valueIndex);
                    Log.i("G value", String.valueOf(groceries));
                }
                if (getmonthdata.getString(categoryIndex).toLowerCase().equals("general")) {
                    general += getmonthdata.getInt(valueIndex);
                }
                if (getmonthdata.getString(categoryIndex).toLowerCase().equals("eating out")) {
                    eatingOut += getmonthdata.getInt(valueIndex);
                }
                if (getmonthdata.getString(categoryIndex).toLowerCase().equals("transport")) {
                    transport += getmonthdata.getInt(valueIndex);
                }
                if (getmonthdata.getString(categoryIndex).toLowerCase().equals("rent")) {
                    rent += getmonthdata.getInt(valueIndex);
                }
                if (getmonthdata.getString(categoryIndex).toLowerCase().equals("bills")) {
                    bills += getmonthdata.getInt(valueIndex);
                }
                if (getmonthdata.getString(categoryIndex).toLowerCase().equals("")) {
                    untagged += getmonthdata.getInt(valueIndex);
                }
                cursor--;
                getmonthdata.moveToNext();
            }
            preference.setPreference(this, "groceries", String.valueOf(groceries));
            preference.setPreference(this, "general", String.valueOf(general));
            preference.setPreference(this, "eatingOut", String.valueOf(eatingOut));
            preference.setPreference(this, "transport", String.valueOf(transport));
            preference.setPreference(this, "rent", String.valueOf(rent));
            preference.setPreference(this, "bills", String.valueOf(bills));
            preference.setPreference(this, "untagged", String.valueOf(untagged));
            int monthTotal = groceries + general + eatingOut + transport + rent + bills + untagged;
            preference.setPreference(this, "monthTotal", String.valueOf(monthTotal));

            Log.i("GroceriesF", preference.getPreference(this,"groceries"));
        } catch (Exception e) {
            e.printStackTrace();
        }



        adapter = new TagsListItemAdapter(this, R.layout.adapter_view_for_tag, transactionList);
        mListView.setAdapter(adapter);


        //code for nav bar
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_summary:
                        Intent intent0 = new Intent(Tagging.this, MainActivity.class);
                        startActivity(intent0);
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.ic_full_statement:
                        Intent intent1 = new Intent(Tagging.this, FullStatement.class);
                        startActivity(intent1);
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.ic_tagging:
                        break;

                    case R.id.ic_feedback:
                        Intent intent3 = new Intent(Tagging.this, Feedback.class);
                        startActivity(intent3);
                        overridePendingTransition(0, 0);
                        break;
                }
                return false;
            }
        });
        //raju
        navigation = (NavigationView) findViewById(R.id.navigation_view);
        View headerView = navigation.getHeaderView(0);
        TextView username = (TextView) headerView.findViewById(R.id.header_username);
        username.setText(preference.getPreference(this, "username").toUpperCase());
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.signOut:
                        Intent SignOut = new Intent(Tagging.this, SignOutActivity.class);
                        startActivity(SignOut);
                        break;
                    case R.id.setting:
                        Intent setting = new Intent(Tagging.this, settingPage.class);
                        startActivity(setting);
                        break;
                    case R.id.about:
                        Intent about = new Intent(Tagging.this, aboutUS.class);
                        startActivity(about);
                        break;
                    case R.id.upload:
                        Intent upload = new Intent(Tagging.this, FileUpload.class);
                        startActivity(upload);
                }
                return false;
            }

        });

        //raju
        myDrawerLaout = (DrawerLayout) findViewById(R.id.drawer);
        myToggle = new ActionBarDrawerToggle(this, myDrawerLaout, R.string.Open, R.string.Close);
        myDrawerLaout.addDrawerListener(myToggle);
        myToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    //raju - opens the menu tab
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String idStr = getResources().getResourceName(id);
        if (myToggle.onOptionsItemSelected(item)) {
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openDialog() {
        DialogForTagButton dialog = new DialogForTagButton();
        dialog.show(getSupportFragmentManager(), "tag dialog");
    }


    public void refresh(){
        Intent intent = new Intent(this,Tagging.class);
        finish();
        startActivity(intent);
    }
}
