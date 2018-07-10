package com.example.a229zzg.nusanswers;

import java.util.ArrayList;

public class UserInfo {
    String UserId;
    String Username;
    String Program;
    ArrayList<Module> CurrentEnrolledModules;
    ArrayList<Module> CompletedModules;

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

    public void setCurrentEnrolledModules(ArrayList<Module> currentEnrolledModules) {
        CurrentEnrolledModules = currentEnrolledModules;
    }

    public void setCompletedModules(ArrayList<Module> completedModules) {
        CompletedModules = completedModules;
    }
}


