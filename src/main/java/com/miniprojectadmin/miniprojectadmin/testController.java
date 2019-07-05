package com.miniprojectadmin.miniprojectadmin;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.miniprojectadmin.miniprojectadmin.constant.WXConstant;
import com.miniprojectadmin.miniprojectadmin.model.PublishInfo;
import com.miniprojectadmin.miniprojectadmin.utils.HttpInvoker;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.security.provider.MD5;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class testController {

    @RequestMapping(value="/wxlogin")
    @ResponseBody
    public Map<String, Object> login(HttpServletRequest request, HttpServletResponse response){
        String code = request.getParameter("code");
        JSONObject jsonObject = HttpInvoker.exec(WXConstant.CODE2SESSION.replace("APPID",WXConstant.APPID)
                                    .replace("SECRET",WXConstant.SECRET)
                                    .replace("JSCODE",code),
                            "GET",null);
        System.out.println(jsonObject.toString());
        System.out.println(code);
        String token = "";

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] MD5String = (jsonObject.getString("openid")+ jsonObject.getString("session_key")).getBytes("UTF-8");
            token = new String(md5.digest(Base64.encodeBase64(MD5String)),"UTF-8");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(token);
        Map<String, Object> map = new HashMap<>();
        map.put("token",token);
        return map;
    }

    @RequestMapping(value="/test")
    @ResponseBody
    public Map<String, Object> test(HttpServletRequest request, HttpServletResponse response){
      /*  type: sell
        latitude: 28.65782
        longitude: 115.87541
        message: 5465
        contact: +465
        address: 江西省南昌市西湖区南昌大桥头·西湖区政府西侧*/
        String type = request.getParameter("type");
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");
        String message = request.getParameter("message");
        String contact = request.getParameter("contact");
        String address = request.getParameter("address");

        System.out.println(type);
        System.out.println(latitude);
        System.out.println(longitude);
        System.out.println(message);
        System.out.println(contact);
        System.out.println(address);

        Map<String, Object> map = new HashMap<>();
        map.put("ret","1");
        return map;
    }
    @RequestMapping(value="/getMessages")
    @ResponseBody
    public Map<String, Object> getMessages(HttpServletRequest request, HttpServletResponse response){
        List<PublishInfo> list = new ArrayList<>();

        PublishInfo publishInfo = new PublishInfo();
        publishInfo.setType("sell");
        publishInfo.setAddress("江西省南昌市新建区");
        publishInfo.setContact("897654");
        publishInfo.setMessage("874984");
        publishInfo.setLatitude("28.66035");
        publishInfo.setLongitude("115.86436");

        PublishInfo publishInfo1 = new PublishInfo();
        publishInfo1.setType("buy");
        publishInfo1.setAddress("江西省南昌市西湖区洪城路987号丽都广场A座");
        publishInfo1.setContact("+954+9");
        publishInfo1.setMessage("8498");
        publishInfo1.setLatitude("28.65666");
        publishInfo1.setLongitude("115.87832");

        list.add(publishInfo);
        list.add(publishInfo1);

        Map<String, Object> map = new HashMap<>();
        map.put("data",list);
        return map;
    }

    @RequestMapping(value = "/getRunData")
    @ResponseBody
    public Map<String, Object> getRunData(HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> map = new HashMap<>();
        String encryptedData = request.getParameter("encryptedData");
        System.out.println(encryptedData);
        return map;
    }
}
