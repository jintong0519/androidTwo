package com.example.jintong.shujuku;

import android.provider.BaseColumns;

import java.io.Serializable;

public class shuju implements Serializable{
    private String id;
    private String title;
    private String date;
    private String message;
    public shuju() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    @Override
    public String toString() {
        return title+"," + date + "," + message;
    }
    public shuju(String id,String title, String date, String message) {
        super();
        this.id = id;
        this.date=date;
        this.title=title;
        this.message=message;
    }
    /*public shuju() {

    }
        public static abstract class sj implements BaseColumns {
            public static final String TABLE_NAME = "shuju";
            public static final String COLUMN_NAME_TITLE = "title";
            public static final String COLUMN_NAME_DATE = "date";
            public static final String COLUMN_NAME_MESSAGE = "message";

        }
*/}
