package com.hyunix.takenote.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hyunix.takenote.R;

public class AddNoteActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_add_note);
    }
}