package com.dehe.wxpush;

import com.dehe.model.InMessage;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 测试open id: o_Rdo0YtjWDqyxx1fA8DNo_4cBM0
 * */
public class WxPush {

        public final static String Appid="wxadbe080ec382214d";//公众号ID

        public final static String Secret="c47e13847d71fb2821b77df28fcd5c1b";//公众号密钥

        public final static String templateId="167Zg9Ib6Kt6DReO9wiVG2PLsIU333m0yqbFVMFU3G8";

        /**
         * 需要传入openid
         * */
    public static void push(InMessage inMessage){
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        wxStorage.setAppId(Appid);
        wxStorage.setSecret(Secret);
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);

        WxMpTemplateMessage  templateMessage =  WxMpTemplateMessage.builder()
                .toUser("o_Rdo0YhN_zq_HevofGzUa4DikKU")//用户openID
                .templateId(templateId)//模板ID
              //  .url("http://182.92.90.129/APP/index.html?from="+inMessage.getAddressee()+"&Addressee="+inMessage.getInfrom())
                .build();
                templateMessage.addData(new WxMpTemplateData("first",inMessage.getContent(),"#FF0000"));
                templateMessage.addData(new WxMpTemplateData("keyword1",inMessage.getInfrom(),"#000000"));
                templateMessage.addData(new WxMpTemplateData("keyword4",dateToString(inMessage.getTime()),"#000000"));
                templateMessage.addData(new WxMpTemplateData("keyword3","客服系统","#000000"));
                //templateMessage.addData(new WxMpTemplateData("remark","点击回复","#000000"));
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }
    /*
     * @Author 杨佳旺
     * @Date 2020/11/12 17:54
     * @Description: 时间转换方法
     * @Param
     * @Return
     */
    public static String dateToString(Date date) {
        SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd");//日期格式
        String tiem = sformat.format(date);

        return tiem;
    }

}
