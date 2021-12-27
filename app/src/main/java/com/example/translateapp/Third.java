package com.example.translateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Third extends AppCompatActivity {
    private TextView word , meaning;
    ImageButton btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        init();
        btnBack = findViewById(R.id.buttonBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentback = new Intent(Third.this,Second.class);
                startActivity(intentback);
            }


        });


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