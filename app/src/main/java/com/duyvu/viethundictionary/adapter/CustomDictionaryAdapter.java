package com.duyvu.viethundictionary.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duyvu.viethundictionary.R;
import com.duyvu.viethundictionary.data.WordListDatabase;
import com.duyvu.viethundictionary.models.Word;
import com.duyvu.viethundictionary.ui.custom_dicionary.DeleteCustomWordDialogFragment;
import com.duyvu.viethundictionary.ui.custom_dicionary.NewCustomWordDialogFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class CustomDictionaryAdapter
        extends RecyclerView.Adapter<CustomDictionaryAdapter.DictionaryViewHolder>
        implements Filterable, SearchView.OnQueryTextListener,
            NewCustomWordDialogFragment.NewCustomWordDialogListener,
            DeleteCustomWordDialogFragment.DeleteCustomWordDialogListener {

    private final List<Word> items;
    private final List<Word> filteredItems;

    private SearchView searchView;
    private View root;

    public void setListener(DictionaryItemClickListener listener) {
        this.listener = listener;
    }

    private DictionaryItemClickListener listener;
    private FragmentManager fragmentManager;

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

    public static CustomDictionaryAdapter getInstance(View root, FragmentManager fragmentManager){
        if (dictionaryAdapter == null){
            dictionaryAdapter = new CustomDictionaryAdapter();
        }
        dictionaryAdapter.fragmentManager = fragmentManager;
        dictionaryAdapter.root = root;
        return dictionaryAdapter;
    }

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
                filterWords();
            }
        }.execute();
    }

    private void filterWords(){
        if (searchView != null)
            getFilter().filter(searchView.getQuery().toString());
        else{
            filteredItems.clear();
            filteredItems.addAll(items);
        }
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
    public void onBindViewHolder(@NonNull DictionaryViewHolder holder, final int position) {
        Word item = filteredItems.get(position);
        holder.wordTextView.setText(item.word);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DeleteCustomWordDialogFragment(filteredItems.get(position), fragmentManager).show(fragmentManager, DeleteCustomWordDialogFragment.TAG);
            }
        });
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

    private boolean contains(String s){
        for(Word word : items){
            if (word.word.equals(s))
                return true;
        }
        return false;
    }

    @Override
    public void onCustomWordCreated(final Word newWord, final Context context, final FragmentManager fragmentManager) {

        if (newWord.word == null || newWord.description == null || newWord.word.length() == 0 || newWord.description.length() == 0) {
            Snackbar.make(root, R.string.item_insert_blank_message, Snackbar.LENGTH_SHORT).show();
            return;
        }
        else if (contains(newWord.word)){
            Snackbar.make(root, R.string.item_insert_exist_message, Snackbar.LENGTH_SHORT).show();
            return;
        }
        searchView.setQuery("", false);
        searchView.clearFocus();

        new AsyncTask<Void, Void, Word>() {

            @Override
            protected Word doInBackground(Void... voids) {
                newWord.id = WordListDatabase.getInstance(context).dictionaryItemDao().insert(newWord);
                return newWord;
            }

            @Override
            protected void onPostExecute(Word item) {
                CustomDictionaryAdapter.getInstance().addItem(item);
            }
        }.execute();

    }

    @Override
    public void onCustomWordDeleted(final Word deletingWord, final Context context, final FragmentManager fragmentManager) {
        new AsyncTask<Void, Void, Word>() {

            @Override
            protected Word doInBackground(Void... voids) {
                WordListDatabase.getInstance(context).dictionaryItemDao().deleteItem(deletingWord);
                return deletingWord;
            }

            @Override
            protected void onPostExecute(Word deletingWord) {
                CustomDictionaryAdapter.getInstance().deleteItem(deletingWord);
            }
        }.execute();
    }

    public void addItem(Word item) {
        items.add(item);
        filterWords();
        notifyDataSetChanged();
        Snackbar.make(root, R.string.item_insert_message, Snackbar.LENGTH_SHORT).show();
    }

    public void deleteItem(Word item) {
        items.remove(item);
        filteredItems.remove(item);
        notifyDataSetChanged();
        Snackbar.make(root, R.string.item_removed_message, Snackbar.LENGTH_SHORT).show();
    }

    public void setSearchView(SearchView searchView) {
        this.searchView = searchView;
    }

    public interface DictionaryItemClickListener{
        void onItemChanged(Word item);
    }

    class DictionaryViewHolder extends RecyclerView.ViewHolder {
        TextView wordTextView;
        ImageButton deleteButton, editButton;

        DictionaryViewHolder(View itemView) {
            super(itemView);
            wordTextView = itemView.findViewById(R.id.word);

            deleteButton = itemView.findViewById(R.id.word_remove_button);
            editButton = itemView.findViewById(R.id.word_edit_button);

            deleteButton.setVisibility(View.VISIBLE);
            editButton.setVisibility(View.VISIBLE);

        }
    }
}