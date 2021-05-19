package com.example.diaryapp.model.entries;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "entries")

public class Entry implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

   @ColumnInfo(name = "userEmail")
    private String userEmail;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "date_time")
    private String dateTime;

    @ColumnInfo(name = "mood")
    private String mood;

    @ColumnInfo(name = "question")
    private String question;

    @ColumnInfo(name = "questionAnswer")
    private String questionAnswer;

    @ColumnInfo(name = "description")
    private String description;

    public Entry(String userEmail, String title, String description, String dateTime, String mood, String question, String questionAnswer) {
        this.userEmail = userEmail;
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.mood = mood;
        this.question = question;
        this.questionAnswer = questionAnswer;

    }

    //parcel has been added so that an entry object can be transferred from the entries fragment to the selected entry activity
    protected Entry(Parcel in) {
        id = in.readInt();
        userEmail = in.readString();
        title = in.readString();
        dateTime = in.readString();
        mood = in.readString();
        question = in.readString();
        questionAnswer = in.readString();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(userEmail);
        dest.writeString(title);
        dest.writeString(dateTime);
        dest.writeString(mood);
        dest.writeString(question);
        dest.writeString(questionAnswer);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Entry> CREATOR = new Creator<Entry>() {
        @Override
        public Entry createFromParcel(Parcel in) {
            return new Entry(in);
        }

        @Override
        public Entry[] newArray(int size) {
            return new Entry[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }


    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }


}