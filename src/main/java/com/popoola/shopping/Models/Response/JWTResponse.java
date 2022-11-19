package com.popoola.shopping.Models.Response;


import com.popoola.shopping.Models.User;

public class JWTResponse {
    private String token;
    private String type = "Bearer";
    private String account;
    private String name;
    private String role;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public JWTResponse(User user, String token) {
        this.token = token;
        this.account = user.getEmail();
        this.name = user.getUsername();
        this.role = user.getRole();
    }
}
