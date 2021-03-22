package com.dehe.service.impl;

import com.dehe.domain.AdminUser;
import com.dehe.domain.User;
import com.dehe.mapper.AdminMapper;
import com.dehe.model.IPClass;
import com.dehe.model.InMessage;
import com.dehe.service.AdminService;
import com.dehe.utils.CommonUtils;
import com.dehe.utils.JWTUtils;
import com.dehe.utils.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YANGJIAWANG
 * @DATE 2020/12/4
 * @TIME 15:14
 **/
@Service
public class AdminServiceImpl implements AdminService {


    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private RedisClient redisClient;


    @Override
    public void InsertinMessage(InMessage inMessage,int state) {
    adminMapper.InsertinMessage(inMessage,state);
    }

    @Override
    public void deleteallInMessage() {
        adminMapper.deleteallInMessage();
    }



    @Override
    public List<InMessage> selectmessage(String to) {

        List<InMessage> inMessages=adminMapper.selectmessage(to);
    return inMessages;
    }
    @Override
    public void updatemessage(String Addressee, String infrom) {
        adminMapper.updatemessage(Addressee,infrom);
    }
    @Override
    public String findBynameAndPwd(String adminname, String adminpass) {

     AdminUser adminUser =  adminMapper.findBynameandPwd(adminname, CommonUtils.MD5(adminpass));

    if (adminUser==null){
        return null;
    }else {
        String token=JWTUtils.geneJsonWebToken(adminUser);
        adminMapper.updateadmintime(new Date(),adminname);
        return token;
    }
    }

    @Override
    public String selecthead(String username) {

        return adminMapper.selecthead(username);

    }

    @Override
    public String selectip(String ipaddress) {
      IPClass ip =  adminMapper.selectip(ipaddress);
      if (ip==null){
          adminMapper.installip(ipaddress,1);
      }
      if (ip!=null){
        int i;
        i=ip.getFrequency();
        if (i==10){
            adminMapper.deleteuseripaddress(ip.getIpaddress());
            return "次数10";
        }else {
            adminMapper.updateip(ip.getIpaddress());
      }
    }
        return null;
    }
    @Override
    public Integer selectuser(User user) {
        Integer id = null;
        User i = adminMapper.selectuser(user.getUsername(),user.getPhone());
       if (i==null){
           adminMapper.insertuser(user);
         id = user.getId();
       }
       if (i!=null){
           user.setId(i.getId());
         adminMapper.updateuser(user);
           id = i.getId();
       }

    return id;
    }

    @Override
    public void deletealliptable() {
       adminMapper.deletealliptable();
    }

    @Override
    public List<User> selectfrom(Integer oemid,String adminname) {
     adminMapper.updateadmintime(new Date(),adminname);
     return    adminMapper.selectfrom(oemid);
    }

    @Override
    public Integer selectmessagestate(String infrom) {
        return adminMapper.selectmessagestate(infrom);
    }

    @Override
    public void deletaallfrom() {
        adminMapper.deletaallfrom();
    }

    @Override
    public void deleteImessage(String infrom, String Addressee) {
        adminMapper.deleteImessage(infrom,Addressee);
    }

    @Override
    public void deletefrom(String username) {
            adminMapper.deletefrom(username);
    }
}
