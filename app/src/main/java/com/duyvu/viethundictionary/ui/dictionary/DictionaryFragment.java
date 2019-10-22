package com.duyvu.viethundictionary.ui.dictionary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.duyvu.viethundictionary.R;

public class DictionaryFragment extends Fragment {

    private DictionaryViewModel dictionaryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dictionaryViewModel =
                ViewModelProviders.of(this).get(DictionaryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dictionary, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        dictionaryViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}