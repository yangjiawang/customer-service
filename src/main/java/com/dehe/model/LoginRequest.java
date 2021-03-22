package com.dehe.model;

/**
 * @author YANGJIAWANG
 * @DATE 2020/12/4
 * @TIME 14:14
 **/
public class LoginRequest {
    private String adminname;

    private String adminpass;

    public String getAdminname() {
        return adminname;
    }

    public void setAdminname(String adminname) {
        this.adminname = adminname;
    }

    public String getAdminpass() {
        return adminpass;
    }

    public void setAdminpass(String adminpass) {
        this.adminpass = adminpass;
    }
}
