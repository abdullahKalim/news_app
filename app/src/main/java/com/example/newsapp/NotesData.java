package com.example.newsapp;

public class NotesData {
    String title;
    String note;
    String json;
    public NotesData(String title,String note,String json)
    {
        this.title=title;
        this.note=note;
        this.json=json;
    }

    public String getTitle() {
        return title;
    }

    public String getNote() {
        return note;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
