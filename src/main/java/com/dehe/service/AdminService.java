package com.dehe.service;

import com.dehe.domain.User;
import com.dehe.model.IPClass;
import com.dehe.model.InMessage;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author YANGJIAWANG
 * @DATE 2020/12/4
 * @TIME 14:24
 **/
public interface AdminService {

    void updatemessage(@Param("to")String Addressee,String infrom);

    List<User> selectfrom(Integer oemid,String adminname);

    Integer selectmessagestate(String infrom);


    Integer selectuser(User user);

    String findBynameAndPwd(String adminname, String adminpass);

    void InsertinMessage(InMessage inMessage,int state);

    void deleteallInMessage();

    void deletaallfrom();

    void deletealliptable();

    String selecthead(String username);

    void deletefrom(String username);

    void deleteImessage(String infrom,String Addressee);

    List<InMessage> selectmessage(String to);

    String selectip(String ipaddress);
}
