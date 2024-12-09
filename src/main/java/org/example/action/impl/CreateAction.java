package org.example.action.impl;

import jakarta.annotation.Resource;
import org.example.action.OrderAction;
import org.example.domain.Order;
import org.example.domain.OrderEvent;
import org.example.enums.OrderEventEnum;
import org.example.enums.OrderStatusEnum;
import org.example.service.OrderEventService;
import org.example.service.OrderService;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.stereotype.Service;

@Service
public class CreateAction implements OrderAction {
    @Resource
    private OrderService orderService;

    @Resource
    private OrderEventService eventService;

    @Override
    public void execute(StateContext<OrderStatusEnum, OrderEventEnum> context) {
        Message<OrderEventEnum> message = context.getMessage();
        Order order = (Order)message.getHeaders().get("order");
        StateMachine<OrderStatusEnum, OrderEventEnum> stateMachine = context.getStateMachine();
        String user = order.getUser();
        order.setStatus(stateMachine.getState().getId().getCode());
        if (user!=null&&!user.isEmpty()){
            order.setStatus(OrderStatusEnum.TO_BE_ACCEPTED.getCode());
        }
        orderService.save(order);

        OrderEvent orderEvent = new OrderEvent(order.getId(), OrderEventEnum.CREATE);
        eventService.save(orderEvent);
    }
}
