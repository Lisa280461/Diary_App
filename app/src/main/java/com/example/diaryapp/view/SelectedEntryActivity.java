package com.example.diaryapp.view;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diaryapp.R;
import com.example.diaryapp.model.entries.Entry;
import com.example.diaryapp.viewmodel.EntryViewModel;

import java.io.Serializable;


public class SelectedEntryActivity extends AppCompatActivity implements Serializable {

    private ImageButton backArrow;

    private TextView textDateTime, moodText, textQuestion, entryTitle, entryDescriptionText, entryAnswerText;

    private EntryViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_entry);

        Entry entry = getIntent().getParcelableExtra("Entry Selected");

        entryTitle = findViewById(R.id.entry_title);
        entryDescriptionText = findViewById(R.id.entryDescriptionText);
        textDateTime = findViewById(R.id.textDateTime);
        moodText = findViewById(R.id.moodText);
        textQuestion = findViewById(R.id.textQuestion);
        entryAnswerText =findViewById(R.id.entryAnswerText);

        backArrow = findViewById(R.id.toolbar_back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        entryTitle.setText(entry.getTitle());
        entryDescriptionText.setText("Entry: \n" + entry.getDescription());
        textDateTime.setText("Date: \n" + entry.getDateTime());
        moodText.setText("Mood: \n" + entry.getMood());
        textQuestion.setText("Question of The Day: \n" + entry.getQuestion());
        entryAnswerText.setText("Your Answer: \n" + entry.getQuestionAnswer());
    }

}