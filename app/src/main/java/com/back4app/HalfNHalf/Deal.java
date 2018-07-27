package com.back4app.HalfNHalf;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("Deal")
public class Deal extends ParseObject {
    private double rate;

    public void setRate(double disc){
        put("rate", disc);
    }
    public void setWarning(String str){
        put("Warning", str);
    }
    public void setAmount(int amnt){
        put("Quantity", amnt);
    }
    public double getRate(){
       return getDouble("rate");
    }
    public String getWarning(){
        return getString("Warning");
    }
    public int getAmount(){
        return getInt("Quantity");
    }
}
