package com.duyvu.viethundictionary.ui.dictionary;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.duyvu.viethundictionary.R;
import com.duyvu.viethundictionary.adapter.DictionaryAdapter;
import com.duyvu.viethundictionary.data.WordListDatabase;
import com.duyvu.viethundictionary.models.Word;

import java.util.List;

public class DictionaryFragment extends Fragment implements DictionaryAdapter.DictionaryItemClickListener {

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
        adapter.setListener(this);
        adapter.update(database);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemChanged(Word item) {

    }
}