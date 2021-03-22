package com.dehe.utils;

import com.dehe.domain.AdminUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @author YANGJIAWANG
 * @DATE 2020/12/4
 * @TIME 10:45
 **/
public class JWTUtils {
        //过期时间
        private static final long EXPIRE = 604800017L;
        //密钥
        private static final String SECRET="dehexinxi";
        //令牌前缀
        private static final String TOKEN_PREFIX="dehe";
        //主题
        private static final String SUBJECT="dehexx";

        /**
         * 根据用户信息生成令牌
         * */
    public static  String geneJsonWebToken(AdminUser adminUser){
      String token = Jwts.builder()
              .setSubject(SUBJECT)
                .claim("adminname",adminUser.getAdminname())
              .setIssuedAt(new Date())
              .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
              .signWith(SignatureAlgorithm.HS256,SECRET).compact();

        token = TOKEN_PREFIX + token;

        return token;
    }
    /**
     * 校验token的方法
     * @param token
     * @return
     */
    public static Claims checkJWT(String token){
        try{
            final  Claims claims = Jwts.parser().setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX,"")).getBody();
            return claims;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
