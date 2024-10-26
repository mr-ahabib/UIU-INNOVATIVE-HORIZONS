package com.example.project;

public class createdView {
    String email,day,fydp;

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public  String getDay(){
        return day;
    }
    public void setDay(String day){
        this.day=day;
    }
    public String getFydp(){
        return fydp;
    }
    public void setFydp(String fydp){
        this.fydp=fydp;
    }
    public createdView(String email,String day,String fydp){
        this.email=email;
        this.day=day;
        this.fydp=fydp;
    }

}
