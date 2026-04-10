package com.zhiji.smartbook.module.bill.vo;

import lombok.Data;

@Data
public class BillListItemVO {
    private Long id;
    private String type;
    private Double amount;
    private String categoryName;
    private String merchantName;
    private String occurredAt;
    private String source;
}
