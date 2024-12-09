package org.example.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.example.enums.OrderEventEnum;
import org.example.enums.OrderStatusEnum;

@TableName(value = "order_event")
public class OrderEvent {
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    @TableField(value = "ORDER_ID")
    private String orderId;

    @TableField(value = "OPERATE")
    private Integer operate;

    public static final String COL_ID = "ID";

    public static final String COL_ORDER_ID = "ORDER_ID";

    public static final String COL_OPERATE = "OPERATE";

    public OrderEvent(String orderId,OrderEventEnum eventEnum) {
        this.operate = eventEnum.getCode();
        this.orderId = orderId;
    }

    public OrderEvent(String id, String orderId, Integer operate) {
        this.id = id;
        this.orderId = orderId;
        this.operate = operate;
    }

    /**
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return ORDER_ID
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * @param orderId
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * @return OPERATE
     */
    public Integer getOperate() {
        return operate;
    }

    /**
     * @param operate
     */
    public void setOperate(Integer operate) {
        this.operate = operate;
    }
}