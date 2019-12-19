package com.wzh.activemq.service;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
public class Cart {

    @Resource
    JmsTemplate jmsTemplate;

    public void cartSend(String msg) {
        ActiveMQTopic activeMQTopic = new ActiveMQTopic("cart");
        jmsTemplate.convertAndSend(activeMQTopic,msg);
    }

}
