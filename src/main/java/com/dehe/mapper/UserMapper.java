package com.dehe.mapper;

import com.dehe.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    User selectuser(String name);

    Integer   selectiptable(String ipaddress);
}
