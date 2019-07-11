package com.fineway.scaffolding;

import com.alibaba.fastjson.JSON;
import com.fineway.dto.sso.UserDto;
import com.fineway.util.HttpUtil;
import com.fineway.util.MD5Util;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public abstract class BaseTest {

    @Test
    public void contextLoads() {
    }

    public static final String SSO_URL = "http://ip:7080/sso/";

    public static final Map<String,String> tokenMaps = Maps.newConcurrentMap();

    public static String getToken(String mobile, String password) {
        String value = tokenMaps.get(mobile+password);
        if (StringUtils.isNotEmpty(value)){
            return value;
        }
        String resp = HttpUtil.post(SSO_URL + "api/v1/login/" + mobile,"X-Client-Id:abc#Content-Type:application/json; charset=UTF-8#", String.format("{password:'%s'}", MD5Util.encode(password)));
        UserDto user = JSON.parseObject(JSON.parseObject(resp).getString("user"), UserDto.class);
        if (null==user){
            return null;
        }
        tokenMaps.put(mobile+password,user.getToken());
        return user.getToken();

    }
}

