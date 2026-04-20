package com.zhiji.smartbook.module.user.vo;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DashboardVO {
    private BigDecimal netBalance;
    private BigDecimal incomeTotal;
    private BigDecimal expenseTotal;
    private ChallengeVO challenge;

    @Data
    public static class ChallengeVO {
        private Integer completedDays;
        private Integer totalDays;
    }
}