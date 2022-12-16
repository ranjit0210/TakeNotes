package com.hyunix.takenote;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.hyunix.takenote.entity.Note;
import com.hyunix.takenote.viewmodel.NoteViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity
{

    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(NoteViewModel.class);

        noteViewModel.getAllNote().observe(this, new Observer<List<Note>>()
        {
            @Override
            public void onChanged(List<Note> notes)
            {

            }
        });
    }
}