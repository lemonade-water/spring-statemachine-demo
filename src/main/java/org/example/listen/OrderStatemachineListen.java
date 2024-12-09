package org.example.listen;

import lombok.extern.slf4j.Slf4j;
import org.example.enums.OrderEventEnum;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

@WithStateMachine(id = "orderStateMachine")
@Slf4j
public class OrderStatemachineListen {

    @OnTransition(source = "TO_BE_DISPATCH", target = "TO_BE_ACCEPTED")
    public void dispatch(Message<OrderEventEnum> message) {
        log.info("dispatch");
    }

    @OnTransition(source = "TO_BE_ACCEPTED", target = "TO_BE_FINISHED")
    public void accepted(Message<OrderEventEnum> message) {
        log.info("accepted");
    }

    @OnTransition(source = "TO_BE_FINISHED", target = "FINISHED")
    public void finished(Message<OrderEventEnum> message) {
        log.info("finished");
    }

    @OnTransition(source = {"TO_BE_DISPATCH", "TO_BE_ACCEPTED", "TO_BE_FINISHED"}, target = "CANCELED")
    public void canceled(Message<OrderEventEnum> message) {
        log.info("canceled");
    }
}
