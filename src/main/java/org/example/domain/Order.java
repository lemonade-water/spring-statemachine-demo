package org.example.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.example.enums.OrderEventEnum;

@TableName(value = "`order`")
public class Order {
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    @TableField(value = "`user`")
    private String user;

    @TableField(value = "`STATUS`")
    private Integer status;

    @TableField(exist = false)
    private OrderEventEnum eventEnum;

    public OrderEventEnum getEventEnum() {
        return eventEnum;
    }

    public void setEventEnum(OrderEventEnum eventEnum) {
        this.eventEnum = eventEnum;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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
     * @return STATUS
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}