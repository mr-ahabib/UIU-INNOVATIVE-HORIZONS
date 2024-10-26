package com.example.project;

public class requestView {
    String name,id,gid,fydp,day;
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id=id;
    }
    public String getGid(){
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getFydp() {
        return fydp;
    }

    public void setFydp(String fydp)
    {
        this.fydp = fydp;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
    public requestView(String name,String id,String gid,String fydp,String day){
        this.name=name;
        this.id=id;
        this.gid=gid;
        this.fydp=fydp;
        this.day=day;
    }
}
