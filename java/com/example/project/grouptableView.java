package com.example.project;

public class grouptableView {
    String gname,ftopic,gid;



    public String getGname(){
        return gname;
    }
    public void setGname(String gname){
        this.gname=gname;
    }


    public String getFtopic(){

        return ftopic;
    }
    public void setFtopic(String ftopic){

        this.ftopic=ftopic;
    }

    public String getGid(){
        return gid;
    }
    public void setGid(String gid){
        this.gid=gid;
    }


    public grouptableView(String gname,String ftopic,String gid){

        this.gname=gname;
        this.ftopic=ftopic;
        this.gid=gid;
    }
}
