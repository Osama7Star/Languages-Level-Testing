package com.Alnajim.osama.StarLanguage.QuestionGame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.Alnajim.osama.StarLanguage.R;


public class examLengeth extends AppCompatActivity {


    Button shortExam , longExam ;

    String  WhichLanguage ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examp_lengeth);

        shortExam = (Button)findViewById(R.id.shortExam);
        longExam  =(Button)findViewById(R.id.longExam) ;

        Intent intent1 ;
        intent1 = getIntent() ;
         WhichLanguage = intent1.getStringExtra("WhichLanguage") ;



    }

    public void GoToShortExam(View v )
    {
        Intent intent = new Intent(examLengeth.this, ShowQuestions.class);
        intent.putExtra("examLength","short" ) ;
        intent.putExtra("WhichLanguage",WhichLanguage);
        startActivity(intent);
    }

    public void GoToLongExam(View v )
    {
        Intent intent = new Intent(examLengeth.this,ShowQuestions.class);
        intent.putExtra("examLength","long") ;
        intent.putExtra("WhichLanguage",WhichLanguage);

        startActivity(intent);
    }


    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(examLengeth.this, WhichLanguage.class);
        intent.putExtra("WhichLanguage",WhichLanguage);
        startActivity(intent);
}


}
