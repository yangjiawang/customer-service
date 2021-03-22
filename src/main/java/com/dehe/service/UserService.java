package com.dehe.service;

import com.dehe.domain.User;

public interface UserService {

    User selectuser(String name);

    Integer   selectiptable(String ipaddress);
}
