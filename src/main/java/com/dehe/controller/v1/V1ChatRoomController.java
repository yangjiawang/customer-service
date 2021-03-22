package com.dehe.controller.v1;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.dehe.domain.AdminUser;
import com.dehe.domain.User;
import com.dehe.model.InMessage;
import com.dehe.service.WebSocketService;
import com.dehe.service.impl.AdminServiceImpl;

import com.dehe.utils.JsonData;
import com.dehe.utils.JsonUtils;
import com.dehe.utils.RedisClient;
import com.dehe.wxpush.WxPush;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSONObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * @author YANGJIAWANG
 * @DATE 2020/11/10
 * @TIME 10:12
 **/

@RestController
@RequestMapping("admin")
public class V1ChatRoomController {

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private WebSocketService ws;

    private static final String Imgsrc = "/usr/local/customer-service-img/images/";

    private static final  String address="http://182.92.90.129/images/";

    public static Map<String,Integer> onlineUser = new HashMap<>();

    @RequestMapping("ip")
    public  JsonData ipselect(String ip){
       String state = adminService.selectip(ip);
        if ("次数10".equals(state)){
            return JsonData.buildSuccess(404);
        }
     return    JsonData.buildSuccess(200);
    }

    @RequestMapping("daili")
    public  JsonData openid(String code){
        System.out.println("运行中");

            String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxadbe080ec382214d&secret=c47e13847d71fb2821b77df28fcd5c1b&code="+code+"&grant_type=authorization_code";
            String openId="";
            try {
                URL getUrl=new URL(url);
                HttpURLConnection http=(HttpURLConnection)getUrl.openConnection();
                http.setRequestMethod("GET");
                http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                http.setDoOutput(true);
                http.setDoInput(true);
                http.connect();
                InputStream is = http.getInputStream();
                int size = is.available();
                byte[] b = new byte[size];
                is.read(b);
                String message = new String(b, "UTF-8");
                JSONObject json = JSONObject.parseObject(message);
                openId = json.getString("openid");
                System.out.println(openId);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
            }
        System.out.println(openId);
            return JsonData.buildSuccess(openId);

    }
    /**
    * 消息路由
    * */
    @MessageMapping("/v1/single/chat")
    public void singleChat(InMessage inMessage){
         ws.sendChatMessage(inMessage);


       if (inMessage.getInfrom().equals("root")){
            adminService.InsertinMessage(inMessage,0);
        }else if (!inMessage.getInfrom().equals("root")){
           adminService.InsertinMessage(inMessage,1);
       }else if (!inMessage.getInfrom().equals("admin")){
            adminService.InsertinMessage(inMessage,1);
        }
        if (inMessage.getAddressee().equals("root")){
            WxPush.push(inMessage);
        }
        }

        @RequestMapping("head")
        public JsonData getheea(String from){

           return JsonData.buildSuccess(adminService.selecthead(from));
        }
     /**
      * 删除联系人
      * */
     @RequestMapping("delete")
     public void delete(String from,String to){
        adminService.deleteImessage(from,to);
        adminService.deleteImessage(to,from);
        adminService.deletefrom(from);
     }


    /**
     * 查询联系人
     */
    @RequestMapping("selectfrom")
    public JsonData selectfrom(HttpServletRequest request,HttpSession session) {
        List<User> users = new ArrayList<>();
        String adminname = (String) request.getAttribute("adminname");
        if(adminname.equals("root")){
            redisClient.set("adminname",adminname);
            users = adminService.selectfrom(0,adminname);
        }else {
            redisClient.set("admin",adminname);
            users =  adminService.selectfrom(1,adminname);
        }
        for (int i=0;i<users.size();i++){
            users.get(i).setState(adminService.selectmessagestate(users.get(i).getUsername()));
        }
        users.sort(Comparator.comparing(User::getTime));

//        String  sessionid=session.getId();
//        AdminUser adminUser = new AdminUser(adminname);
//        V1ChatRoomController.onlineUser.put(sessionid,adminUser.getAdminname());
        return JsonData.buildSuccess(users,adminname);
    }

    /**
     * 查询消息
     * 发送人和收到人
     * */
    @RequestMapping("selectimessage")
    public List<InMessage> selectimessage(String to, String from){
        adminService.updatemessage(to,from);
      List<InMessage> list = new ArrayList<>();
              //查询收到的消息
              list.addAll(adminService.selectmessage(to));
             //查询发送的消息
              list.addAll(adminService.selectmessage(from));
              List<InMessage> inMessages = new ArrayList<>();
                 //消息时间排序
               list.sort(Comparator.comparing(InMessage::getTime));
               //倒序 让最新的信息排在前面
               Collections.reverse(list);
        for (int i = 0;i < list.size(); i++){
            if (list.get(i).getAddressee().equals(to)&&list.get(i).getInfrom().equals(from)
                    ||list.get(i).getInfrom().equals(to) &&list.get(i).getAddressee().equals(from)){

                inMessages.add(list.get(i));

            }
        }
return inMessages;
    }


    /**
     * 用户上线之后进行提示
     * */
    @RequestMapping(value="user", method=RequestMethod.POST)
    public JsonData userLogin(User user,HttpSession session) {
        Integer id =adminService.selectuser(user);
        String sessionId = session.getId();
        redisClient.set(sessionId, JsonUtils.obj2String(user.getId()));
        onlineUser.put(sessionId,user.getId());
        ws.sendOnlineUser(user.getUsername()+"上线了");
         return    JsonData.buildSuccess(id);
    }
    /**
     * 推送在线用户
     * */
//    @Scheduled(fixedRate = 2000)
//    public void onlineUser() {
//
//     if (onlineUser.size()==0){
//         adminService.updatestate();
//     }
//
//    }

   /**
    * 每过三个月 一日上午零点清空所有消息
    * */
    @Scheduled(cron ="0 0 0 1 1/3 ? ")
    public void deleteInMessage(){
     adminService.deleteallInMessage();
     adminService.deletaallfrom();
    }

    /**
     * 每天清空IP访问次数
     * */
    @Scheduled(cron ="0 0 0 * * ?")
    public void deleteiptable(){
        adminService.deletealliptable();
    }



    @PostMapping(value = "messageimg")
        public JsonData uploadmessageimg(@RequestParam("img")MultipartFile file) throws IOException {
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件的后缀名,比如图片的jpeg,png
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        fileName = UUID.randomUUID() + suffixName;
        if (file==null&&!suffixName.equals("png")&&!suffixName.equals("jpg")&&!suffixName.equals("jpeg")){
            return JsonData.buildError("图片上传格式不正确",-1);
        }
        File dest = new File( Imgsrc+ fileName);
        try {
            file.transferTo(dest);
            return JsonData.buildSuccess(address+fileName);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return JsonData.buildError("发送图片错误");
    }


    @GetMapping("Offline")
    public void Offline(String name){
        if (name.equals("root")){
            redisClient.delete("adminname");
        }
        else {
            redisClient.delete("admin");
        }
    }


    public static void main(String[] args) {
        System.out.println(new Date());
    }





    }


