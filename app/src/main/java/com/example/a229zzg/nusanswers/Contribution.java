package com.example.a229zzg.nusanswers;

public class Contribution {
    String username;
    String answer;

    public Contribution(String username, String answer) {
        this.username = username;
        this.answer = answer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
