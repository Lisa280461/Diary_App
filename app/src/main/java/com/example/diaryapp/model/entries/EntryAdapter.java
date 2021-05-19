package com.example.diaryapp.model.entries;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diaryapp.R;

import java.util.ArrayList;
import java.util.List;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.RegisterHolder> {
    //enables the recyclerView to hold data from the entry

    private List<Entry> entries = new ArrayList<>();

    public EntryAdapter( List<Entry> entries)
    {
        entries = this.entries;
    }

    @NonNull
    @Override
    public RegisterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.entry_contrainer, parent, false);
        return new RegisterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RegisterHolder holder, int position) {

        Entry entry =entries.get(position);

        holder.title.setText(entry.getTitle());
        holder.dateTime.setText(entry.getDateTime());

    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
        notifyDataSetChanged();
    }

    class RegisterHolder extends RecyclerView.ViewHolder
    {

        private final TextView title;
        private final TextView dateTime;


        public RegisterHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.entryTitle);
            dateTime = itemView.findViewById(R.id.textDateTime);
        }
    }
    public Entry getEntryAtPosition (int position) {
        return entries.get(position);
    }
}



