package com.example.a229zzg.nusanswers;

import java.util.ArrayList;

public class UserInfo {
    String userId;
    String username;
    String program;
    ArrayList<Module> currentEnrolledModules;
    ArrayList<Module> completedModules;

    public UserInfo(){

    }

    public UserInfo(String userId, String username) {
        this.userId = userId;
        this.username = username;
        this.program = null;
        this.completedModules = null;
        this.currentEnrolledModules = null;
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


