package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.domain.Order;

public interface OrderService extends IService<Order> {

    void create();

    void createWithUser();

    void dispatch(String id, String user);

    void accepted(String id);

    void finished(String id);

    void cancel(String id);

    void flowEnd(Order order);

}
