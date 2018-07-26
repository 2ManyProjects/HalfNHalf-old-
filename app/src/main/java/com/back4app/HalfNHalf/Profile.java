package com.back4app.HalfNHalf;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("Profile")
public class Profile extends ParseObject {
    private String email = null;
    private String username = null;

    public void setEmail(String str)
    {
        put("email", str);
    }

    public String getEmail()
    {
        return getString("email");
    }

    public void setUsername(String str)
    {
        put("username", str);
    }

    public String getUsername()
    {
        return getString("username");
    }
}
