package com.duyvu.viethundictionary.ui.custom_dicionary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duyvu.viethundictionary.R;
import com.duyvu.viethundictionary.adapter.CustomDictionaryAdapter;
import com.duyvu.viethundictionary.adapter.DictionaryAdapter;
import com.duyvu.viethundictionary.data.WordListDatabase;
import com.duyvu.viethundictionary.models.Word;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CustomDictionaryFragment extends Fragment {

    View root;
    private RecyclerView recyclerView;
    private CustomDictionaryAdapter adapter;

    private WordListDatabase database;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_custom_dictionary, container, false);

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NewCustomWordDialogFragment().show(getActivity().getSupportFragmentManager(), NewCustomWordDialogFragment.TAG);
            }
        });

        database = WordListDatabase.getInstance(getActivity().getApplicationContext());
        initRecyclerView();

        return root;
    }

    private void initRecyclerView() {
        recyclerView = root.findViewById(R.id.MainRecyclerView);
        adapter = CustomDictionaryAdapter.getInstance(root, getActivity().getSupportFragmentManager());
        adapter.update(database);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }
}