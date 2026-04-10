package com.zhiji.smartbook.module.bill.dto;

import lombok.Data;

@Data
public class BillQueryDTO {
    private Integer pageNo;
    private Integer pageSize;
    private Long ledgerId;
    private String range;
    private String type;
    private Long categoryId;
}
