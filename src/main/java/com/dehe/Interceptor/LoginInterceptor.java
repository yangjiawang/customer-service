package com.dehe.Interceptor;

import com.dehe.utils.JWTUtils;
import com.dehe.utils.JsonData;
import com.dehe.utils.RedisClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author YANGJIAWANG
 * @DATE 2020/12/4
 * @TIME 16:20
 **/
public class LoginInterceptor implements HandlerInterceptor {

    private RedisClient redisClient;

    public LoginInterceptor(RedisClient redisClient) {
        this.redisClient = redisClient;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      try {
        String accesToken=request.getHeader("token");

        if (accesToken == null){
            accesToken =  request.getParameter("token");

        }
        if (StringUtils.isNotBlank(accesToken)){
            Claims claims = JWTUtils.checkJWT(accesToken);
            if (claims == null){
                sendJsonMessage(response, JsonData.buildError("登录过期，请重新登录"));
                return false;
            }
            String adminname = (String) claims.get("adminname");

            request.setAttribute("adminname",adminname);
            return true;
        }
      }catch (Exception e){
          e.printStackTrace();
      }

        sendJsonMessage(response, JsonData.buildError("登录过期，请重新登录"));
      return false;
    }
    /**
     * 响应json数据给前端
     * @param response
     * @param obj
     */
    public static void sendJsonMessage(HttpServletResponse response, Object obj){

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.print(objectMapper.writeValueAsString(obj));
            writer.close();
            response.flushBuffer();
        }catch (Exception e){
            e.printStackTrace();
        }


    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
