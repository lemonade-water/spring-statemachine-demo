package org.example.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OrderEventEnum {
    CREATE(0, "下单"),
    DISPATCH(1, "派工"),
    ACCEPTED(2, "接收"),
    CANCELED(3, "取消"),
    FINISHED(4, "完成"),
    CHECK(5, "choice"),
    ;
    private final int code;
    private final String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
