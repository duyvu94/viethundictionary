package com.duyvu.viethundictionary.ui.custom_dicionary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.duyvu.viethundictionary.R;
import com.duyvu.viethundictionary.adapter.CustomDictionaryAdapter;
import com.duyvu.viethundictionary.models.Word;

public class NewCustomWordDialogFragment extends DialogFragment {

    public static final String TAG = "NewWordDialogFragment";

    private AlertDialog dialog;
    private EditText wordText;
    private EditText descriptionText;
    private Spinner typeSpinner;

    public interface NewCustomWordDialogListener {
        void onCustomWordCreated(Word newWord, Context context);
    }

    private NewCustomWordDialogListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listener = CustomDictionaryAdapter.getInstance();
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
                        listener.onCustomWordCreated(getWord(), getContext());
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

    private Word getWord() {
        Word item = new Word(wordText.getText().toString(),
                Word.Type.getByOrdinal(typeSpinner.getSelectedItemPosition()),
                descriptionText.getText().toString(), Word.Category.PRIVATE);
        return item;
    }

    private View getContentView() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_new_custom_word, null);
        wordText = contentView.findViewById(R.id.WordEditText);
        descriptionText = contentView.findViewById(R.id.WordDescriptionEditText);
        typeSpinner = contentView.findViewById(R.id.TypeSpinner);
        typeSpinner.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.type_items)));
        return contentView;
    }
}