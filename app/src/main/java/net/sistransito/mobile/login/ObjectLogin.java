package net.sistransito.mobile.login;

public class ObjectLogin {

    private static LoginData loginData;

    private ObjectLogin() {
        loginData = new LoginData();
    }

    public static LoginData getLoginData() {
        return loginData;
    }

    public static void setLoginData(LoginData data) {
        loginData = data;
    }

}
