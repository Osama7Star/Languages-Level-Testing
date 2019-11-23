package com.Alnajim.osama.StarLanguage.model;

import java.io.Serializable;

public class Questions implements Serializable
{
    private String question ,answer1,answer2,answer3,answer4,language;
    private int correectIns,section;
    String id;




    public Questions(String id , String question, String answer1, String answer2, String answer3, String answer4, int correectIns, int section, String language) {
        this.id       = id ;
        this.question = question;
        this.answer1  = answer1;
        this.answer2  = answer2;
        this.answer3  = answer3;
        this.answer4  = answer4;
        this.correectIns = correectIns;
        this.section  = section ;
        this.language = language;


    }

    public Questions()
    {}

    public String getQuestion() {
        return question;
    }

    public String getAnswer1() {
        return answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public int getCorreectIns() {
        return correectIns;
    }

    public String getLanguage() { return language; }

    public int getSection()   {  return section;}
    public String getId(){return  id ; }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public void setCorreectIns(int correectIns) {
        this.correectIns = correectIns;
    }

    public void setSection(int section) {this.section = section;}
    public  void setId(String id ){this.id=id;}



    public void setLanguage(String language) {
        this.language = language;
    }

}