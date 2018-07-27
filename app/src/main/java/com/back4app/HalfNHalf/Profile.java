package com.back4app.HalfNHalf;



import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

@ParseClassName("Profile")
public class Profile extends ParseObject {

    public List<Store> storeList = new ArrayList();

    public boolean addStore(String ID) {
        if (storeList.size() == 0) {
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
        put("email", str);
    }

    public String getEmail(){
        return getString("email");
    }

    public void setUsername(String str){
        put("username", str);
    }

    public String getUsername(){
        return getString("username");
    }


}
