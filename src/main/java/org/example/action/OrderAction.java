package org.example.action;

import org.example.enums.OrderEventEnum;
import org.example.enums.OrderStatusEnum;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Service;

public interface OrderAction extends Action<OrderStatusEnum, OrderEventEnum> {


}
