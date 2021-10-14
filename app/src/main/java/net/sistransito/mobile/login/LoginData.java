package net.sistransito.mobile.login;

import java.io.Serializable;

public class LoginData implements Serializable {

    public static final String IDLogin = "login_id";

    private String password;

    public static String getIDLogin() {
        return IDLogin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
