package com.example.notes;

import android.util.JsonWriter;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;

public class Note implements Serializable {

    private final String title;
    private final String note;
    private final String date;

    Note (String t, String n, String d) {
        this.title = t;
        this.note = n;
        this.date=d;
    }

    public String getNote() {
        return note;
    }
    public String getTitle() {
        return title;
    }
    public String getDate() {
        return date;
    }


    /*public void setTitle(String newTitle) {
        this.title = newTitle;
    }
    public void setNote(String newNote) {
        this.note = newNote;
    }
*/
    @NonNull
    public String toString() {

        try {
            StringWriter sw = new StringWriter();
            JsonWriter jsonWriter = new JsonWriter(sw);
            jsonWriter.setIndent("  ");
            jsonWriter.beginObject();
            jsonWriter.name("title").value(getTitle());
            jsonWriter.name("note").value(getNote());
            jsonWriter.name("date").value(getDate());

            jsonWriter.endObject();
            jsonWriter.close();
            return sw.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}






