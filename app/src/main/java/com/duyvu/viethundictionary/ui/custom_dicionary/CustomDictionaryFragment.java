package com.duyvu.viethundictionary.ui.custom_dicionary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.duyvu.viethundictionary.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CustomDictionaryFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_custom_dictionary, container, false);

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NewCustomWordDialogFragment().show(getActivity().getSupportFragmentManager(), NewCustomWordDialogFragment.TAG);
            }
        });

        return root;
    }
}