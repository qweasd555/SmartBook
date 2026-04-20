package com.zhiji.smartbook.module.bill.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RecentBillVO {
    private Long id;
    private String type;
    private BigDecimal amount;
    private String categoryName;
    private String merchantName;
    private String occurredAt;
    private String source;
}
