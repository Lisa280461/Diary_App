package com.example.diaryapp.model.questionsOfTheDay;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import com.example.diaryapp.model.entries.Entry;

import java.util.List;

@Dao
public interface QuestionDao {
    @Insert
    void insert(Question question);

    @Delete
    void deleteQuestion(Question question);

   // @Query("SELECT * FROM questionsOfTheDay ORDER BY id DESC")
   // LiveData<List<Question>> getAllQuestions();

    @Query("SELECT * FROM questionsOfTheDay WHERE userEmail=:userEmail ORDER BY id DESC")
    LiveData<List<Question>> getQuestionsByUser(String userEmail);


    @Query("SELECT * FROM questionsOfTheDay ORDER BY RANDOM() LIMIT 1")
    LiveData<List<Question>> getRandomQuestion();
}


