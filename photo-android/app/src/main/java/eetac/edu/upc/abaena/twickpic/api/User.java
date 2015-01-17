package eetac.edu.upc.abaena.twickpic.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alejandro on 15/01/2015.
 */
public class User {

    private String username;
    private String password;
    private int avatar;
    private boolean RegisterSuccessful;
    private Map<String, Link> links = new HashMap<String, Link>();

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public boolean isRegisterSuccessful() {
        return RegisterSuccessful;
    }

    public void setRegisterSuccessful(boolean registerSuccessful) {
        RegisterSuccessful = registerSuccessful;
    }


}
