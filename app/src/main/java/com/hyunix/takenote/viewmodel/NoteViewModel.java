package com.hyunix.takenote.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hyunix.takenote.entity.Note;
import com.hyunix.takenote.repository.NoteRepository;

import java.util.List;

public class NoteViewModel extends AndroidViewModel
{
    private final NoteRepository noteRepository;

    private final LiveData<List<Note>> notes;


    public NoteViewModel(@NonNull Application application)
    {
        super(application);
        noteRepository = new NoteRepository(application);
        notes = noteRepository.getAllNotes();
    }

    public void insert(Note note)
    {
        noteRepository.insert(note);
    }

    public void update(Note note)
    {
        noteRepository.update(note);
    }

    public void delete(Note note)
    {
        noteRepository.delete(note);
    }

    public LiveData<List<Note>> getAllNote()
    {
        return notes;
    }
}
