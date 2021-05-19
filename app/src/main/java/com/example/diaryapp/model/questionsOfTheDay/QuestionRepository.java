package com.example.diaryapp.model.questionsOfTheDay;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.diaryapp.model.entries.Entry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class QuestionRepository {

    private static QuestionRepository instance;
    private final ExecutorService executorService;
    private final QuestionDao questionDao;
    private final LiveData<List<Question>> allQuestions;
    private final LiveData<List<Question>> randomQuestion;


    public QuestionRepository(Application application) {
        QuestionDatabase database = QuestionDatabase.getInstance(application);
        questionDao = database.questionDao();
        allQuestions = questionDao.getAllQuestions();
        randomQuestion = questionDao.getRandomQuestion();

        executorService = Executors.newFixedThreadPool(2);
    }


    public static synchronized QuestionRepository getInstance(Application application) {
        if (instance == null)
            instance = new QuestionRepository(application);

        return instance;
    }

    public LiveData<List<Question>> getAllQuestions() {
        return allQuestions;
    }

    public LiveData<List<Question>> getRandomQuestion() {
        return randomQuestion;
    }

    public void insert(Question question) {
        executorService.execute(() -> questionDao.insert(question));
    }

    public void deleteQuestion(Question question) {
        executorService.execute(() -> questionDao.deleteQuestion(question));
    }
}




