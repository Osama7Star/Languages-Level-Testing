package com.Alnajim.osama.StarLanguage.QuestionGame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.Alnajim.osama.StarLanguage.model.Questions;
import com.Alnajim.osama.StarLanguage.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class getQuestions extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener{

    int postionOfCategory ;
    String item1;
    String language ;

    Questions q ;

    ///////////////
    EditText question,answer1,answer2,answer3,answer4;
    TextView getquestionTextview ;
    Button addQuestion ;
    ////////////////
    Spinner correctAnswerSpinner;
    Spinner categorySpinner ;
    final List<String> list = new ArrayList<String>();


    final List<String> listCategory = new ArrayList<String>();


    private DatabaseReference datebasecourse ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_questions);



        ///////////////////////
        question = (EditText)findViewById(R.id.questionText) ;
        answer1  = (EditText)findViewById(R.id.addAnswer1) ;
        answer2  = (EditText)findViewById(R.id.addAnswer2) ;
        answer3  = (EditText)findViewById(R.id.addAnswer3) ;
        answer4  = (EditText)findViewById(R.id.addAnswer4) ;
        getquestionTextview = (TextView)findViewById(R.id.getquestionTextview);
        ///////////////////
        //// RECIVE FROM WHICH LANGAUGE
        Intent intent ;
        intent = getIntent() ;
        String whichLanguage = intent.getStringExtra("WhichLanguage") ;

        if (whichLanguage.equals("addEnglishQuestion"))
        {
            getquestionTextview.setText("ADD Question In English Language");
            language ="English" ;
        }
        else if (whichLanguage.equals("addTurkishQuestion"))
        {
            getquestionTextview.setText("ADD Question In Turkish Language");
            language ="Turkish";

        }
        /// 11 / 26 /2018
        //////////////////
        addQuestion =(Button)findViewById(R.id.addQuestion) ;

        correctAnswerSpinner = (Spinner)findViewById(R.id.correctAnswerSpinner) ;
        categorySpinner      = (Spinner)findViewById(R.id.showCategorySpinner) ;

        List<String > correctAnswer =  new ArrayList<String>( );
        correctAnswer.add("1");
        correctAnswer.add("2");
        correctAnswer.add("3");
        correctAnswer.add("4");

  ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, correctAnswer);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        correctAnswerSpinner.setAdapter(dataAdapter);
        correctAnswerSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        /////////////////////////////////////
        //// SPINNER FOR QUESTION CATEGORY
        ////////////////////////////////////

        List<String > categorySpinnerList =  new ArrayList<String>( );

        categorySpinnerList.add("conversation");
        categorySpinnerList.add("reading");
        categorySpinnerList.add("listening");
        categorySpinnerList.add("grammar");

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categorySpinnerList);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(dataAdapter1);
        categorySpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);






        addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)

            {
            final String questionT= question.getText().toString();
            final String answer1T = answer1.getText().toString() ;
            final String answer2T = answer2.getText().toString() ;
            final String answer3T = answer3.getText().toString() ;
            final String answer4T = answer4.getText().toString() ;

                correctAnswerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        Object item = parent.getItemAtPosition(pos);
                        getPostion(pos);
                        q = new Questions("",questionT, answer1T, answer2T, answer3T, answer4T, pos,0,language);


                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        Object item = parent.getItemAtPosition(pos);
                        getPostion(pos);

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                ///FOR CONNECT TO DATEBASE
                datebasecourse = FirebaseDatabase.getInstance().getReference("questoins");

                String id = datebasecourse.push().getKey();
                Questions q = new Questions(id,questionT, answer1T, answer2T, answer3T, answer4T, correctAnswerSpinner.getSelectedItemPosition()+1,categorySpinner.getSelectedItemPosition()+1,language);

                datebasecourse.child(id).setValue(q);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getQuestions.this,"Your Questoin Added SUCCESSFULY",Toast.LENGTH_SHORT ).show();
                    }
                }, 200);
                finish();

            }
        });




    }

    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        item1 = parent.getItemAtPosition(position).toString();
        postionOfCategory =position ;

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    public int getPostion (int postion ){return postion;}


}

