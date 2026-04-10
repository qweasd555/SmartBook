package com.zhiji.smartbook.module.report.vo;

import lombok.Data;

@Data
public class CategoryRatioVO {
    private Long categoryId;
    private String categoryName;
    private Double amount;
    private Double ratio;
}
