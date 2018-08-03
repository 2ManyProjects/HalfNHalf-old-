package com.back4app.HalfNHalf;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("Deal")
public class Deal extends ParseObject {
    private double rate;
    private String Warning;
    private int Amount;

    public void setRate(double disc, String ID){
        rate = disc;
        put(ID.concat("rate".concat(getString(ID))), rate);
    }
    public void setWarning(String str, String ID){
        Warning = str;
        put(ID.concat("Warning".concat(getString(ID))), Warning);
    }
    public void setAmount(int amnt, String ID){
        Amount = amnt;
        put(ID.concat("Amount".concat(getString(ID))), Amount);
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
