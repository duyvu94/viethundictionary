package com.duyvu.viethundictionary.fragment.dictionary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duyvu.viethundictionary.R;
import com.duyvu.viethundictionary.adapter.DictionaryAdapter;
import com.duyvu.viethundictionary.data.WordListDatabase;

public class DictionaryFragment extends Fragment{

    View root;
    private RecyclerView recyclerView;
    private DictionaryAdapter adapter;

    private WordListDatabase database;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_dictionary, container, false);

        database = WordListDatabase.getInstance(getActivity().getApplicationContext());

        initRecyclerView();
        return root;
    }

    private void initRecyclerView() {
        recyclerView = root.findViewById(R.id.MainRecyclerView);
        adapter = DictionaryAdapter.getInstance();
        adapter.update(database);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }
}