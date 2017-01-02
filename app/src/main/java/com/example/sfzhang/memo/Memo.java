package com.example.sfzhang.memo;

import org.litepal.crud.DataSupport;

/**
 * Created by sf Zhang on 2016/12/20.
 */
public class Memo extends DataSupport {
    private int num;
    private int tag;
    private String textDate;
    private String textTime;
    private String alarm;
    private String mainText;
    private int id;

    //getter
    public int getNum(){
        return num;
    }
    public int getTag(){
        return tag;
    }
    public String getTextDate(){
        return textDate;
    }
    public String getTextTime(){
        return textTime;
    }
    public String getAlarm(){
        return alarm;
    }
    public String getMainText(){
        return mainText;
    }
    public int getId() { return id; }

    //setter
    public void setNum(int num) {
        this.num=num;
    }
    public void setTag(int tag){
        this.tag=tag;
    }
    public void setTextDate(String textDate){
        this.textDate=textDate;
    }
    public void setTextTime(String textTime){
        this.textTime=textTime;
    }
    public void setAlarm(String alarm){
        this.alarm=alarm;
    }
    public void setMainText(String mainText){
        this.mainText=mainText;
    }
    public void setId(int id){ this.id=id; }
}
