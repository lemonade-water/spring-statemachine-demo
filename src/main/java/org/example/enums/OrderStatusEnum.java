package org.example.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OrderStatusEnum {
    TO_BE_DISPATCH(0, "待派工"),
    TO_BE_DISPATCH_CHOICE(5, "待派工choice"),
    TO_BE_ACCEPTED(1, "待接受"),
    TO_BE_FINISHED(2, "处理中"),
    CANCELED(3, "已取消"),
    FINISHED(4, "已完成"),
    ;
    private final int code;
    private final String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static OrderStatusEnum getByCode(Integer code) {
        if (code==null){
            return null;
        }
        for (OrderStatusEnum status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }
}
