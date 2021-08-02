package com.example.newsapp;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SpansToString {

    public String toJsonString(Spannable text) {
        int index = 0;
        String json="";
        try {
            for (int i = 0; i < text.length(); i++) {
                char ch = text.charAt(i);
                if (ch == ' ' || ch == '\n') {
                    //Log.d("word=",text.subSequence(index,i).toString());
                    JSONObject obj = new JSONObject();
                    BackgroundColorSpan spans[] = text.getSpans(index, i, BackgroundColorSpan.class);
                    if (spans.length != 0) {
                        try {
                            obj.put("BackColor", spans[spans.length - 1].getBackgroundColor());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    AbsoluteSizeSpan sizeSpan[] = text.getSpans(index, i, AbsoluteSizeSpan.class);
                    if (sizeSpan.length != 0) {
                        try {
                            obj.put("Size", sizeSpan[sizeSpan.length - 1].getSize());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    AlignmentSpan align[] = text.getSpans(index, i, AlignmentSpan.class);
                    if (align.length != 0) {
                        try {
                            obj.put("AlingSpan", align[align.length - 1].getAlignment());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    CustomTypefaceSpan font[] = text.getSpans(index, i, CustomTypefaceSpan.class);
                    if (font.length != 0) {
                        try {
                            obj.put("FontSpan", font[font.length - 1].getTypeface());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    try {
                        if (align.length != 0 || spans.length != 0 || sizeSpan.length != 0 || font.length != 0) {
                            obj.put("SpanStart", index);
                            obj.put("SpanEnd", i);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    index = i + 1;
                    if (!obj.toString().equals("{}")) {
                        json = json + obj.toString() + ',';
                    }
                }
            }
            Log.d("Json=", "[" + json.substring(0, json.length() - 1) + "]");
            return "[" + json.substring(0, json.length() - 1) + "]";
        }
        catch (Exception e) {
        return "";
        }
    }
    public Spannable applySpan(Spannable text, String json, Activity activity)
    {
        try {
            JSONArray arr=new JSONArray(json);
            for(int x=0;x<arr.length();x++)
            {
                JSONObject ob=new JSONObject(arr.getString(x));
                int i=ob.getInt("SpanStart");
                int f=ob.getInt("SpanEnd");
                try {
                    BackgroundColorSpan backColor = new BackgroundColorSpan(ob.getInt("BackColor"));
                    text.setSpan(backColor, i,f, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                catch (Exception e) {
                    Log.d("Error ",e.getMessage());
                }
                try {
                    AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(ob.getInt("Size"), true);
                    text.setSpan(sizeSpan, i, f, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                catch (Exception e) {
                    Log.d("Error ",e.getMessage());
                }
                try
                {
                    AlignmentSpan align=new AlignmentSpan.Standard(Layout.Alignment.valueOf(ob.getString("AlingSpan")));
                    text.setSpan(align,i,f,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }catch (Exception e) {
                    Log.d("Error ",e.getMessage());
                }
                try
                {
                    Typeface font=Typeface.createFromAsset(activity.getAssets(),ob.getString("FontSpan"));
                    text.setSpan(new CustomTypefaceSpan(font,ob.getString("FontSpan")),i,f,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                catch (Exception e) {
                    Log.d("Error ",e.getMessage());
                }
            }
        } catch (JSONException e) {
            Log.d("Error ",e.getMessage());
        }

        return text;
    }

}