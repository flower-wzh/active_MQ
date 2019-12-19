package com.wzh.activemq.controller;

import com.wzh.activemq.entity.User;
import com.wzh.activemq.util.SendEmailUtil;
import com.wzh.activemq.util.SendMessageUtil;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.jms.JMSException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    JmsTemplate jmsTemplate;

    @PostMapping
    public Map<String,Object> regist(User user) {
        Map<String,Object> map = new HashMap<>();
        /**
         * 模拟插入数据库
         */
        System.out.println(user);
        Map<String,String> userMessage = new HashMap<>();
        userMessage.put("phone",user.getPhone());
        userMessage.put("email",user.getEmail());
        jmsTemplate.convertAndSend("user",userMessage);
        map.put("status",200);
        map.put("data",user);
        return map;
    }

    @JmsListener(destination = "user")
    public void sendPhone(ActiveMQMapMessage message) throws JMSException {
        System.out.println("sendPhone");
        Map<String, Object> contentMap = message.getContentMap();
        String phone = contentMap.get("phone").toString();
        SendMessageUtil.send(phone,"945614");
    }

    @JmsListener(destination = "user")
    public void sendEmail(ActiveMQMapMessage message) throws JMSException {
        System.out.println("sendEmail");
        Map<String, Object> contentMap = message.getContentMap();
        String email = contentMap.get("email").toString();
        SendEmailUtil.sendMessage(email,"971128");
    }
}
