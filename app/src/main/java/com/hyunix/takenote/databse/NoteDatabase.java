package com.hyunix.takenote.databse;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.hyunix.takenote.entity.Note;
import com.hyunix.takenote.entity.NoteDao;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase
{
    private static NoteDatabase instance;

    public abstract NoteDao noteDao();
}
