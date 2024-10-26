package com.example.project;

public class gRequestView {
    String mname,mid,memail,status;

    public String getMname(){
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMemail() {
        return memail;
    }

    public void setMemail(String memail) {
        this.memail = memail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public gRequestView(String mname,String mid,String memail,String status){
        this.mname=mname;
        this.mid=mid;
        this.memail=memail;
        this.status=status;
    }

}
