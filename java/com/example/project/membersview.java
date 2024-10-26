package com.example.project;

public class membersview {
    String mname,mid,g_id,memail;
    public String getMname(){
        return mname;
    }
    public void setMname(String mname){
        this.mname=mname;
    }
    public String getMid(){
        return mid;
    }
    public void setMid(String mid){
        this.mid=mid;
    }
    public String getG_id(){
        return g_id;
    }
    public void setGid(String gid){
        this.g_id=gid;
    }
    public String getMemail(){
        return memail;
    }
    public void setMemail(String memail){
        this.memail=memail;
    }
    public membersview(String mname,String mid,String g_id,String memail){
        this.mname=mname;
        this.mid=mid;
        this.g_id=g_id;
        this.memail=memail;
    }


}
