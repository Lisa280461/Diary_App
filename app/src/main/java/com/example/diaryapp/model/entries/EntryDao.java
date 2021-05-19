package com.example.diaryapp.model.entries;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EntryDao {
/*    @Query("SELECT * FROM entries ORDER BY id DESC")
    LiveData<List<Entry>> getAllEntries();
*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEntry(Entry entry);

    @Delete
    void deleteEntry(Entry entry);

    @Query("SELECT * FROM entries WHERE userEmail=:userEmail ORDER BY id DESC")
    LiveData<List<Entry>> getEntriesByUser(String userEmail);
}

