package com.hyunix.takenote;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hyunix.takenote.adapter.NoteAdapter;
import com.hyunix.takenote.viewmodel.NoteViewModel;

public class MainActivity extends AppCompatActivity
{

    private NoteViewModel noteViewModel;
    private NoteAdapter adapter = new NoteAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        noteViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(NoteViewModel.class);

        noteViewModel.getAllNote().observe(this, notes -> adapter.setNotes(notes));
    }
}