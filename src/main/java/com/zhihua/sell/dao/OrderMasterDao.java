package com.zhihua.sell.dao;

import com.zhihua.sell.pojo.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterDao extends JpaRepository<OrderMaster,String> {

    /**根据微信id查询订单*/
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
