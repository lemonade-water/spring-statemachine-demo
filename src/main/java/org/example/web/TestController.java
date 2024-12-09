package org.example.web;

import jakarta.annotation.Resource;
import org.example.domain.Order;
import org.example.domain.OrderEvent;
import org.example.enums.OrderEventEnum;
import org.example.enums.OrderStatusEnum;
import org.example.service.OrderEventService;
import org.example.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 缺点：不执行的异常会被吃掉
 */
@RestController
public class TestController {

    @Resource
    private OrderService orderService;

    @Resource
    private OrderEventService orderEventService;

    @GetMapping("/create")
    public String create() {
        orderService.create();
        return "ok";
    }

    @GetMapping("/createWithUser")
    public String createWithUser() {
        orderService.createWithUser();
        return "ok";
    }

    @GetMapping("/dispatch")
    public String dispatch(@RequestParam String id,@RequestParam String user) {
        orderService.dispatch(id,user);
        return "ok";
    }

    @GetMapping("/accepted")
    public String accepted(@RequestParam String id) {
        orderService.accepted(id);
        return "ok";
    }

    @GetMapping("/finished")
    public String finished(@RequestParam String id) {
        orderService.finished(id);
        return "ok";
    }

    @GetMapping("/cancel")
    public String cancel(@RequestParam String id) {
        orderService.cancel(id);
        return "ok";
    }

    @GetMapping("/batchCancel")
    public String batchCancel(@RequestParam String id) {
        String[] split = id.split(",");
        for (String s : split) {
            orderService.cancel(s);
        }
        return "ok";
    }

    @GetMapping("flowEnd")
    public String flowEnd(){
        Order order = new Order();
        order.setStatus(OrderStatusEnum.TO_BE_DISPATCH.getCode());
        order.setId("1");
        orderService.flowEnd(order);

        return "ok";
    }
}
