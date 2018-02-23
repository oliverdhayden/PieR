package com.groupl.project.pier;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by ollie on 28/01/2018.
 */

public class Tagging extends AppCompatActivity {

    //raju
    private DrawerLayout myDrawerLaout;
    private ActionBarDrawerToggle myToggle;
    NavigationView navigation;

    private ListView mListView;

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

        ListItemForTags item1 = new ListItemForTags("$20", "Tesco", "12/3.2018", "Please select the adequate TAG for this payment");
        ListItemForTags item2 = new ListItemForTags("$120", "BritishGas", "12/3.2018", "Please select the adequate TAG for this payment");
        ListItemForTags item3 = new ListItemForTags("$234", "Sainsbury", "11/3.2018", "Please select the adequate TAG for this payment");
        ListItemForTags item4 = new ListItemForTags("$230", "Amazon", "10/3.2018", "Please select the adequate TAG for this payment");
        ListItemForTags item5 = new ListItemForTags("$45", "eBay", "12/2.2018", "Please select the adequate TAG for this payment");
        ListItemForTags item6 = new ListItemForTags("$67", "Tesco", "12/2.2018", "Please select the adequate TAG for this payment");
        ListItemForTags item7 = new ListItemForTags("$89", "Tesco", "9/2.2018", "Please select the adequate TAG for this payment");
        ListItemForTags item8 = new ListItemForTags("$567", "Landlord", "9/2.2018", "Please select the adequate TAG for this payment");
        ListItemForTags item9 = new ListItemForTags("$56", "Tesco", "9/2.2018", "Please select the adequate TAG for this payment");
        ListItemForTags item10 = new ListItemForTags("$23", "Tesco", "9/2.2018", "Please select the adequate TAG for this payment");
        ListItemForTags item11 = new ListItemForTags("$46", "Tesco", "9/2.2018", "Please select the adequate TAG for this payment");
        ListItemForTags item12 = new ListItemForTags("$56", "Tesco", "9/2.2018", "Please select the adequate TAG for this payment");

        transactionList = new ArrayList<>();
        transactionList.add(item1);
        transactionList.add(item2);
        transactionList.add(item3);
        transactionList.add(item4);
        transactionList.add(item5);
        transactionList.add(item6);
        transactionList.add(item7);
        transactionList.add(item8);
        transactionList.add(item9);
        transactionList.add(item10);
        transactionList.add(item11);
        transactionList.add(item12);

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
                    case R.id.ic_home:
                        Intent intent0 = new Intent(Tagging.this, MainActivity.class);
                        startActivity(intent0);
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.ic_pie:
                        Intent intent1 = new Intent(Tagging.this, FullStatement.class);
                        startActivity(intent1);
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.ic_money:
                        break;

                    case R.id.ic_settings:
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

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.login:
                        Intent login = new Intent(Tagging.this,Login.class);
                        startActivity(login);
                        break;
                    case R.id.setting:
                        Intent setting = new Intent(Tagging.this,settingPage.class);
                        startActivity(setting);
                        break;
                    case R.id.about:
                        Intent about = new Intent(Tagging.this,aboutUS.class);
                        startActivity(about);
                        break;
                }
                return false;
            }

        });

        //raju
        myDrawerLaout = (DrawerLayout) findViewById(R.id.drawer);
        myToggle = new ActionBarDrawerToggle(this,myDrawerLaout,R.string.Open,R.string.Close);
        myDrawerLaout.addDrawerListener(myToggle);
        myToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    //raju - opens the menu tab
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String idStr = getResources().getResourceName(id);
        if (myToggle.onOptionsItemSelected(item)){
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openDialog() {
        DialogForTagButton dialog = new DialogForTagButton();
        dialog.show(getSupportFragmentManager(), "tag dialog");
    }
}
