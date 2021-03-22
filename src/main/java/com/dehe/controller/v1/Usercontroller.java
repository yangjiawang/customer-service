package com.dehe.controller.v1;

import com.dehe.domain.User;
import com.dehe.service.impl.UserServiceImpl;
import com.dehe.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
public class Usercontroller {

        @Autowired
      private UserServiceImpl userService;

        @GetMapping("/userinformation")
           public JsonData userinformation(String name,Integer userid){
                  User user = userService.selectuser(name);
                  Integer frequency = userService.selectiptable(user.getIpaddress());
                  String status="离线";
                  for (Map.Entry entry:V1ChatRoomController.onlineUser.entrySet()){
                      if (userid.equals(entry.getValue())){
                        status="在线";
                      }else{
                          status="离线";
                      }
                  }
            return  new JsonData(frequency,user,status);
        }
    @PostMapping("/Test")
    public void test(HttpSession session){
        System.out.println(session.getId());
    }
}
