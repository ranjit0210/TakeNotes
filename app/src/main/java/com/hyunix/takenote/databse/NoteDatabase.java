package com.hyunix.takenote.databse;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.hyunix.takenote.entity.Note;
import com.hyunix.takenote.entity.NoteDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase
{
    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context)
    {

        if (instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    //.allowMainThreadQueries() --> not recommended
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db)
        {
            super.onCreate(db);

            NoteDao noteDao = instance.noteDao();

            ExecutorService executorService = Executors.newSingleThreadExecutor();

            executorService.execute(() -> {
                noteDao.insert(new Note("Title 1", "Description1"));
                noteDao.insert(new Note("Title 2", "Description2"));
                noteDao.insert(new Note("Title 3", "Description3"));
                noteDao.insert(new Note("Title 4", "Description4"));
                noteDao.insert(new Note("Title 5", "Description5"));
                noteDao.insert(new Note("Title 6", "Description6"));
            });

//            new PopulateAsyncTask(instance).execute();

        }
    };
/*
    private static class PopulateAsyncTask extends AsyncTask<Void, Void, Void>
    {
        public PopulateAsyncTask(NoteDatabase noteDatabase)
        {
            this.noteDao = noteDatabase.noteDao();
        }

        private NoteDao noteDao;

        @Override
        protected Void doInBackground(Void... voids)
        {

            noteDao.insert(new Note("Title 1", "Description1"));
            noteDao.insert(new Note("Title 2", "Description2"));
            noteDao.insert(new Note("Title 3", "Description3"));
            noteDao.insert(new Note("Title 4", "Description4"));
            noteDao.insert(new Note("Title 5", "Description5"));
            noteDao.insert(new Note("Title 6", "Description6"));
            return null;
        }
    }*/
}
