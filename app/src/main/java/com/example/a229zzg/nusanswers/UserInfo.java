package com.example.a229zzg.nusanswers;

import java.util.ArrayList;

public class UserInfo {
    private String userId;
    private String username;
    private String program;
    private ArrayList<Module> currentEnrolledModules;
    private ArrayList<Module> completedModules;

    public UserInfo() {

    }

    public UserInfo(String userId, String username) {
        this.userId = userId;
        this.username = username;
        this.program = "";
        this.currentEnrolledModules = new ArrayList<>();
        this.completedModules = new ArrayList<>();
    }
    public String getUsername() {
        return this.username;
    }
    public void setProgram(String program) {
        this.program = program;
    }

    public String getProgram() {
        return this.program;
    }
    public void setCurrentEnrolledModules(ArrayList<Module> currentEnrolledModules) {
        this.currentEnrolledModules = currentEnrolledModules;
    }

    public ArrayList<Module> getCurrentEnrolledModules() {
        return this.currentEnrolledModules;
    }

    public void setCompletedModules(ArrayList<Module> completedModules) {
        this.completedModules = completedModules;
    }

    public ArrayList<Module> getCompletedModules() {
        return this.completedModules;
    }
}



