package com.zhiji.smartbook.module.report.vo;

import lombok.Data;

@Data
public class ReportSummaryVO {
    private String periodType;
    private String startDate;
    private String endDate;
    private Double expenseTotal;
    private Double incomeTotal;
    private Double avgDailyExpense;
    private Double largestExpenseAmount;
    private String largestExpenseCategoryName;
    private String largestExpenseMerchantName;
    private Double expenseChangeRate;
}
