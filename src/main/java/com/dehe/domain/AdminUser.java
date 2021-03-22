package com.dehe.domain;

import java.io.Serializable;

/**
 * @author YANGJIAWANG
 * @DATE 2020/11/30
 * @TIME 14:49
 **/
public class AdminUser implements Serializable {
    private  String adminname;
    private  String adminpass;
    private  String headimg;

    public AdminUser(String adminname) {
        this.adminname = adminname;
    }

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

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }
}
