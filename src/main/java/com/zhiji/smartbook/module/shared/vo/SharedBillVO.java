package com.zhiji.smartbook.module.shared.vo;

import lombok.Data;

@Data
public class SharedBillVO {
    private Long id;
    private String name;
    private Double totalAmount;
    private String currency;
    private String status;
    private Integer memberCount;
}
