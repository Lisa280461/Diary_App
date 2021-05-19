package com.example.diaryapp.view;


import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.diaryapp.R;
import com.example.diaryapp.model.entries.Entry;
import com.example.diaryapp.model.questionsOfTheDay.Question;
import com.example.diaryapp.viewmodel.EntryViewModel;
import com.example.diaryapp.viewmodel.QuestionViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class NewEntryFragment extends Fragment {
//screen for adding entrys

    private EditText entryTitle, entryDescription, entryAnswerQuestion;
    private TextView textDateTime, textQuestion;
    private EntryViewModel viewModel;
    private QuestionViewModel questionViewModel;
    private Spinner moodSpinner;
    private Question question;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.new_entries_fragment, container, false);

        viewModel = ViewModelProviders.of(this).get(EntryViewModel.class);

        entryTitle = rootView.findViewById(R.id.entryTitle);
        entryDescription = rootView.findViewById(R.id.entryDescription);
        textDateTime = rootView.findViewById(R.id.textDateTime);
        moodSpinner = rootView.findViewById(R.id.spinner);
        textQuestion = rootView.findViewById(R.id.textQuestion);
        entryAnswerQuestion = rootView.findViewById(R.id.entryAnswerQuestion);
        initSpinner();

        textDateTime.setText(currentDateTime());
        randomQuestion();

        Button button = rootView.findViewById(R.id.saveEntry);
        button.setOnClickListener(v -> saveEntry());

        return rootView;

    }

    private void saveEntry() {
        if (entryTitle.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext().getApplicationContext(), "Title is missing", Toast.LENGTH_LONG).show();
            return;
        } else if (entryDescription.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext().getApplicationContext(), "Description is missing", Toast.LENGTH_LONG).show();
            return;
        } else if (entryAnswerQuestion.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext().getApplicationContext(), "Answer is missing", Toast.LENGTH_LONG).show();
            return;
        }

        String userEmail = null;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userEmail = user.getEmail();

            String title = entryTitle.getText().toString();
            String description = entryDescription.getText().toString();
            String time = textDateTime.getText().toString();
            String question = textQuestion.getText().toString();
            String questionAnswer = entryAnswerQuestion.getText().toString();
            String mood = moodSpinner.getSelectedItem().toString();


            Entry theEntry = new Entry(userEmail, title, description, time, mood, question, questionAnswer);

            viewModel.insert(theEntry);
            Toast.makeText(getContext().getApplicationContext(), "Entry has been saved!", Toast.LENGTH_LONG).show();

            entryTitle.getText().clear();
            entryDescription.getText().clear();
            textDateTime.setText(currentDateTime());
            initSpinner();
            textQuestion.setText("");
            randomQuestion();
            entryAnswerQuestion.getText().clear();

        }
    }


    private void initSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.mood_array, android.R.layout.simple_spinner_item);
        moodSpinner.setAdapter(adapter);
        moodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(moodSpinner.getSelectedItem() == "Please Select A Mood...")
                {
                    Toast.makeText(getContext().getApplicationContext(), "Mood is missing", Toast.LENGTH_LONG).show();
                }
                else{
                    String mood = parent.getItemAtPosition(position).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext().getApplicationContext(), "Mood is missing", Toast.LENGTH_LONG).show();
            }
        });
    }

    private String currentDateTime(){
        String currentDate= new SimpleDateFormat("EEEE, dd MMMM HH:mm a", Locale.getDefault())
                .format(new Date());
        return currentDate;
    }

    private void randomQuestion(){
        questionViewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);
        questionViewModel.getRandomQuestion().observe(getViewLifecycleOwner(), randomQuestion -> {
            if (!randomQuestion.isEmpty()) {
                textQuestion.setText("");
                for (Question question : randomQuestion) {
                    textQuestion.append(question.getQuestion());
                }
            } else {
                textQuestion.setText("Please add a question before writing an entry!");
            }
        });
    }

}