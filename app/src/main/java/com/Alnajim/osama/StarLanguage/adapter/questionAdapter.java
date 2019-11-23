package com.Alnajim.osama.StarLanguage.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.Alnajim.osama.StarLanguage.model.Questions;
import com.Alnajim.osama.StarLanguage.R;
import java.util.List;

public class questionAdapter extends RecyclerView.Adapter<questionAdapter.MyViewHolder>
{


    private  List<Questions> questionlist;
    private  List<Integer>   usersAnswers ;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView question, answer1, answer2,answer3,answer4;
        ImageView imageResult ;


        public MyViewHolder(View view) {
            super(view);
            question = (TextView) view.findViewById(R.id.question1);
            answer1  = (TextView) view.findViewById(R.id.answer11);
            answer2  = (TextView) view.findViewById(R.id.answer22);
            answer3  = (TextView) view.findViewById(R.id.answer33);
            answer4  = (TextView) view.findViewById(R.id.answer44);
            imageResult = (ImageView)view.findViewById(R.id.imageResult);

            }
    }


    public questionAdapter(List<Questions> question ,List<Integer> usersAnswers1) {
        this.questionlist = question;
        this.usersAnswers =  usersAnswers1;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.questionlistview, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Questions question = questionlist.get(position);

        holder.question.setText(position+1 +")  "+question.getQuestion());
        holder.answer1.setText(question.getAnswer1());
        holder.answer2.setText(question.getAnswer2());
        holder.answer3.setText(question.getAnswer3());
        holder.answer4.setText(question.getAnswer4());
        int i = questionlist.get(position).getCorreectIns();
        int j = usersAnswers.get(position);
        

            if (i==1)
            {
                holder.answer1.setBackgroundColor(Color.parseColor("#27995C"));
                holder.answer2.setBackgroundColor(Color.parseColor("#747071"));
                holder.answer3.setBackgroundColor(Color.parseColor("#747071"));
                holder.answer4.setBackgroundColor(Color.parseColor("#747071"));
                holder.imageResult.setImageResource(R.drawable.correct);


            }
        else if(i==2)
            {
                holder.answer1.setBackgroundColor(Color.parseColor("#747071"));
                holder.answer2.setBackgroundColor(Color.parseColor("#27995C"));
                holder.answer3.setBackgroundColor(Color.parseColor("#747071"));
                holder.answer4.setBackgroundColor(Color.parseColor("#747071"));
                holder.imageResult.setImageResource(R.drawable.correct);


            }
            else if(i==3)
            {
                holder.answer1.setBackgroundColor(Color.parseColor("#747071"));
                holder.answer2.setBackgroundColor(Color.parseColor("#747071"));
                holder.answer3.setBackgroundColor(Color.parseColor("#27995C"));
                holder.answer4.setBackgroundColor(Color.parseColor("#747071"));
                holder.imageResult.setImageResource(R.drawable.correct);


            }
            else if(i==4)
            {
                holder.answer1.setBackgroundColor(Color.parseColor("#747071"));
                holder.answer2.setBackgroundColor(Color.parseColor("#747071"));
                holder.answer3.setBackgroundColor(Color.parseColor("#747071"));
                holder.answer4.setBackgroundColor(Color.parseColor("#27995C"));
                holder.imageResult.setImageResource(R.drawable.correct);


            }
        if ((j==1)&& (i!=1))
        {
            holder.answer1.setBackgroundColor(Color.parseColor("#ff6861"));
            holder.imageResult.setImageResource(R.drawable.wrong);

        }
     else   if ((j==2)&& (i!=2))
        {            holder.answer2.setBackgroundColor(Color.parseColor("#ff6861"));
                     holder.imageResult.setImageResource(R.drawable.wrong);

        }
   else     if ((j==3)&& (i!=3))
        {
                      holder.answer3.setBackgroundColor(Color.parseColor("#ff6861"));
                     holder.imageResult.setImageResource(R.drawable.wrong);

        }
     else if ((j==4)&& (i!=4))
        {                  holder.answer4.setBackgroundColor(Color.parseColor("#ff6861"));
                           holder.imageResult.setImageResource(R.drawable.wrong);

        }


    }



    @Override
    public int getItemCount() {
        return questionlist.size();
    }
}