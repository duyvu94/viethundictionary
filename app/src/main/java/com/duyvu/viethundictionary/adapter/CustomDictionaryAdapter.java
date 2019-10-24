package com.duyvu.viethundictionary.adapter;

import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duyvu.viethundictionary.R;
import com.duyvu.viethundictionary.data.WordListDatabase;
import com.duyvu.viethundictionary.models.Word;

import java.util.ArrayList;
import java.util.List;

public class CustomDictionaryAdapter
        extends RecyclerView.Adapter<CustomDictionaryAdapter.DictionaryViewHolder> implements Filterable, SearchView.OnQueryTextListener {

    private final List<Word> items;
    private final List<Word> filteredItems;

    public void setListener(DictionaryItemClickListener listener) {
        this.listener = listener;
    }

    private DictionaryItemClickListener listener;

    private static CustomDictionaryAdapter dictionaryAdapter;

    private Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Word> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0)
                filteredList.addAll(items);
            else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Word item : items) {
                    if (item.word.toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            filteredItems.clear();
            filteredItems.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public static CustomDictionaryAdapter getInstance(){
        if (dictionaryAdapter == null){
            dictionaryAdapter = new CustomDictionaryAdapter();
        }
        return dictionaryAdapter;
    }

    private CustomDictionaryAdapter() {
        items = new ArrayList<>();
        filteredItems = new ArrayList<>();
    }

    public void update(final WordListDatabase database) {
        new AsyncTask<Void, Void, List<Word>>() {

            @Override
            protected List<Word> doInBackground(Void... voids) {
                return database.dictionaryItemDao().getAllCustomWords();
            }

            @Override
            protected void onPostExecute(List<Word> words) {
                items.clear();
                items.addAll(words);
                filteredItems.clear();
                filteredItems.addAll(words);
                notifyDataSetChanged();
            }
        }.execute();
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
        Word item = filteredItems.get(position);
        holder.wordTextView.setText(item.word);
    }

    @Override
    public int getItemCount() {
        return filteredItems.size();
    }

    @Override
    public Filter getFilter() {
        return myFilter;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        getFilter().filter(s);
        return false;
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