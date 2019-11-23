package com.Alnajim.osama.StarLanguage.QuestionGame;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.Alnajim.osama.StarLanguage.database.DatabaseHelperWords;
import com.Alnajim.osama.StarLanguage.R;
import com.Alnajim.osama.StarLanguage.model.Questions;
import com.Alnajim.osama.StarLanguage.sqliteDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShowQuestions extends AppCompatActivity implements View.OnClickListener {

    int i = 0 ;
    int score =0;
    TextView question , answer1,answer2 , answer3,answer4,scoreView,showTime,questionsOrder,WelcomToTest;
    TextView   txtAnswer1A,txtAnswer2B,txtAnswer3C,txtAnswer4D;

    LinearLayout linear ;
    LinearLayout  layoutAnswer1,layoutAnswer2,layoutAnswer3,layoutAnswer4;

    private ProgressBar spinner,progressBarQuestion;





    /////////////
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    int Seconds, Minutes, MilliSeconds ;
    Handler handler;

    int numberOfTheQuestionForSection;

    String wholeTime;
    ////////////
    String examLength ;
     String  WhichLanguage ;
    /////

    int jTemp  = 0 ;


    int numberOfQuestion ;
    private DatabaseReference datebasecourse ;

    private Questions questionsclass;

    public List<Questions> QuestionsList     = new ArrayList<Questions>();


    public List<Questions> QuestoinListShwed     = new ArrayList<Questions>();

    public List<Questions>section1     = new ArrayList<>();
    public List<Questions>section1Temp = new ArrayList<>();


    public List<Questions>section2     = new ArrayList<>();
    public List<Questions>section2Temp = new ArrayList<>();

    public List<Questions>section3     = new ArrayList<>();
    public List<Questions>section3Temp = new ArrayList<>();

    public List<Questions>section4     = new ArrayList<>();
    public List<Questions>section4Temp = new ArrayList<>();








    ///ARRAY OF INTEGER TO SEND THE ANSWERS OF USERS
    List<Integer> usersAnswers           = new ArrayList<Integer>();
    List<Integer> sectionMark            = new ArrayList<Integer>();
    List<Integer> sectionQuestionNumber  = new ArrayList<>();
    List<sqliteDatabase>questionShowedBeofre = new ArrayList<sqliteDatabase>() ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showquestion);

        question  =  findViewById(R.id.question);
        answer1   =  findViewById(R.id.answer1);
        answer2   =  findViewById(R.id.answer2);
        answer3   =  findViewById(R.id.answer3);
        answer4   =  findViewById(R.id.answer4);
        txtAnswer1A = findViewById(R.id.txtAnswer1A);
        txtAnswer2B = findViewById(R.id.txtAnswer2B);
        txtAnswer3C = findViewById(R.id.txtAnswer3C);
        txtAnswer4D = findViewById(R.id.txtAnswer4D);
        showTime  =  findViewById(R.id.time);
        scoreView =  findViewById(R.id.score);
        linear    =  findViewById(R.id.test) ;
        layoutAnswer1 = findViewById(R.id.layoutAnswer1);
        layoutAnswer2 = findViewById(R.id.layoutAnswer2);
        layoutAnswer3 = findViewById(R.id.layoutAnswer3);
        layoutAnswer4 = findViewById(R.id.layoutAnswer4);


        questionsOrder = findViewById(R.id.questionsOrder);
        spinner   =  findViewById(R.id.showQuestionProgressbar);
        progressBarQuestion = findViewById(R.id.progressBarQuestion);
        if(!checkInternetStatus())
        Toast.makeText(ShowQuestions.this,"Check Your Internet Connection !",Toast.LENGTH_LONG).show();
        spinner.bringToFront();
        sectionMark.add(0);
        sectionMark.add(0);
        sectionMark.add(0);
        sectionMark.add(0);
        sectionQuestionNumber.add(0);
        sectionQuestionNumber.add(0);
        sectionQuestionNumber.add(0);
        sectionQuestionNumber.add(0);

        // CHECK  IF THERE ARE DATE HAVE BEEN DOWNLOADED FROM THE SERVER OR NOT
        // TO SHOW THE THE

        Intent intent ;
        intent = getIntent() ;
        examLength = intent.getStringExtra("examLength") ;

        Intent intent1 ;
        intent1 = getIntent() ;
         WhichLanguage = intent1.getStringExtra("WhichLanguage") ;
        if (examLength.equals("short"))
            numberOfQuestion = 20 ;
        else if (examLength.equals("long"))
            numberOfQuestion = 40 ;

        else
            numberOfQuestion = 20 ;


            handler = new Handler();

            StartTime = SystemClock.uptimeMillis();

        progressBarQuestion.setMax(numberOfQuestion);
        progressBarQuestion.setProgress(1);


        jTemp =  0 ;



            datebasecourse = FirebaseDatabase.getInstance().getReference("questoins");
            questionShowedBeofre = getAllNotes() ;

            datebasecourse.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot artistSnapshot : dataSnapshot.getChildren())

                    {

                  questionsclass =   artistSnapshot.getValue(Questions.class);
                  String theId   =   artistSnapshot.getKey();
                  questionsclass.setId(theId);

                  /// CHECK FOR GET QUESTIONS ONLY FROM LANGUAGE I WANT
                        if (WhichLanguage.equals(questionsclass.getLanguage()))
                    {
                        /// CHAECK IF QUESTOIN DOSE NOT APPEAR BEFOEE FOR SAME USER IF HE DO A EXAM AGAIN
                        if (checkIfNotFound(questionShowedBeofre,theId))
                        {
//                            QuestionsList.add(questionsclass);
                            if (questionsclass.getSection() == 1)
                            {
                                int temp = sectionQuestionNumber.get(0);
                                temp++;
                                sectionQuestionNumber.set(0, temp++);
                                section1.add(questionsclass);

                            }
                            else if (questionsclass.getSection() == 2)
                            {
                                int temp = sectionQuestionNumber.get(1);
                                temp++;
                                sectionQuestionNumber.set(1, temp++);
                                section2.add(questionsclass);


                            }
                            else if (questionsclass.getSection() == 3)
                            {
                                int temp = sectionQuestionNumber.get(2);
                                temp++;
                                sectionQuestionNumber.set(2, temp++);
                                section3.add(questionsclass);


                            }
                            else if (questionsclass.getSection() == 4)
                            {
                                int temp = sectionQuestionNumber.get(3);
                                temp++;
                                sectionQuestionNumber.set(3, temp++);
                                section4.add(questionsclass);

                            }



                        }
                        else
                        {
                           // QuestionsListTemp.add(questionsclass);
                            if (questionsclass.getSection() == 1)
                            {
                                int temp = sectionQuestionNumber.get(0);
                                temp++;
                                sectionQuestionNumber.set(0, temp++);
                                section1Temp.add(questionsclass);

                            }
                            else if (questionsclass.getSection() == 2)
                            {
                                int temp = sectionQuestionNumber.get(1);
                                temp++;
                                sectionQuestionNumber.set(1, temp++);
                                section2Temp.add(questionsclass);


                            }
                            else if (questionsclass.getSection() == 3)
                            {
                                int temp = sectionQuestionNumber.get(2);
                                temp++;
                                sectionQuestionNumber.set(2, temp++);
                                section3Temp.add(questionsclass);


                            }
                            else if (questionsclass.getSection() == 4)
                            {
                                int temp = sectionQuestionNumber.get(3);
                                temp++;
                                sectionQuestionNumber.set(3, temp++);
                                section4Temp.add(questionsclass);

                            }


                        }

                    }




                    }
                    numberOfTheQuestionForSection = numberOfQuestion / 4 ;
               //     Toast.makeText(ShowQuestions.this,numberOfTheQuestionForSection +"", Toast.LENGTH_SHORT).show();

                    try
                {

                    checkSectionArray(numberOfTheQuestionForSection,section1,section1Temp) ;
                    checkSectionArray(numberOfTheQuestionForSection,section2,section2Temp) ;
                    checkSectionArray(numberOfTheQuestionForSection,section3,section3Temp) ;
                    checkSectionArray(numberOfTheQuestionForSection,section4,section4Temp) ;


                }
                    catch (Exception e)
                    {
                        Toast.makeText(ShowQuestions.this,"There Are Erorr "+e.getMessage(),Toast.LENGTH_LONG).show();
                    }



                    MakeQuestion(QuestionsList, i, usersAnswers, numberOfQuestion);
                    questionsOrder.setText("Question "+ 1 +" Of "+numberOfQuestion/4*4);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        layoutAnswer1.setOnClickListener(this);
        layoutAnswer2.setOnClickListener(this);
        layoutAnswer3.setOnClickListener(this);
        layoutAnswer4.setOnClickListener(this);

        handler.postDelayed(runnable, 0);
            scoreView.setText("Score : " + score);

        showTime.setText( String.format("%02d", Minutes) + ":"
                + String.format("%02d", Seconds));




    }


    @Override
        public void onClick(View view)
        {

          //  handler.postDelayed(runnable, 0);
            switch (view.getId())
            {
                case R.id.layoutAnswer1:


                    if (i<QuestionsList.size())
                        if (QuestionsList.get(i).getCorreectIns()==1)
                        {
                            score++;
                            scoreView.setText("Score : "+score);

                            layoutAnswer1.setBackgroundColor(Color.parseColor("#27995C"));

                            ////////ADD USERS ANSWERS
                            usersAnswers.add(1) ;

                            checkCategoryMark () ;



                        }
                        else
                        {
                            usersAnswers.add(1) ;
                            layoutAnswer1.setBackgroundColor(Color.parseColor("#ff6861"));

                        }
                    if (i<QuestionsList.size())

                        if (i<QuestionsList.size())
                            i++;
                    MakeQuestion(QuestionsList,i,usersAnswers,numberOfQuestion);
                    break;

                case R.id.layoutAnswer2:
                    if (i<QuestionsList.size())

                        if (QuestionsList.get(i).getCorreectIns()==2)
                        {

                            score++;
                            scoreView.setText("Score : "+score);

                            layoutAnswer2.setBackgroundColor(Color.parseColor("#27995C"));
                            usersAnswers.add(2) ;
                            checkCategoryMark () ;



                        }
                        else
                        {
                            layoutAnswer2.setBackgroundColor(Color.parseColor("#ff6861"));
                            usersAnswers.add(2) ;

                        }

                    if (i<QuestionsList.size())
                        i++;

                    MakeQuestion(QuestionsList,i,usersAnswers,numberOfQuestion);


                    break;

                case R.id.layoutAnswer3:
                    if (i<QuestionsList.size())

                        if (QuestionsList.get(i).getCorreectIns()==3)
                        {
                            score++;
                            scoreView.setText("Score : "+score);

                            layoutAnswer3.setBackgroundColor(Color.parseColor("#27995C"));

                            usersAnswers.add(3) ;
                            checkCategoryMark () ;




                        }
                        else
                        {
                            layoutAnswer3.setBackgroundColor(Color.parseColor("#ff6861"));
                            usersAnswers.add(3) ;

                        }
                    if (i<QuestionsList.size())
                        i++;
                    MakeQuestion(QuestionsList,i,usersAnswers,numberOfQuestion);

                    break;

                case R.id.layoutAnswer4:
                    if (i<QuestionsList.size())

                        if (QuestionsList.get(i).getCorreectIns()==4)
                        {
                            score++;
                            scoreView.setText("Score : "+score);

                            layoutAnswer4.setBackgroundColor(Color.parseColor("#27995C"));
                            usersAnswers.add(4) ;
                            checkCategoryMark () ;


                        }
                        else
                        {
                            layoutAnswer4.setBackgroundColor(Color.parseColor("#ff6861"));
                            usersAnswers.add(4) ;

                        }
                    if (i<QuestionsList.size())
                        i++;
                    MakeQuestion(QuestionsList,i,usersAnswers,numberOfQuestion);

                    break;
            }



            if (i==QuestionsList.size()-1)
                return;

        }
        public void MakeQuestion(final List<Questions> QuestionsList, final int i , final List<Integer> usersAnswers1 , final int numberOfQuestion)
        {

            progressBarQuestion.setProgress(i+1);
                if (!question.getText().toString().equals(null))
                    spinner.setVisibility(View.GONE);
                   // spinner.getIndeterminateDrawable().setColorFilter(0xe5101010,android.graphics.PorterDuff.Mode.MULTIPLY);


            final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run()
                    {

                        if (i<numberOfQuestion )
                        {
                            int jTemp = i +1 ;
                            questionsOrder.setText("Questoin "+ jTemp +" Of " +numberOfQuestion/4*4);


                        }
                        if( (i<QuestionsList.size())&& (jTemp<numberOfQuestion))
                        {
                            question.setText(
                                    QuestionsList.get(i).getQuestion());
                            answer1.setText(QuestionsList.get(i).getAnswer1());
                            answer2.setText(QuestionsList.get(i).getAnswer2());
                            answer3.setText(QuestionsList.get(i).getAnswer3());
                            answer4.setText(QuestionsList.get(i).getAnswer4());
                            txtAnswer1A.setText("A )");
                            txtAnswer2B.setText("B )");
                            txtAnswer3C.setText("C )");
                            txtAnswer4D.setText("D )");

                            QuestoinListShwed.add (QuestionsList.get(i));
                           if (checkIfNotFound(questionShowedBeofre,QuestionsList.get(i).getId()))
                           {
                               insertNote(QuestionsList.get(i).getId());
                           }



                            layoutAnswer1.setBackgroundColor(Color.parseColor("#484344"));
                            layoutAnswer2.setBackgroundColor(Color.parseColor("#484344"));
                            layoutAnswer3.setBackgroundColor(Color.parseColor("#484344"));
                            layoutAnswer4.setBackgroundColor(Color.parseColor("#484344"));

                            jTemp++;


                        }
                        else
                        {
                            answer1.setClickable(false);
                            answer2.setClickable(false);
                            answer3.setClickable(false);
                            answer4.setClickable(false);

                            /////////////////////
                            //////SEND QUESTIONS
                            /////////////////////
                            Intent intent = new Intent(ShowQuestions.this, TheResult.class);
                            Bundle bundel = new Bundle();
                            bundel.putSerializable("QuestionsArray", (Serializable) QuestoinListShwed);
                            intent.putExtras(bundel);


                            /////////////////////
                            //////SEND USER ANSWERS
                            /////////////////////
                            Bundle bunde2 = new Bundle();
                            bunde2.putSerializable("USERS'S ANSWERS", (Serializable) usersAnswers1);
                            intent.putExtras(bunde2);

                            Bundle bunde3 = new Bundle();
                            bunde3.putSerializable("Section Mark", (Serializable) sectionMark);
                            intent.putExtras(bunde3);


                            Bundle bunde4 = new Bundle();
                            bunde4.putSerializable("QuestionNumber", (Serializable) sectionQuestionNumber);
                            intent.putExtras(bunde4);


                            //////////SEND USERS SCORE
                            intent.putExtra("theResult", score);
                            intent.putExtra("theTime", wholeTime);
                            intent.putExtra("numberOfQuestion",numberOfQuestion);
                            intent.putExtra("WhichLanguage",WhichLanguage);

                            intent.putExtra("numberOfTheQuestionForSection",numberOfTheQuestionForSection);



                            intent.putExtra("sizeOfArray",questionShowedBeofre.size());

                            startActivity(intent);


                        }
                    }
                }, 1000);



        }


        void checkCategoryMark ()
        {
            if ((QuestionsList.get(i).getSection()==1))
            {
                int grade =   sectionMark.get(0);
                grade++;
                sectionMark.set(0,grade);

            }
            else   if ((QuestionsList.get(i).getSection()==2))
            {
                int grade =   sectionMark.get(1);
                grade++ ;
                sectionMark.set(1,grade);

            }
            else   if ((QuestionsList.get(i).getSection()==3))
            {
                int grade =   sectionMark.get(2);
                grade++ ;
                sectionMark.set(2,grade);

            }
            else   if ((QuestionsList.get(i).getSection()==4))
            {
                int grade =   sectionMark.get(3);
                grade++ ;

                sectionMark.set(3,grade);

            }
        }




/////////////////////
/// INSERT THE QUESTOINS TO THE DATABASE
///////////////////
    public long insertNote(String questionId) {
        // get writable database as we want to write data

        DatabaseHelperWords d = new DatabaseHelperWords(getApplicationContext()) ;
        SQLiteDatabase db = d.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(sqliteDatabase.COLUMN_QUESTIONS_ID, questionId);

        // insert row
        long id = db.insert(sqliteDatabase.TABLE_NAME, null, values);

        // close db connection
        db.close();
        // return newly inserted row id
        return id;
    }



    public List<sqliteDatabase> getAllNotes() {
        List<sqliteDatabase> notes = new ArrayList<>();
        DatabaseHelperWords d = new DatabaseHelperWords(getBaseContext()) ;

        // Select All Query
        String selectQuery = "SELECT  * FROM " + sqliteDatabase.TABLE_NAME + " ORDER BY " +
                sqliteDatabase.COLUMN_QUESTIONS_ID + " DESC";

        SQLiteDatabase db = d.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                sqliteDatabase note = new sqliteDatabase();
                note.setId(cursor.getInt(cursor.getColumnIndex(sqliteDatabase.COLUMN_ID)));
                note.setQuestionId(cursor.getString(cursor.getColumnIndex(sqliteDatabase.COLUMN_QUESTIONS_ID)));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list

        return notes;
    }

    public boolean checkIfNotFound (List<sqliteDatabase>questionShowedBeofre , String questionId )

    {

        for (int i = 0 ; i <questionShowedBeofre.size() ; i ++)
            if (questionShowedBeofre.get(i).getQuestionId().equals(questionId))
                return false  ;

        return  true;
    }


    void checkSectionArray(int numberOfTheQuestionForSection , List<Questions> arr, List<Questions> arrtemp)
    {
        Collections.shuffle(arr);
        Collections.shuffle(arrtemp);
   //Toast.makeText(ShowQuestions.this,"Main " +arr.size() +" Temp "+arrtemp.size() , Toast.LENGTH_SHORT).show();

        if (arr.size()>=numberOfTheQuestionForSection )
        {
            for (int i = 0; i<arr.size() ; i++)
            {
                QuestionsList.add(arr.get(i));
//                QuestionsList.addAll(arr);
            }


        }
        else
            {
            int temp = numberOfTheQuestionForSection - arr.size() ;

            for (int i =0  ;i <temp ; i++)
            {
                QuestionsList.add(arrtemp.get(i));

            }

        }



    }


    Boolean checkInternetStatus ()
    {
        ConnectivityManager cm =
                (ConnectivityManager)getApplicationContext().getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected ;
    }

  public  Runnable runnable = new Runnable()
    {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int) (UpdateTime % 1000);

            wholeTime =Minutes+" Minutes :"+Seconds+" Seconds"  ;


            showTime.setText( String.format("%02d", Minutes) + ":"
                    + String.format("%02d", Seconds));
            handler.postDelayed(this, 0);
        }




    };
}



