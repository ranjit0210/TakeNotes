package com.hyunix.takenote;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hyunix.takenote.activity.AddNoteActivity;
import com.hyunix.takenote.activity.UpdateActivity;
import com.hyunix.takenote.adapter.NoteAdapter;
import com.hyunix.takenote.entity.Note;
import com.hyunix.takenote.viewmodel.NoteViewModel;

public class MainActivity extends AppCompatActivity
{

    private NoteViewModel noteViewModel;
    private NoteAdapter adapter = new NoteAdapter();

    ActivityResultLauncher<Intent> activityResultLauncherForAddNote;
    ActivityResultLauncher<Intent> activityResultLauncherForUpdateNote;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // to register activity
        setActivityResultLauncherAddNote();
        setActivityResultLauncherForUpdateNote();

        RecyclerView recyclerView = findViewById(R.id.recyclerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        noteViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(NoteViewModel.class);

        noteViewModel.getAllNote().observe(this, notes -> adapter.setNotes(notes));

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)
        {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target)
            {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction)
            {
                noteViewModel.delete(adapter.getNotes(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, R.string.note_delete, Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnClickListener(note -> {
            Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
            intent.putExtra("id", note.getId());
            intent.putExtra("title", note.getTitle());
            intent.putExtra("description", note.getDescription());
            activityResultLauncherForUpdateNote.launch(intent);
        });
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
                activityResultLauncherForAddNote.launch(i);

                //activityResultLauncher
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void setActivityResultLauncherForUpdateNote()
    {
        activityResultLauncherForUpdateNote = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

            int resultCode = result.getResultCode();
            Intent data = result.getData();

            if (resultCode == RESULT_OK && data != null)
            {
                int id = data.getIntExtra("id", -1);
                String title = data.getStringExtra("lastTitle");
                String description = data.getStringExtra("lastDescription");

                Note note = new Note(title, description);
                note.setId(id);
                noteViewModel.update(note);
            }

        });
    }

    public void setActivityResultLauncherAddNote()
    {
        activityResultLauncherForAddNote = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

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