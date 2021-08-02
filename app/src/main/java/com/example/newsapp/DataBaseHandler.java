package com.example.newsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataBaseHandler extends SQLiteOpenHelper {
    String note_table="NOTE_TABLE";
    String news_table="NEWS_TABLE";
    public DataBaseHandler(@Nullable Context context) {
        super(context, "database.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_note_table="CREATE TABLE IF NOT EXISTS "+note_table+" (column_id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT,note TEXT,json TEXT)";
        String create_news_table="CREATE TABLE IF NOT EXISTS "+news_table+" (column_id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT,newsUrl TEXT,imageUrl TEXT,source TEXT,author TEXT)";
        db.execSQL(create_news_table);
        db.execSQL(create_note_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void addNews(NewsData newsData)
    {
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("title",newsData.getTitle());
        cv.put("newsUrl",newsData.getNewsUrl());
        cv.put("imageUrl",newsData.getImageUrl());
        cv.put("source",newsData.getSource());
        cv.put("author",newsData.getAuthor());
        database.insert(news_table,null,cv);
        database.close();
    }
    public void addNotes(String title,String note,String json)
    {
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("title",title);
        cv.put("note",note);
        cv.put("json",json);
        database.insert(note_table,null,cv);
        database.close();
    }
    public ArrayList<NewsData> getStarredNews()
    {
        SQLiteDatabase database=this.getReadableDatabase();
        String query="SELECT * FROM "+news_table;
        Cursor cursor=database.rawQuery(query,null);
        ArrayList<NewsData> newslist=new ArrayList<NewsData>();
        if(cursor.moveToNext())
        {
            do
            {
                String title=cursor.getString(1);
                String newsUrl=cursor.getString(2);
                String imageUrl=cursor.getString(3);
                String source=cursor.getString(4);
                String author=cursor.getString(5);
                NewsData data=new NewsData(title,author,imageUrl,newsUrl,source);
                newslist.add(data);
            }while(cursor.moveToNext());
        }
        else
        {
            NewsData data=new NewsData("","","","","");
            newslist.add(data);
        }
        database.close();
        cursor.close();
        return newslist;
    }
    public ArrayList<NotesData> getNotes()
    {
        SQLiteDatabase database=this.getReadableDatabase();
        String query="SELECT * FROM "+note_table;
        Cursor cursor=database.rawQuery(query,null);
        ArrayList<NotesData> noteslist=new ArrayList<NotesData>();
        if(cursor.moveToNext())
        {
            do
            {
                String title=cursor.getString(1);
                String notes=cursor.getString(2);
                String json=cursor.getString(3);
                NotesData notesData=new NotesData(title,notes,json);
                noteslist.add(notesData);
            }while(cursor.moveToNext());
        }
        else
        {
            NotesData notesData=new NotesData("","","");
            noteslist.add(notesData);
            }
        database.close();
        cursor.close();
        return noteslist;
    }
    public void upgradeNotes(String title,String note,String json)
    {
        SQLiteDatabase database=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("title",title);
        cv.put("note",note);
        cv.put("json",json);
        String whereargs[]={title};
        database.update(note_table,cv,"title=?",whereargs);
        database.close();
    }
    public void deleteNews(String newsUrl)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String whereargs[]={newsUrl};
        db.delete(news_table,"newsUrl=?",whereargs);
        db.close();
    }
    public void deleteNotes(String title)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String whereargs[]={title};
        db.delete(note_table,"title=?",whereargs);
        db.close();
    }


}
