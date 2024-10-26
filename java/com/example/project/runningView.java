package com.example.project;

public class runningView {

    String gid,fydp,day,complete;

    public String getGid(){
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getFydp() {
        return fydp;
    }

    public void setFydp(String fydp) {
        this.fydp = fydp;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public runningView(String gid,String fydp,String day,String complete){
        this.gid=gid;
        this.fydp=fydp;
        this.day=day;
        this.complete=complete;
    }
}
