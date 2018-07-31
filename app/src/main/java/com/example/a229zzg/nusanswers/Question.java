package com.example.a229zzg.nusanswers;

public class Question {
    String content;
    String filter;
    String year;
    String sem;
    String uid;

    public Question(String content, String filter, String year, String sem, String uid) {
        this.content = content;
        this.filter = filter;
        this.year = year;
        this.sem = sem;
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}