package com.wzh.activemq;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.xml.bind.SchemaOutputResolver;
import java.util.List;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void test() {
        Integer count = (Integer) redisTemplate.opsForValue().get("sales_total_1");
        Integer num = (Integer) redisTemplate.opsForValue().get("sales_num_1");
        List sales_user_1 = redisTemplate.opsForList().range("sales_user_1", 0, -1);
        System.out.println("count--->"+count);
        System.out.println("num--->"+num);
        System.out.println("users--->"+sales_user_1);
        System.out.println(sales_user_1.size());
    }
}
