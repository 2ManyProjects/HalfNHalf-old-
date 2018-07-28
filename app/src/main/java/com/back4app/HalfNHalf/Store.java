package com.back4app.HalfNHalf;

import java.util.ArrayList;
import java.util.List;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("Store")
public class Store extends ParseObject {
    public List<Deal> storeDeals = new ArrayList();
    private String ID;

    public void setID(String str) {
        ID = str;
    }

    public String getID() {
        return ID;
    }

    public void addDeal(double rate, String Warning, int amount) {
        Deal temp = new Deal();
        temp.setRate(rate);
        temp.setWarning(Warning);
        temp.setAmount(amount);
        storeDeals.add(temp);
    }

    public void changeDeal(int index, double rate, String Warning, int amount ) {
        Deal temp = storeDeals.get(index);
        temp.setRate(rate);
        temp.setWarning(Warning);
        temp.setAmount(amount);
        storeDeals.set(index, temp);
    }

    public int getsize() {
        return storeDeals.size() + 1;
    }
}
