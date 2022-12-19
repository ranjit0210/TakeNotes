package com.hyunix.takenote;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hyunix.takenote.activity.AddNoteActivity;
import com.hyunix.takenote.adapter.NoteAdapter;
import com.hyunix.takenote.entity.Note;
import com.hyunix.takenote.viewmodel.NoteViewModel;

public class MainActivity extends AppCompatActivity
{

    private NoteViewModel noteViewModel;
    private NoteAdapter adapter = new NoteAdapter();

    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // to register activity
        setActivityResultLauncher();

        RecyclerView recyclerView = findViewById(R.id.recyclerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        noteViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(NoteViewModel.class);

        noteViewModel.getAllNote().observe(this, notes -> adapter.setNotes(notes));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        getMenuInflater().inflate(R.menu.new_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.top_menu:
                Intent i = new Intent(MainActivity.this, AddNoteActivity.class);
                // startActivityForResult(i, 1); // deprecated function logic

                // ActivityResultLauncher current work on it
                activityResultLauncher.launch(i);

                //activityResultLauncher
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void setActivityResultLauncher()
    {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

            int resultCode = result.getResultCode();

            Intent data = result.getData();

            if (resultCode == RESULT_OK && data != null)
            {
                String title = data.getStringExtra("noteTitle");
                String description = data.getStringExtra("noteDescription");

                Note note = new Note(title, description);

                noteViewModel.insert(note);
            }
        });
    }
/*

// Deprecated method code
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null)
        {
            String title = data.getStringExtra("noteTitle");
            String description = data.getStringExtra("noteDescription");

            Note note = new Note(title, description);
            noteViewModel.insert(note);
        }
    }*/
}