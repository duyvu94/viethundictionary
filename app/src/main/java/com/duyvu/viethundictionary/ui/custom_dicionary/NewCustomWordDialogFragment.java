package com.duyvu.viethundictionary.ui.custom_dicionary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.duyvu.viethundictionary.R;
import com.duyvu.viethundictionary.models.Word;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NewCustomWordDialogFragment extends DialogFragment {

    public static final String TAG = "NewShoppingItemDialogFragment";

    private AlertDialog dialog;

    public interface NewCustomWordDialogListener {
        void onCustomWordCreated(Word newWord);
    }

    private NewCustomWordDialogListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentActivity activity = getActivity();
        if (activity instanceof NewCustomWordDialogListener) {
            listener = (NewCustomWordDialogListener) activity;
        } else {
           // throw new RuntimeException("Activity must implement the NewShoppingItemDialogListener interface!");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = new AlertDialog.Builder(requireContext())
                .setTitle(R.string.new_custom_word)
                .setView(getContentView())
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // TODO implement item creation
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });

        return dialog;
    }

    private View getContentView() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_new_custom_word, null);
        //nameEditText = contentView.findViewById(R.id.ShoppingItemNameEditText);
        //descriptionEditText = contentView.findViewById(R.id.ShoppingItemDescriptionEditText);
        //estimatedPriceEditText = contentView.findViewById(R.id.ShoppingItemEstimatedPriceEditText);

        return contentView;
    }
}