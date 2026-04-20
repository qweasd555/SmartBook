package com.zhiji.smartbook.module.report.vo;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ReportSummaryVO {
    private String periodType;
    private String startDate;
    private String endDate;
    private BigDecimal expenseTotal;
    private BigDecimal incomeTotal;
    private BigDecimal avgDailyExpense;
    private BigDecimal largestExpenseAmount;
    private String largestExpenseCategoryName;
    private String largestExpenseMerchantName;
    private BigDecimal expenseChangeRate;
}