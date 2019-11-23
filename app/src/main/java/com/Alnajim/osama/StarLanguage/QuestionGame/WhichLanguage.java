package com.Alnajim.osama.StarLanguage.QuestionGame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.Alnajim.osama.StarLanguage.R;


public class WhichLanguage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);



    }
    public void GoToEnglish(View v)
    {
        Intent intent = new Intent(WhichLanguage.this ,examLengeth.class) ;
        intent.putExtra("WhichLanguage", "English" ) ;
        startActivity(intent);
    }

    public void GoToTurkish(View v)
    {
        Intent intent = new Intent(WhichLanguage.this , examLengeth.class) ;
        intent.putExtra("WhichLanguage", "Turkish") ;
        startActivity(intent);
    }

    public void addQuestionbutton(View v)
    {
        Intent intent = new Intent(WhichLanguage.this , addQuestionLanguage.class) ;
        startActivity(intent);
    }



}
