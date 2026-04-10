package com.zhiji.smartbook.module.shared.dto;

import lombok.Data;

import java.util.List;

@Data
public class SharedBillItemCreateDTO {
    private String title;
    private Double amount;
    private Long paidByUserId;
    private String splitType;
    private List<Long> participantUserIds;
    private String occurredAt;
}
