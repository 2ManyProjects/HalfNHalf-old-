package com.back4app.HalfNHalf;

import android.content.Context;
import android.support.annotation.Nullable;

import com.parse.GetDataCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

@ParseClassName("Profile")
public class Profile extends ParseObject {

    public List<Store> storeList = new ArrayList();
    private String email;
    private String username;
    private int storenum = 0;
    public String fileStream;

    public void init(byte[] data, Context context){
        fileStream = new String(data);

        /*email = this.getString("email");
        username = this.getString("username");
        if(getInt("storenum") == 0){
            //TODO  initialize the base variables
            storenum = 0;
            this.put("storenum", storenum);

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
        }*/
    }

    public String getFileStream(){
        return fileStream;
    }

    private void buildDeal(int i, int x){//storenum dealnum
        Store temp = new Store();
        temp.setID(getString(Integer.toString(i)));
        String ratestr = getString(Integer.toString(i)).concat("rate".concat(getString(Integer.toString(x))));
        String warningstr =  getString(Integer.toString(i)).concat("warning".concat(getString(Integer.toString(x))));
        String dealAmnt = getString(Integer.toString(i)).concat("amount".concat(getString(Integer.toString(x))));
        temp.addDeal(getDouble(ratestr), getString(warningstr), getInt(dealAmnt), storenum);
        storeList.add(temp);
    }

//    public void create(Context context) {
//        String fileName = username + "profile";
//        try {
//            outputStream = context.openFileOutput(fileName, context.MODE_PRIVATE);
//        }catch (Exception e){
//            Displayer.toaster("No file Found, Creating Profile", "2", context);
//        }
//
//    }

    public String addStore(String ID) {
        if (storeList.size() < 1) {
            Store temp = new Store();
            temp.setID(ID);
            storeList.add(temp);
            storenum += 1;
            this.put("storenum", storenum);
            this.put(Integer.toString(storenum), ID);
            this.saveInBackground();
            return "Store was Added, First store";
        }else {
            boolean found = false;
            for(int i = 0; i < storeList.size(); i++) {
                if(storeList.get(i).getID().equals(ID)){
                    found = true;
                    break;
                }
            }
            if(found){
                return "Store ID was already added";
            }else{
                Store temp = new Store();
                temp.setID(ID);
                storeList.add(temp);
                storenum += 1;
                this.put("storenum", storenum);
                this.put(Integer.toString(storenum), ID);
                this.saveInBackground();
                return "Store was Added (more than 1 store was found)";
            }
        }
    }

    public void setEmail(String str){
        email = str;
        put("email", str);
        this.saveInBackground();
    }

    public String getEmail(){
        return email;
    }

    public void setUsername(String str){
        username = str;
        put("username", str);
        this.saveInBackground();
    }

    public String getUsername(){
        return username;
    }


}
