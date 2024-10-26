
package com.example.project;

public class counsellingView {
    String day,Start,End;
    public String getDay(){
        return day;
    }
    public void setDay(String day){
        this.day=day;
    }
    public String getStart(){
        return Start;
    }
    public void setStart(String Start){
        this.Start=Start;
    }
    public String getEnd(){
        return End;
    }

    public void setEnd(String End) {
        this.End = End;
    }



    public counsellingView(String day,String Start,String End){
        this.day=day;
        this.Start=Start;
        this.End=End;
    }
}
