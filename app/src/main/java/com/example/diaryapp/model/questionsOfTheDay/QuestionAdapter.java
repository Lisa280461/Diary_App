package com.example.diaryapp.model.questionsOfTheDay;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diaryapp.R;

import java.util.ArrayList;
import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.RegisterHolder> {

    private List<Question> questions = new ArrayList<>();

    public QuestionAdapter( List<Question> questions)
    {
        questions = this.questions;
    }

    @NonNull
    @Override
    public QuestionAdapter.RegisterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_container, parent, false);
        return new QuestionAdapter.RegisterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAdapter.RegisterHolder holder, int position) {

        Question entry =questions.get(position);

        holder.questionText.setText(entry.getQuestion());

    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
        notifyDataSetChanged();
    }

    class RegisterHolder extends RecyclerView.ViewHolder
    {

        private final TextView questionText;


        public RegisterHolder(@NonNull View itemView) {
            super(itemView);
            questionText = itemView.findViewById(R.id.question);

        }
    }
    public Question getQuestionAtPosition (int position) {
        return questions.get(position);
    }
}



