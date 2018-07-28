package com.back4app.HalfNHalf;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("Deal")
public class Deal extends ParseObject {
    private double rate;
    private String Warning;
    private int Amount;

    public void setRate(double disc){
        rate = disc;
    }
    public void setWarning(String str){
        Warning = str;
    }
    public void setAmount(int amnt){
        Amount = amnt;
    }
    public double getRate(){
       return rate;
    }
    public String getWarning(){
        return Warning;
    }
    public int getAmount(){
        return Amount;
    }
}
