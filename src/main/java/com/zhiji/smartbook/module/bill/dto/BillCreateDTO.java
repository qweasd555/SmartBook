package com.zhiji.smartbook.module.bill.dto;

import lombok.Data;

@Data
public class BillCreateDTO {
    private Long ledgerId;
    private String type;
    private Double amount;
    private Long categoryId;
    private String merchantName;
    private String occurredAt;
    private String remark;
    private String receiptImageUrl;
    private String source;
}
