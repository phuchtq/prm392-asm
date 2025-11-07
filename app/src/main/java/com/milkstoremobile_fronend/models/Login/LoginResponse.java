package com.milkstoremobile_fronend.models.Login;

public class LoginResponse {
    private String customerId;
    private String userName;
    private String roleName;

    public LoginResponse(String customerId, String userName, String roleName) {
        this.customerId = customerId;
        this.userName = userName;
        this.roleName = roleName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
