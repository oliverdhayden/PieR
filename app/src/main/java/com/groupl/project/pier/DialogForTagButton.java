package com.groupl.project.pier;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by alexandra on 18/02/2018.
 */

public class DialogForTagButton extends AppCompatDialogFragment {

    private String[] tagItems = {"Groceries", "Rent", "Transport", "Bills", "General", "Eating Out"};
    private int checked_item;
    private int indexOfSelectedItem;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());



        builder.setView(getView())
                .setTitle("Select one")
                .setSingleChoiceItems(tagItems, checked_item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        indexOfSelectedItem = position;
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {

                    }
                })
                .setPositiveButton("apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {

                        try {
                            SQLiteDatabase pierDatabase = getActivity().openOrCreateDatabase("Statement", android.content.Context.MODE_PRIVATE, null);
                            pierDatabase.execSQL("CREATE TABLE IF NOT EXISTS tag (description VARCHAR, category VARCHAR);");

                            pierDatabase.execSQL("INSERT INTO tag (description, category) VALUES ('"+((Tagging) getActivity()).transactionList.get(TagsListItemAdapter.positionOfTheItemInTheList).getTransactionBrand()+"','"+tagItems[indexOfSelectedItem]+"')");
                            String category = tagItems[indexOfSelectedItem];
                            String desc =((Tagging) getActivity()).transactionList.get(position+1).getTransactionBrand();
                            Log.i("Position", String.valueOf(TagsListItemAdapter.positionOfTheItemInTheList));
                            Log.i("Shop", String.valueOf(((Tagging) getActivity()).transactionList.get(TagsListItemAdapter.positionOfTheItemInTheList).getTransactionBrand()));
                            Log.i("Description", "SELECT * FROM statement WHERE description='"+desc+"'");

                            Cursor cursordata = pierDatabase.rawQuery("SELECT * FROM statement WHERE description ='"+desc+"';", null);
                            int count = cursordata.getCount();
                            int valueupdated = 0;

                            while (count > 0){
                                pierDatabase.execSQL("UPDATE statement SET category='"+category+"' WHERE description ='"+desc+"' AND category='';");
                                Log.i("Qurry","UPDATE statement SET category='"+category+"' WHERE description ='"+desc+"' AND category='';");
                                valueupdated++;
                                Log.i("Value updated", String.valueOf(valueupdated));
                                count--;
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }

                        //remove the selected transaction from the arrayList
                        ((Tagging) getActivity()).transactionList.remove(0);

                        //update the adapter because the arrayList has changed
                        ((Tagging) getActivity()).adapter.notifyDataSetChanged();

                        Toast.makeText(getContext(), "Transaction was tagged in " + tagItems[indexOfSelectedItem] + " category", Toast.LENGTH_LONG).show();
                        Toast.makeText(getContext(), "The index of the removed transaction: " + TagsListItemAdapter.positionOfTheItemInTheList, Toast.LENGTH_LONG).show();
                        Toast.makeText(getContext(), "Name"+ ((Tagging) getActivity()).transactionList.get(TagsListItemAdapter.positionOfTheItemInTheList).getTransactionAmount(), Toast.LENGTH_SHORT).show();



                    }
                });

        return builder.create(); //the dialog
    }

}
