package com.example.a229zzg.nusanswers;

public class Module {
    private String Code;
    private String Description;

    public Module() {

    }

    public Module(String code,String description){
        this.Code = code;
        this.Description = description;
    }

    public String getCode() {
        return Code;
    }

    public String getDescription() {
        return Description;
    }
}
