package com.zhiji.smartbook.module.shared.vo;

import lombok.Data;

import java.util.List;

@Data
public class SharedBillDetailVO {
    private Long id;
    private String name;
    private Double totalAmount;
    private String currency;
    private String status;
    private Integer memberCount;
    private List<String> members;
}
