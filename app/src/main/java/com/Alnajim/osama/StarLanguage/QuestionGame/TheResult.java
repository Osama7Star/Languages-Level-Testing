package com.Alnajim.osama.StarLanguage.QuestionGame;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;

import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.Alnajim.osama.StarLanguage.database.DatabaseHelperWords;
import com.Alnajim.osama.StarLanguage.R;
import com.Alnajim.osama.StarLanguage.adapter.questionAdapter;
import com.Alnajim.osama.StarLanguage.model.Questions;
import com.Alnajim.osama.StarLanguage.sqliteDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.Alnajim.osama.StarLanguage.sqliteDatabase.TABLE_NAME;

public class TheResult extends AppCompatActivity {

    TextView showResult,tvPercentage;
    ProgressBar progressBarResult;
  ////////
    public List<Questions> QuestionsList = new ArrayList<Questions>();
    List<Integer> usersAnswers = new ArrayList<Integer>();
    List<Integer> sectionMark  = new ArrayList<Integer>();
    List<Integer> sectionQuestionNumber  = new ArrayList<>();
    LinearLayout llResult;




    private RecyclerView recyclerView;
    private questionAdapter mAdapter;
    String   WhichLanguage ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_result);

        getNotesCount();
        Bundle bundle  = getIntent().getExtras();
        QuestionsList = (List<Questions>) bundle.getSerializable("QuestionsArray");

        Bundle bundle1  = getIntent().getExtras();
        usersAnswers = (List<Integer>) bundle1.getSerializable("USERS'S ANSWERS");

        Bundle bundle3  = getIntent().getExtras();
        sectionMark = (List<Integer>) bundle3.getSerializable("Section Mark");

        Bundle bundle4  = getIntent().getExtras();
        sectionQuestionNumber = (List<Integer>) bundle4.getSerializable("QuestionNumber");


        /////GET THE RESULT AND THE TIME OF THE EXAM
        Intent intent = getIntent();
        int Theresult = intent.getIntExtra("theResult",0);
        Intent intent1   = getIntent();
        String time      = intent1.getExtras().getString("theTime");
        String sizeOfArray = intent1.getExtras().getString("sizeOfArray");
        int numberOfQuestion = intent1.getExtras().getInt("numberOfQuestion");
        WhichLanguage = intent1.getExtras().getString("WhichLanguage");
        int numberOfTheQuestionForSection = intent1.getExtras().getInt("numberOfTheQuestionForSection");




        recyclerView = (RecyclerView) findViewById(R.id.showquestionrecycleview);
        mAdapter = new questionAdapter(QuestionsList,usersAnswers);
        mAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mAdapter);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    List<sqliteDatabase> questionsIds = new ArrayList<sqliteDatabase>() ;

        showResult   = findViewById(R.id.showResult);
        tvPercentage = findViewById(R.id.tvPercentage);
        progressBarResult = findViewById(R.id.progressBarResult);
        llResult     =  findViewById(R.id.llResult);

        double firstSection  = getPercentage(sectionMark.get(0),numberOfTheQuestionForSection) ;
        double secondSection = getPercentage(sectionMark.get(1),numberOfTheQuestionForSection) ;
        double ThirdSetcion  = getPercentage(sectionMark.get(2),numberOfTheQuestionForSection) ;
        double foruthSetion  = getPercentage(sectionMark.get(3),numberOfTheQuestionForSection) ;

        double yourPercentage = (firstSection+secondSection+ThirdSetcion+foruthSetion )/4;

        showResult.setText(
                "You have :"  +Theresult +" Correct questions of "+numberOfTheQuestionForSection*4
                +"\n Time : " +time+"\n\n"

                +"Your Percentage is : "+ (int)yourPercentage+"%");


        if (yourPercentage>49.99) {
            Log.i("Theresult","Bigger");
            progressBarResult.setProgressDrawable(getResources().getDrawable(R.drawable.customeprogressbar));
        }
        else
        {

            progressBarResult.setProgressDrawable(getResources().getDrawable(R.drawable.customeprogressbar1));

        }

        progressBarResult.setMax(100);
        progressBarResult.setProgress((int)yourPercentage);
        tvPercentage.setText((int)yourPercentage+"%");









    }


    double getPercentage (int correctAnswer, int numQuestion)
    {
        if ( numQuestion!=0)

        return correctAnswer *100 / numQuestion  ;
        else
            return  0;
    }


    public sqliteDatabase getNote(long id) {
        // get readable database as we are not inserting anything
        DatabaseHelperWords d = new DatabaseHelperWords(getBaseContext()) ;

        SQLiteDatabase db = d.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{sqliteDatabase.COLUMN_ID, sqliteDatabase.COLUMN_QUESTIONS_ID},
                sqliteDatabase.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        sqliteDatabase note = new sqliteDatabase(
                cursor.getInt(cursor.getColumnIndex(sqliteDatabase.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(sqliteDatabase.COLUMN_QUESTIONS_ID))
                );

        // close the db connection
        cursor.close();

        return note;
    }



    public int getNotesCount() {
        DatabaseHelperWords d = new DatabaseHelperWords(getBaseContext()) ;
        SQLiteDatabase db1 = d.getWritableDatabase();

        db1.execSQL("delete from "+ TABLE_NAME);




        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = d.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }


    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(TheResult.this, examLengeth.class);
                        intent.putExtra("WhichLanguage",WhichLanguage);
                        startActivity(intent);
//
//        AlertDialog.Builder builder;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            builder = new AlertDialog.Builder(TheResult.this, android.R.style.Theme_Material_Dialog_Alert);
//        } else {
//            builder = new AlertDialog.Builder(TheResult.this);
//        }
//        builder.setTitle("Repeat Exam ")
//                .setMessage("Are you sure you Do Exam Again?")
//                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which)
//                    {
//                        Intent intent = new Intent(TheResult.this,exampLengeth.class);
//                        intent.putExtra("WhichLanguage",WhichLanguage);
//                        startActivity(intent);
//
//                    }
//                })
//                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
//                        System.exit(0);
//
//                    }
//                })
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .show();
    }
}
