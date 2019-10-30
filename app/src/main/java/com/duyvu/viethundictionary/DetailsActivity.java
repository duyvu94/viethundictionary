package com.duyvu.viethundictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.duyvu.viethundictionary.models.Word;

public class DetailsActivity extends AppCompatActivity {

    public final static String TAG ="com.duyvu.viethundictionary.details";

    private Word word;
    private TextView wordTv, typeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        word = (Word) getIntent().getSerializableExtra(TAG);
        wordTv = findViewById(R.id.text_word);
        typeTv = findViewById(R.id.type);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(word.word);

        wordTv.setText(word.description);
        typeTv.setText(word.type.toString());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



}
