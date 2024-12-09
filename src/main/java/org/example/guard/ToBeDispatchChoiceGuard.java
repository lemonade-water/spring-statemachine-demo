package org.example.guard;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.Order;
import org.example.enums.OrderEventEnum;
import org.example.enums.OrderStatusEnum;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ToBeDispatchChoiceGuard implements Guard<OrderStatusEnum, OrderEventEnum> {

    @Override
    public boolean evaluate(StateContext<OrderStatusEnum, OrderEventEnum> context) {
        log.info("待派工守卫");
        boolean returnValue;
        Order order = context.getMessage().getHeaders().get("order", Order.class);
        String user = order.getUser();

        if (user!=null && !user.isEmpty()){
            returnValue = false;
        } else {
            returnValue = true;
        }
        return returnValue;
    }

}

