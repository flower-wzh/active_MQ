package com.wzh.activemq.controller;

import com.wzh.activemq.service.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private Cart cart;

    @RequestMapping("/send")
    public void send(String msg) {
        cart.cartSend(msg);
    }

    @JmsListener(destination = "cart")
    public void consumer(TextMessage message) throws JMSException {
        String text = message.getText();
        System.out.println(text);
    }
}
