package com.duyvu.viethundictionary.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.duyvu.viethundictionary.models.Word;

import java.util.List;

@Dao
public interface DictionaryItemDao {
    @Query("SELECT * FROM word")
    List<Word> getAll();

    @Insert
    long insert(Word word);

    @Insert
    void insertAll(Word... word);

    @Update
    void update(Word word);

    @Delete
    void deleteItem(Word word);
}
