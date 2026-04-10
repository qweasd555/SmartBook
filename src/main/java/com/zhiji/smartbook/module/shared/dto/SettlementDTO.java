package com.zhiji.smartbook.module.shared.dto;

import lombok.Data;

@Data
public class SettlementDTO {
    private Long settlementId;
    private String fromUserName;
    private String toUserName;
    private Double amount;
    private String status;
}
