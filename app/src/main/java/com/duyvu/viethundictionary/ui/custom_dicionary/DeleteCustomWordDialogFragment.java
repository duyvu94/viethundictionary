package com.duyvu.viethundictionary.ui.custom_dicionary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.duyvu.viethundictionary.R;
import com.duyvu.viethundictionary.adapter.CustomDictionaryAdapter;
import com.duyvu.viethundictionary.models.Word;

public class DeleteCustomWordDialogFragment extends DialogFragment {

    public static final String TAG = "DeleteWordDialogFragment";

    private AlertDialog dialog;
    private Word deletingWord;

    public interface DeleteCustomWordDialogListener {
        void onCustomWordDeleted(Word deletingWord, Context context);
    }

    private DeleteCustomWordDialogListener listener;

    public DeleteCustomWordDialogFragment(Word word){
        super();
        this.deletingWord = word;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listener = CustomDictionaryAdapter.getInstance();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = new AlertDialog.Builder(requireContext())
                .setTitle(R.string.delete_custom_word)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onCustomWordDeleted(deletingWord, getContext());
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
}