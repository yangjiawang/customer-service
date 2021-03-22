package com.dehe.mapper;

import com.dehe.domain.AdminUser;
import com.dehe.domain.User;
import com.dehe.model.IPClass;
import com.dehe.model.InMessage;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author YANGJIAWANG
 * @DATE 2020/12/4
 * @TIME 14:55
 **/
@Repository
public interface AdminMapper {

    AdminUser findBynameandPwd(@Param("adminname") String adminname,@Param("adminpass") String adminpass);

    Integer insertuser(User user);

    User selectuser(String username,String phone);

    void updatemessage(@Param("to")String Addressee,String infrom);

    Integer updateuser(User user);

    void deletealliptable();

    void deleteuseripaddress(String ipaddress);

    void InsertinMessage(@Param("inMessage")InMessage inMessage,@Param("state")int state);

    void deleteallInMessage();

    void deletaallfrom();

    void deletefrom(@Param("username") String username);

    void deleteImessage(String infrom,String Addressee);

    String selecthead(String username);

    List<User> selectfrom(Integer oemid);

    void updateadmintime(@Param("time") Date time,@Param("name")String name);

    Integer selectmessagestate(String infrom);

   List<InMessage> selectmessage(String to);

    IPClass selectip(String ipaddress);

    void updateip(String ipaddress);

   void installip(String ipaddress,int frequency);


}
