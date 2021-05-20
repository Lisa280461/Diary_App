package com.example.diaryapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diaryapp.R;
import com.example.diaryapp.model.questionsOfTheDay.Question;
import com.example.diaryapp.model.questionsOfTheDay.QuestionAdapter;
import com.example.diaryapp.viewmodel.QuestionViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class QuestionsFragment extends Fragment {
    private QuestionViewModel viewModel;
    private EditText editText;

    private ArrayList<Question> questionList;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private QuestionAdapter adapter;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.questions_fragment, container, false);

        editText = rootView.findViewById(R.id.editText);

        viewModel = new ViewModelProvider(this).get(QuestionViewModel.class);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.all_questions_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        viewModel.getAllQuestionsByUser().observe(getViewLifecycleOwner(), new Observer<List<Question>>() {
            @Override
            public void onChanged(List<Question> questions) {

                adapter.setQuestions((List<Question>) questions);
            }
        });

        adapter = new QuestionAdapter(questionList);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Question toBeDeletedQuestion = adapter.getQuestionAtPosition(position);

                        viewModel.deleteQuestion(toBeDeletedQuestion);
                    }
                });

        helper.attachToRecyclerView(recyclerView);

        Button button = rootView.findViewById(R.id.button);
        button.setOnClickListener(v -> saveQuestion(rootView));

        return rootView;
    }

    public void saveQuestion(View v) {
        String userEmail = null;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userEmail = user.getEmail();
        }
        viewModel.insert(new Question(editText.getText().toString(),userEmail));
    }


}

