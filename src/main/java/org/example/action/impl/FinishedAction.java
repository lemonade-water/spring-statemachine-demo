package org.example.action.impl;

import jakarta.annotation.Resource;
import org.example.domain.Order;
import org.example.domain.OrderEvent;
import org.example.enums.OrderEventEnum;
import org.example.enums.OrderStatusEnum;
import org.example.service.OrderEventService;
import org.example.service.OrderService;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Service;

@Service
public class FinishedAction implements Action<OrderStatusEnum, OrderEventEnum> {
    @Resource
    private OrderService orderService;

    @Resource
    private OrderEventService eventService;
    @Override
    public void execute(StateContext<OrderStatusEnum, OrderEventEnum> context) {
        Message<OrderEventEnum> message = context.getMessage();
        Order order = (Order) message.getHeaders().get("order");
        order.setStatus(OrderStatusEnum.FINISHED.getCode());
        OrderEvent event = new OrderEvent(order.getId(), OrderEventEnum.FINISHED);

        orderService.updateById(order);
        eventService.save(event);
    }
}
