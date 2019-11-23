package com.Alnajim.osama.StarLanguage.QuestionGame;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.Alnajim.osama.StarLanguage.R;


public class addQuestionLanguage extends AppCompatActivity {


    Button addTurkishQuestion , addEnglishQuestion ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question_language);

        addTurkishQuestion = (Button)findViewById(R.id.addTurkishQuestion) ;
        addEnglishQuestion = (Button)findViewById(R.id.addEnglishQuestion) ;



    }

    public void addEnglishQuestion(View v )
    {
        Intent intent = new Intent(addQuestionLanguage.this, getQuestions.class);
        intent.putExtra("WhichLanguage","addEnglishQuestion");
        startActivity(intent);
    }
    public void addTurkishQuestion(View v )
    {
        Intent intent = new Intent(addQuestionLanguage.this,getQuestions.class);
        intent.putExtra("WhichLanguage","addTurkishQuestion");
        startActivity(intent);
    }



}
