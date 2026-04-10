package com.zhiji.smartbook.module.user.vo;

import lombok.Data;

@Data
public class UserDashboardVO {
    private String range;
    private Double netBalance;
    private Double incomeAmount;
    private Double expenseAmount;
    private Double todayExpense;
    private Double todayIncome;
    private Double budgetRemain;
}
