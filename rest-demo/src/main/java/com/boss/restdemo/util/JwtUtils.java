package com.boss.restdemo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    //default expire time
    private static final long EXPIRE_TIME=1*60*1000;
    //secret key
    private static final String TOKEN_SECRET="MY_TOKEN_SECRET";
    //TYPE
    private static final String TYPE="Jwt";
    //encryption algorithm
    private static final String ALGORITHM_NAME="HS256";
    //user sign
    private static final String USER_SIGN="UID";


    private JwtUtils(){}

    public static String sign(String uid) {
        if(uid==null){
            return null;
        }
        String result=null;
        //过期时间
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        //私钥加密
        Algorithm hmac256 = Algorithm.HMAC256(TOKEN_SECRET);
        //设置头部信息
        Map<String,Object> header=new HashMap<>();
        header.put("Type",TYPE);
        header.put("alg", ALGORITHM_NAME);
        //返回token字符串
        result=JWT.create()
            .withHeader(header)
            .withClaim(USER_SIGN, uid)
            .withExpiresAt(expireDate)
            .sign(hmac256);

        return result;
    }

    public static String verify(String token){
        if(token==null){
            return null;
        }

        String uid=null;
        Algorithm hmac256 = Algorithm.HMAC256(TOKEN_SECRET);
        JWTVerifier verifier = JWT.require(hmac256).build();
        try {
            DecodedJWT jwt = verifier.verify(token);
            uid = jwt.getClaim(USER_SIGN).asString();
        }catch (JWTVerificationException e){
            logger.error("token:{}\nerrMsg:{}",token,e.getMessage());
        }

        return uid;
    }
}
