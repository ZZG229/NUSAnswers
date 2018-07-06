package com.example.a229zzg.nusanswers;

import java.util.ArrayList;

public class UserInfo {
    String UserId;
    String Username;
    String Program;
    ArrayList<String> CurrentEnrolledModules;
    ArrayList<String> CompletedModules;

    public UserInfo(){

    }

    public UserInfo(String userId, String username) {
        UserId = userId;
        Username = username;
        Program = null;
        CompletedModules = null;
        CurrentEnrolledModules = null;
    }

    public void setProgram(String program) {
        Program = program;
    }

    public void setCurrentEnrolledModules(ArrayList<String> currentEnrolledModules) {
        CurrentEnrolledModules = currentEnrolledModules;
    }

    public void setCompletedModules(ArrayList<String> completedModules) {
        CompletedModules = completedModules;
    }
}


