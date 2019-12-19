package com.wzh.activemq.service;

import com.wzh.activemq.dao.SalesDao;
import com.wzh.activemq.entity.Sales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SalesServiceImpl implements SalesService {

    @Autowired
    private SalesDao salesDao;

    @Override
    public Integer selectSalesCount(String salesId) {
        Sales sales = salesDao.selectByPrimaryKey(salesId);
        return sales.getCount();
    }
}
