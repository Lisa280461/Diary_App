package com.example.diaryapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.diaryapp.model.entries.Entry;
import com.example.diaryapp.model.entries.EntryRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class EntryViewModel extends AndroidViewModel {
    private String userEmail=null;
    private final EntryRepository repository;
   // private final LiveData<List<Entry>> allEntries;
    private final LiveData<List<Entry>> entriesByUser;

    public EntryViewModel(@NonNull Application application) {
        super(application);
        repository = new EntryRepository(application);
       // allEntries = repository.getAllEntries();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userEmail = user.getEmail();
        }
        entriesByUser = repository.getEntriesByUser(userEmail);
    }


    public void insert(Entry entry)
    {
        repository.insert(entry);
    }

   /* public LiveData<List<Entry>> getAllEntries()
    {
        return  allEntries;
    }
*/
    public void deleteEntry(Entry entry) {repository.deleteEntry(entry);}

    public LiveData<List<Entry>> getEntriesByUser(String userEmail){return entriesByUser;}


}