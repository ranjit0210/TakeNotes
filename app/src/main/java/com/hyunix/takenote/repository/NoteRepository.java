package com.hyunix.takenote.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.hyunix.takenote.databse.NoteDatabase;
import com.hyunix.takenote.entity.Note;
import com.hyunix.takenote.entity.NoteDao;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteRepository
{
    private final NoteDao noteDao;
    private final LiveData<List<Note>> notes;

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public NoteRepository(Application application)
    {
        NoteDatabase noteDatabase = NoteDatabase.getInstance(application);
        noteDao = noteDatabase.noteDao();
        notes = noteDao.getAllNotes();
    }

    public void insert(Note note)
    {
//        new InsertNoteAsyncTask(noteDao).execute(note);
        executorService.execute(() -> noteDao.insert(note));
    }

    public void update(Note note)
    {
//        new UpdateNoteAsyncTask(noteDao).execute(note);
        executorService.execute(() -> noteDao.update(note));
    }

    public void delete(Note note)
    {
//        new DeleteNoteAsyncTask(noteDao).execute(note);

        executorService.execute(() -> noteDao.delete(note));
    }

    public LiveData<List<Note>> getAllNotes()
    {
        return notes;
    }

  /*  // insert
    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void>
    {

        private NoteDao noteDao;

        public InsertNoteAsyncTask(NoteDao noteDao)
        {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes)
        {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    // update
    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void>
    {
        public UpdateNoteAsyncTask(NoteDao noteDao)
        {
            this.noteDao = noteDao;
        }

        private NoteDao noteDao;

        @Override
        protected Void doInBackground(Note... notes)
        {
            noteDao.update(notes[0]);
            return null;
        }
    }

    // delete
    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void>
    {

        private NoteDao noteDao;

        public DeleteNoteAsyncTask(NoteDao noteDao)
        {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes)
        {
            noteDao.delete(notes[0]);
            return null;
        }
    }
*/


}
