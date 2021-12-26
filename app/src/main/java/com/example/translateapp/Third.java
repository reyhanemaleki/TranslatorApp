package com.example.translateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Third extends AppCompatActivity {
    private TextView word , meaning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        init();

    }
    private void init(){
        word = findViewById(R.id.word);
        meaning = findViewById(R.id.meaning);
        String title =getIntent().getStringExtra("title");
        String text =getIntent().getStringExtra("text");
        String titleEn =getIntent().getStringExtra("titleEn");
        word.setText(title);
        meaning.setText(titleEn +"\n"+text);
    }
}