package com.example.diaryapp.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.diaryapp.model.questionsOfTheDay.Question;
import com.example.diaryapp.model.questionsOfTheDay.QuestionRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class QuestionViewModel extends AndroidViewModel {
    private String userEmail=null;
    private final QuestionRepository repository;
    private final LiveData<List<Question>> randomQuestion;

    public QuestionViewModel(Application app) {
        super(app);
        repository = QuestionRepository.getInstance(app);
        randomQuestion = repository.getRandomQuestion();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userEmail = user.getEmail();
        }

    }

    public LiveData<List<Question>> getAllQuestionsByUser() {
        return repository.getAllQuestionsByUser(userEmail);
    }

    public LiveData<List<Question>> getRandomQuestion()
    {
        return  randomQuestion;
    }


    public void insert(final Question question) {
        repository.insert(question);
    }

    public void deleteQuestion(Question question) {repository.deleteQuestion(question);}
}

