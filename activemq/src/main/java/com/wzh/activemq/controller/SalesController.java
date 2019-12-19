package com.wzh.activemq.controller;

import com.wzh.activemq.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SalesService salesService;

    @RequestMapping("/sales")
    public Map<String,Object> sales(String userId,String salesId) {
        Map<String,Object> map = new HashMap<>();
        Integer count = (Integer) redisTemplate.opsForValue().get("sales_total_" + salesId);
        Integer num = (Integer) redisTemplate.opsForValue().get("sales_num_" + salesId);
        Integer aLong = Math.toIntExact(redisTemplate.opsForList().leftPush("sales_user_" + salesId, userId));
        if (count >= aLong) {
            /**
             * 代表抢购成功的人数
             */
            map.put("status",200);
            System.out.println("抢购成功"+aLong);
            map.put("message","抢购成功！");
            return map;
        }
        map.put("status",-200);
        System.out.println("秒杀失败"+aLong);
        map.put("message","秒杀失败！");
        return map;
    }

    @RequestMapping("/createSale")
    public Map<String,Object> createSale(String salesId) {
        Map<String,Object> map = new HashMap<>();
        Integer integer = salesService.selectSalesCount(salesId);
        redisTemplate.opsForValue().set("sales_total_"+salesId,integer);
        redisTemplate.opsForValue().set("sales_num_"+salesId,0);
        map.put("status",200);
        map.put("message","创建秒杀成功");
        return map;
    }
}
