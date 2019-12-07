package com.duyvu.viethundictionary.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.duyvu.viethundictionary.adapter.DictionaryAdapter;
import com.duyvu.viethundictionary.model.Word;

import java.util.concurrent.Executors;

@Database(
        entities = {Word.class},
        version = 1,
        exportSchema = false
)
@TypeConverters(value = {Word.Category.class, Word.Type.class})
public abstract class WordListDatabase extends RoomDatabase {
    public abstract DictionaryItemDao dictionaryItemDao();

    private static WordListDatabase INSTANCE;

    public synchronized static WordListDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }

    private static WordListDatabase buildDatabase(final Context context) {

        return Room.databaseBuilder(context,
                WordListDatabase.class,
                "dictionary")
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                getInstance(context).dictionaryItemDao().insertAll(Word.populateData(context));
                                DictionaryAdapter.getInstance().update(getInstance(context));
                            }
                        });
                    }
                })
                .build();
    }
}