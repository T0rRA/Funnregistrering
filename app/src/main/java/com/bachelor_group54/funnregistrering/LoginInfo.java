package com.bachelor_group54.funnregistrering;

import java.io.Serializable;

public class LoginInfo implements Serializable {
    private String user_name, password;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword(String s) {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
