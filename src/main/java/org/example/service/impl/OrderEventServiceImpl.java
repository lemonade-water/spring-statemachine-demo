package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.domain.OrderEvent;
import org.example.mapper.OrderEventMapper;
import org.example.service.OrderEventService;
import org.springframework.stereotype.Service;

@Service
public class OrderEventServiceImpl extends ServiceImpl<OrderEventMapper, OrderEvent> implements OrderEventService {


}
