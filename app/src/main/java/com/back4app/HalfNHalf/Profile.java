package com.back4app.HalfNHalf;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

@ParseClassName("Profile")
public class Profile extends ParseObject {

    public List<Store> storeList = new ArrayList();
    private String email;
    private String username;
    private int storenum = -1;

    public void init(){
        if(get("storenum") != (int)get("storenum")){
            //TODO  initialize the base variables
            return;
        }else{
            storenum = getInt("storenum");
            for(int i = 0; i < storenum; i++){
                if(getInt(getString(Integer.toString(i))) < 1){
                    buildDeal(i, 0);
                }else{
                    for(int x = 0; x < getInt(getString(Integer.toString(i))); x++){
                        buildDeal(i, x);
                    }
                }
            }
        }
    }

    private void buildDeal(int i, int x){//storenum dealnum
        Store temp = new Store();
        temp.setID(getString(Integer.toString(i)));
        String ratestr = getString(Integer.toString(i)).concat("rate".concat(getString(Integer.toString(x))));
        String warningstr =  getString(Integer.toString(i)).concat("warning".concat(getString(Integer.toString(x))));
        String dealAmnt = getString(Integer.toString(i)).concat("amount".concat(getString(Integer.toString(x))));
        temp.addDeal(getDouble(ratestr), getString(warningstr), getInt(dealAmnt));
        storeList.add(temp);
    }


    public boolean addStore(String ID) {
        if (storeList.size() < 1) {
            Store temp = new Store();
            temp.setID(ID);
            storeList.add(temp);
            return true;
        }else {
            boolean found = false;
            for(int i = 0; i < storeList.size(); i++) {
                if(storeList.get(i).getID().equals(ID)){
                    found = true;
                    break;
                }
            }
            if(found){
                return false;
            }else{
                Store temp = new Store();
                temp.setID(ID);
                storeList.add(temp);
                return true;
            }
        }
    }

    public void setEmail(String str){
        email = str;
    }

    public String getEmail(){
        return email;
    }

    public void setUsername(String str){
        username = str;
    }

    public String getUsername(){
        return username;
    }


}
