package com.example.a229zzg.nusanswers;

import java.util.ArrayList;

public class UserInfo {
    String userId;
    String username;
    String program;
    ArrayList<Module> currentEnrolledModules;
    ArrayList<Module> completedModules;

    public UserInfo(){
        this.userId = "";
        this.username = "";
        this.program = "";
        this.currentEnrolledModules = new ArrayList<>();
        this.completedModules = new ArrayList<>();
    }

    public UserInfo(String userId, String username) {
        this.userId = userId;
        this.username = username;
        this.program = "";
        this.currentEnrolledModules = new ArrayList<>();
        this.completedModules = new ArrayList<>();
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public void setCurrentEnrolledModules(ArrayList<Module> currentEnrolledModules) {
        this.currentEnrolledModules = currentEnrolledModules;
    }

    public void setCompletedModules(ArrayList<Module> completedModules) {
        this.completedModules = completedModules;
    }
}


