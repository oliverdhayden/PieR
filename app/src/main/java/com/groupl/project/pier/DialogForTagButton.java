package com.groupl.project.pier;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.widget.Toast;

/**
 * Created by alexandra on 18/02/2018.
 */

public class DialogForTagButton extends AppCompatDialogFragment {

    private String[] tagItems = {"Groceries", "Rent", "Tfl", "Bills", "Shopping", "General", "Entertainment"};
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

                        //remove the selected transaction from the arrayList
                        ((ActivityTwo)getActivity()).transactionList.remove(0);

                        //update the adapter because the arrayList has changed
                        ((ActivityTwo)getActivity()).adapter.notifyDataSetChanged();

                        Toast.makeText(getContext(), "Transaction was tagged in " + tagItems[indexOfSelectedItem] + " category" , Toast.LENGTH_LONG).show();
                        Toast.makeText(getContext(), "The index of the removed transaction: " + TagsListItemAdapter.positionOfTheItemInTheList, Toast.LENGTH_LONG).show();
                    }
                });

        return builder.create(); //the dialog
    }

}
