package com.example.diaryapp.model.questionsOfTheDay;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = Question.class, version = 1, exportSchema = false)
public abstract class QuestionDatabase extends RoomDatabase {
    private static com.example.diaryapp.model.questionsOfTheDay.QuestionDatabase instance;

    public abstract QuestionDao questionDao();

    public static synchronized com.example.diaryapp.model.questionsOfTheDay.QuestionDatabase getInstance(Context context)
    {
        if(instance==null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    com.example.diaryapp.model.questionsOfTheDay.QuestionDatabase.class, "questionsOfTheDay")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();

        }
        return instance;
    }

    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

        }
    };
}