package com.hyunix.takenote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hyunix.takenote.R;

public class AddNoteActivity extends AppCompatActivity
{

    EditText editTextTitle, editTextDescription;
    Button buttonCancel, buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.add_notes);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_add_note);

        init();

        clickEvent();

    }

    private void init()
    {
        buttonCancel = findViewById(R.id.btnCancel);
        buttonSave = findViewById(R.id.btnSave);

        editTextTitle = findViewById(R.id.editTextViewTitle);
        editTextDescription = findViewById(R.id.editTextViewDescription);
    }

    private void clickEvent()
    {
        buttonCancel.setOnClickListener(v -> {

            Toast.makeText(this, R.string.nothing_show, Toast.LENGTH_SHORT).show();
            finish();
        });

        buttonSave.setOnClickListener(v -> {
            saveNote();
        });
    }

    public void saveNote()
    {
        String title, description;

        title = editTextTitle.getText().toString();
        description = editTextDescription.getText().toString();

        if (!title.isEmpty())
        {
            Intent intent = new Intent();
            intent.putExtra("noteTitle", title);
            intent.putExtra("noteDescription", description);
            setResult(RESULT_OK, intent);
            finish();
        }
        else
        {
            Toast.makeText(this, R.string.title_error, Toast.LENGTH_SHORT).show();
        }
    }
}