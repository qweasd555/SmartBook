package com.zhiji.smartbook.module.bill.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BillListItemVO {
    private Long id;

    //private String type;
    // 1. type 改成 Integer（0=支出，1=收入）
    private Integer type;

    private BigDecimal amount;
    private String categoryName;
    private String merchantName;
    private String occurredAt;
    private String source;
}
