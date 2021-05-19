package com.example.diaryapp.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.diaryapp.model.questionsOfTheDay.Question;
import com.example.diaryapp.model.questionsOfTheDay.QuestionRepository;

import java.util.List;

public class QuestionViewModel extends AndroidViewModel {

    private final QuestionRepository repository;
    private final LiveData<List<Question>> randomQuestion;

    public QuestionViewModel(Application app) {
        super(app);
        repository = QuestionRepository.getInstance(app);
        randomQuestion = repository.getRandomQuestion();
    }

    public LiveData<List<Question>> getAllQuestions() {
        return repository.getAllQuestions();
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

