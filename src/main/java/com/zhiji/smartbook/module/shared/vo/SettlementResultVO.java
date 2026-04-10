package com.zhiji.smartbook.module.shared.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SettlementResultVO {
    private Double payableAmount;
    private Double receivableAmount;
    private List<Map<String, Object>> settlements;
}
