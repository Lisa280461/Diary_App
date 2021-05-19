package com.example.diaryapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.lifecycle.ViewModelProviders;

import com.example.diaryapp.R;
import com.example.diaryapp.model.entries.EntryAdapter;
import com.example.diaryapp.model.entries.Entry;
import com.example.diaryapp.viewmodel.EntryViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EntriesFragment extends Fragment implements Serializable {

    private RecyclerView recyclerView;
    private EntryViewModel viewModel;
    private EntryAdapter adapter;
    private ArrayList<Entry> entryList;
    private RecyclerView.LayoutManager layoutManager;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.entries_fragment, container, false);

        viewModel = ViewModelProviders.of(this).get(EntryViewModel.class);


        recyclerView = (RecyclerView) rootView.findViewById(R.id.allrecycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        //needed to ensure that the correct user data is returned and therewith the right entries are displayed
        String userEmail=null;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userEmail = user.getEmail();
        }

        //set the entries on the adapter
        viewModel.getEntriesByUser(userEmail).observe(getViewLifecycleOwner(), new Observer<List<Entry>>() {
            @Override
            public void onChanged(List<Entry> entries) {

                adapter.setEntries((List<Entry>) entries);
            }
        });

        adapter = new EntryAdapter(entryList);
        recyclerView.setAdapter(adapter);


        // Add the functionality to swipe items in the recycler view
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    // onMove() is not implemented and therefore but on false
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    // On a left swipe the entry will be deleted, whereas on a left swipe a new intent will be opened to display the whole entry
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        if(direction == ItemTouchHelper.LEFT) {
                            int position = viewHolder.getAdapterPosition();
                            Entry toBeDeletedEntry = adapter.getEntryAtPosition(position);

                            // Delete the entry
                            viewModel.deleteEntry(toBeDeletedEntry);
                        }
                        else if (direction == ItemTouchHelper.RIGHT){
                            int position = viewHolder.getAdapterPosition();
                            Entry selectedEntry = adapter.getEntryAtPosition(position);

                            //go to Selected Entry and transfer Entry to the new intent, required that Entry implemented parcelable
                            Intent intent = new Intent(getActivity(), SelectedEntryActivity.class);
                            intent.putExtra("Entry Selected", (Parcelable) selectedEntry);
                            startActivity(intent);
                            //ensures that the items are still visible when returning from the SelectedEntryActivity
                            adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                        }
                    }

                });
        helper.attachToRecyclerView(recyclerView);


        return rootView;

    }


}