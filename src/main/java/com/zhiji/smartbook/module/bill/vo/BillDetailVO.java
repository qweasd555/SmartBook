package com.zhiji.smartbook.module.bill.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BillDetailVO {
    private Long id;
    private Long ledgerId;
    private String type;
    private BigDecimal amount;
    private Long categoryId;
    private String categoryName;
    private String merchantName;
    private String occurredAt;
    private String remark;
    private String receiptImageUrl;
    private String source;
}
