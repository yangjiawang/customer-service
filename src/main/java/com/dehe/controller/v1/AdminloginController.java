package com.dehe.controller.v1;

import com.dehe.domain.User;
import com.dehe.model.LoginRequest;
import com.dehe.service.AdminService;
import com.dehe.service.impl.AdminServiceImpl;
import com.dehe.utils.JWTUtils;
import com.dehe.utils.JsonData;
import com.dehe.utils.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author YANGJIAWANG
 * @DATE 2020/11/30
 * @TIME 15:08
 * 管理员登录接口
 **/
@RestController
@RequestMapping("/admin")
public class AdminloginController {

    @Autowired
    RedisClient redisClient;

    @Autowired
    private AdminServiceImpl adminService;

   @PostMapping("/login.do")
    public JsonData Login(LoginRequest loginRequest, HttpSession session){

        String ToKen = adminService.findBynameAndPwd(loginRequest.getAdminname(),loginRequest.getAdminpass());


       return ToKen == null ?JsonData.buildError("登录失败，账号密码错误"): JsonData.buildSuccess(ToKen);
        }


    @GetMapping("selectstatus")
    public JsonData selectstatus(Integer oemid){
        String status = "";
       if (oemid==0){
         status = redisClient.get("adminname");
       }
       else {
           status = redisClient.get("admin");
       }
       if (status!=null){
        return JsonData.buildSuccess("在线",0);
       }else {
           return JsonData.buildError("离线",-1);
       }
    }




}
