package com.example.diaryapp.model.entries;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class EntryRepository {

    private final EntryDao entryDao;
   // private final LiveData<List<Entry>> allEntries;
    private final LiveData<List<Entry>> entriesByUser;


    public EntryRepository(Application application)
    {
        EntryDatabase database = EntryDatabase.getInstance(application);
        entryDao = database.entryDao();
       // allEntries = entryDao.getAllEntries();


        String userEmail=null;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userEmail = user.getEmail();
        }
        entriesByUser =entryDao.getEntriesByUser(userEmail);
    }

    /*   public LiveData<List<Entry>> getAllEntries()
      {
          return allEntries;
      }
     */

    public LiveData<List<Entry>> getEntriesByUser(String userEmail)
    {
        return entriesByUser;
    }

   public void insert(Entry entry)
    {
        new InsertEntryAsyncTask(entryDao).execute(entry);
    }


    private static class InsertEntryAsyncTask extends AsyncTask<Entry, Void, Void>
    {
        private final EntryDao entryDao;
        private InsertEntryAsyncTask(EntryDao entryDao)
        {
            this.entryDao = entryDao;
        }

        @Override
        protected Void doInBackground(Entry... entries) {
            entryDao.insertEntry(entries[0]);
            return null;
        }
    }

    private static class deleteEntryAsyncTask extends AsyncTask<Entry, Void, Void> {
        private final EntryDao mAsyncTaskDao;

        deleteEntryAsyncTask(EntryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Entry... params) {
            mAsyncTaskDao.deleteEntry(params[0]);
            return null;
        }
    }

    public void deleteEntry(Entry entry)  {
        new deleteEntryAsyncTask(entryDao).execute(entry);
    }



}