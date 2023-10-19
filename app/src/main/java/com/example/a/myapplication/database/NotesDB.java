package com.example.a.myapplication.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.a.myapplication.dao.NoteDao;
import com.example.a.myapplication.entities.Note;


@Database(entities = Note.class, version = 1, exportSchema = false)
public abstract class NotesDB extends RoomDatabase {

    private static NotesDB notesDB;

    public static synchronized NotesDB getDatabase(Context context){
        if (notesDB == null){
            notesDB = Room.databaseBuilder(
                    context,
                    NotesDB.class,
                    "notes_db"
            ).build();
        }

        return notesDB;
    }

    public abstract NoteDao noteDao();

}
