package org.example.config;

import jakarta.annotation.Resource;
import org.example.action.OrderAction;
import org.example.domain.Order;
import org.example.enums.OrderEventEnum;
import org.example.enums.OrderStatusEnum;
import org.example.guard.ToBeDispatchChoiceGuard;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

import java.util.EnumSet;
import java.util.Map;
import java.util.Optional;

/**
 * 每一个订单记录 有自己的状态机
 */
@Component
@Configuration
//注意：用build 创建 也需要添加EnableStateMachine 被博客坑了
@EnableStateMachine
public class OrderStateMachineBuilder {
    private final static String MACHINE_ID = "orderStateMachine";

    @Resource
    @Lazy
    private Map<String, OrderAction> orderActionMap;

    @Resource
    private ToBeDispatchChoiceGuard dispatchChoiceGuard;
    public StateMachine<OrderStatusEnum, OrderEventEnum> build(BeanFactory beanFactory) throws Exception {
        StateMachineBuilder.Builder<OrderStatusEnum, OrderEventEnum> builder = StateMachineBuilder.builder();

        System.out.println("构建订单状态机");

        builder.configureConfiguration()
                .withConfiguration()
                .machineId(MACHINE_ID)
                .beanFactory(beanFactory)
        ;

        builder.configureStates()
                .withStates()
                .initial(OrderStatusEnum.TO_BE_DISPATCH)
                .choice(OrderStatusEnum.TO_BE_DISPATCH_CHOICE)
                .states(EnumSet.allOf(OrderStatusEnum.class));

        builder.configureTransitions()
                .withExternal()
                .source(OrderStatusEnum.TO_BE_DISPATCH)
                .target(OrderStatusEnum.TO_BE_DISPATCH_CHOICE)
                .event(OrderEventEnum.CHECK)
                .and()
                .withChoice()
                .source(OrderStatusEnum.TO_BE_DISPATCH_CHOICE)
                .first(OrderStatusEnum.TO_BE_DISPATCH,dispatchChoiceGuard,getAction(OrderEventEnum.CREATE))
                .last(OrderStatusEnum.TO_BE_ACCEPTED,getAction(OrderEventEnum.CREATE))
                .and()
                //待派工-派工-待接收
                .withExternal()
                .source(OrderStatusEnum.TO_BE_DISPATCH)
                .target(OrderStatusEnum.TO_BE_ACCEPTED)
                .event(OrderEventEnum.DISPATCH)
                .action(getAction(OrderEventEnum.DISPATCH))
                .and()
                //待接收-接收-处理中
                .withExternal()
                .source(OrderStatusEnum.TO_BE_ACCEPTED)
                .target(OrderStatusEnum.TO_BE_FINISHED)
                .event(OrderEventEnum.ACCEPTED)
                .action(getAction(OrderEventEnum.ACCEPTED))
                .and()
                //处理中-完成-已完成
                .withExternal()
                .source(OrderStatusEnum.TO_BE_FINISHED)
                .target(OrderStatusEnum.FINISHED)
                .event(OrderEventEnum.FINISHED)
                .action(getAction(OrderEventEnum.FINISHED))
                .and()
                //待派工-取消-已取消
                .withExternal()
                .source(OrderStatusEnum.TO_BE_DISPATCH)
                .target(OrderStatusEnum.CANCELED)
                .event(OrderEventEnum.CANCELED)
                .action(getAction(OrderEventEnum.CANCELED))
                .and()
                //待接受-取消-已取消
                .withExternal()
                .source(OrderStatusEnum.TO_BE_ACCEPTED)
                .target(OrderStatusEnum.CANCELED)
                .event(OrderEventEnum.CANCELED)
                .action(getAction(OrderEventEnum.CANCELED))
                .and()
                //处理中-取消-已取消
                .withExternal()
                .source(OrderStatusEnum.TO_BE_FINISHED)
                .target(OrderStatusEnum.CANCELED)
                .event(OrderEventEnum.CANCELED)
                .action(getAction(OrderEventEnum.CANCELED))
        ;

        return builder.build();
    }

    private OrderAction getAction(OrderEventEnum eventEnum){
        return orderActionMap.get(eventEnum.name().toLowerCase() + "Action");
    }

    @Bean("orderStateMachinePersister")
    public DefaultStateMachinePersister orderStateMachinePersister() {
        return new DefaultStateMachinePersister<>(new StateMachinePersist<OrderStatusEnum, OrderEventEnum, Order>() {
            @Override
            public void write(StateMachineContext<OrderStatusEnum, OrderEventEnum> context, Order o) {

            }

            @Override
            public StateMachineContext<OrderStatusEnum, OrderEventEnum> read(Order o) {
                //注意这里需要把MACHINE_ID带上
                return new DefaultStateMachineContext<>(OrderStatusEnum.getByCode(o.getStatus()), o.getEventEnum(), null, null, null, MACHINE_ID);
            }
        });
    }
}
