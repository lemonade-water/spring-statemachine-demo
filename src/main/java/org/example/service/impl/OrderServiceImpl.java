package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.config.OrderStateMachineBuilder;
import org.example.domain.Order;
import org.example.enums.OrderEventEnum;
import org.example.enums.OrderStatusEnum;
import org.example.mapper.OrderMapper;
import org.example.service.OrderService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Resource
    private OrderStateMachineBuilder orderStateMachineBuilder;
    @Resource
    private BeanFactory beanFactory;
    @Resource(name = "orderStateMachinePersister")
    private StateMachinePersister<OrderStatusEnum, OrderEventEnum, Order> orderStateMachinePersister;

    private synchronized void sendEvent(Message<OrderEventEnum> message,Order order){
        try {
            order.setEventEnum(message.getPayload());
            StateMachine<OrderStatusEnum, OrderEventEnum> stateMachine = orderStateMachineBuilder.build(beanFactory);
            orderStateMachinePersister.restore(stateMachine, order);
            stateMachine.sendEvent(message);
            log.info("当前值："+stateMachine.getState().getId().getName());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void create() {
        Order order = new Order();
        order.setStatus(OrderStatusEnum.TO_BE_DISPATCH.getCode());
        Message<OrderEventEnum> message = MessageBuilder.withPayload(OrderEventEnum.CHECK).
                setHeader("order", order).build();
        sendEvent(message,order);
    }

    @Override
    public void createWithUser() {
        Order order = new Order();
        order.setUser("zhangs");
        order.setStatus(OrderStatusEnum.TO_BE_DISPATCH.getCode());
        Message<OrderEventEnum> message = MessageBuilder.withPayload(OrderEventEnum.CHECK).
                setHeader("order", order).build();
        sendEvent(message,order);
    }

    @Override
    public void dispatch(String id, String user) {
        Order order = this.getById(id);
        order.setUser(user);
        Message<OrderEventEnum> message = MessageBuilder.withPayload(OrderEventEnum.DISPATCH).
                setHeader("order", order).build();
        sendEvent(message,order);
    }

    @Override
    public void accepted(String id) {
        Order order = this.getById(id);
        Message<OrderEventEnum> message = MessageBuilder.withPayload(OrderEventEnum.ACCEPTED).
                setHeader("order", order).build();
        sendEvent(message,order);
    }

    @Override
    public void finished(String id) {
        Order order = this.getById(id);
        Message<OrderEventEnum> message = MessageBuilder.withPayload(OrderEventEnum.FINISHED).
                setHeader("order", order).build();
        sendEvent(message,order);
    }

    @Override
    public void cancel(String id) {
        Order order = this.getById(id);
        Message<OrderEventEnum> message = MessageBuilder.withPayload(OrderEventEnum.CANCELED).
                setHeader("order", order).build();
        sendEvent(message,order);
    }

    @Override
    public void flowEnd(Order order) {
        Message<OrderEventEnum> message = MessageBuilder.withPayload(OrderEventEnum.DISPATCH).
                setHeader("order", order).build();
        sendEvent(message,order);
    }
}
