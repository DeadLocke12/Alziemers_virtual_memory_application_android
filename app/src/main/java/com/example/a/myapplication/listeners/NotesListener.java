package com.example.a.myapplication.listeners;


import com.example.a.myapplication.entities.Note;

public interface NotesListener {
    void onNoteClicked(Note note, int position);
}
