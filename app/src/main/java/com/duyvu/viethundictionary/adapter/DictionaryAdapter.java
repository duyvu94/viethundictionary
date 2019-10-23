package com.duyvu.viethundictionary.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duyvu.viethundictionary.R;
import com.duyvu.viethundictionary.models.Word;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DictionaryAdapter
        extends RecyclerView.Adapter<DictionaryAdapter.DictionaryViewHolder> {

    private final List<Word> items;

    private DictionaryItemClickListener listener;

    public DictionaryAdapter(DictionaryItemClickListener listener) {
        this.listener = listener;
        items = new ArrayList<>();
    }

    public void update(List<Word> dictionary) {
        items.clear();
        items.addAll(dictionary);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DictionaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.word_list, parent, false);
        return new DictionaryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DictionaryViewHolder holder, int position) {
        Word item = items.get(position);
        holder.wordTextView.setText(item.word);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface DictionaryItemClickListener{
        void onItemChanged(Word item);
    }

    class DictionaryViewHolder extends RecyclerView.ViewHolder {
        TextView wordTextView;

        DictionaryViewHolder(View itemView) {
            super(itemView);
            wordTextView = itemView.findViewById(R.id.word);
        }
    }
}