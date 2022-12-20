package com.hyunix.takenote.activity;

import static com.hyunix.takenote.R.string.nothing_update;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hyunix.takenote.MainActivity;
import com.hyunix.takenote.R;

public class UpdateActivity extends AppCompatActivity
{
    EditText editTxtUpdateTitle, editTxtUpdateDescription;

    Button btnUpdateCancel, btnUpdate;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Edit Note");
        setContentView(R.layout.activity_update);

        init();

        getData();

        clickEvent();

    }

    private void init()
    {
        editTxtUpdateTitle = findViewById(R.id.editUpdateTitle);
        editTxtUpdateDescription = findViewById(R.id.editUpdateDescription);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdateCancel = findViewById(R.id.btnUpdateCancel);
    }

    private void clickEvent()
    {
        btnUpdateCancel.setOnClickListener(v -> {
            Toast.makeText(this, nothing_update, Toast.LENGTH_SHORT).show();
            finish();
        });

        btnUpdate.setOnClickListener(v -> {
            updateNote();
        });

    }

    private void updateNote()
    {
        String lastTitle = editTxtUpdateTitle.getText().toString();
        String lastDescription = editTxtUpdateDescription.getText().toString();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("lastTitle", lastTitle);
        intent.putExtra("lastDescription", lastDescription);

        if (id != -1)
        {
            intent.putExtra("id", id);
            setResult(RESULT_OK, intent);
            finish();
        }

    }

    public void getData()
    {
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");

        editTxtUpdateTitle.setText(title);
        editTxtUpdateDescription.setText(description);

    }
}