package com.example.project;

public class myGroups {
    String gid,gname;

    public String getGid(){
        return gid;
    }
    public void setGid(String gid){
        this.gid=gid;
    }
    public String getGname(){
        return gname;
    }
    public void setGname(String gname){
        this.gname=gname;
    }
    public myGroups(String gid,String gname){
        this.gid=gid;
        this.gname=gname;
    }
}
