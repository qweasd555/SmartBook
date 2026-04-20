package com.zhiji.smartbook.module.report.vo;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TrendPointVO {
    private String label;
    private BigDecimal amount;
}