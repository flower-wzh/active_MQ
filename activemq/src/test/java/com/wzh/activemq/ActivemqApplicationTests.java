package com.wzh.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;

import javax.annotation.Resource;
import javax.jms.*;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;

@SpringBootTest
class ActivemqApplicationTests {

    @Test
    void contextLoads() throws JMSException {

        //1.连接工厂
        String url = "tcp://116.62.38.97:61616";
        ConnectionFactory factory = new ActiveMQConnectionFactory(url);
        //2.创建连接
        Connection connection = factory.createConnection();
        //3.创建会话
        //第一个参数 代表第二参数是否生效开启事务  参数二 自动发送回执
        Session session = connection.createSession(true, AUTO_ACKNOWLEDGE);
        //4.创建生产者
        Destination destination = new ActiveMQQueue("cart");
        MessageProducer producer = session.createProducer(destination);
        //5.创建消息
        TextMessage text = session.createTextMessage("hello world");
        //6.使用生产者发生消息
        producer.send(text);
        //7.提交
        session.commit();
        //8.关闭
        producer.close();
        session.close();
        connection.close();
    }

    @Test
    public void consumer() throws JMSException {
        //1.连接工厂
        String url = "tcp://116.62.38.97:61616";
        ConnectionFactory factory = new ActiveMQConnectionFactory(url);
        //2.创建连接
        Connection connection = factory.createConnection();
        connection.start();
        //3.创建会话
        //第一个参数 代表第二参数是否生效开启事务  参数二 自动发送回执
        Session session = connection.createSession(true, AUTO_ACKNOWLEDGE);
        Destination destination = new ActiveMQQueue("cart");
        MessageConsumer consumer = session.createConsumer(destination);
        while (true) {
            TextMessage receive = (TextMessage) consumer.receive();
            String text = receive.getText();
            System.out.println(text);
            session.commit();
        }
    }
}
