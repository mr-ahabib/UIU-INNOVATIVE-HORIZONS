package com.example.project;

public class mentorsTable {
    String name,email,fydp;
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public String getFydp(){
        return fydp;
    }
    public void setFydp(String fydp){
        this.fydp=fydp;
    }
    public mentorsTable(String name,String email,String fydp){
        this.name=name;
        this.email=email;
        this.fydp=fydp;
    }
}
