package com.zhiji.smartbook.module.report.service.impl;

import com.zhiji.smartbook.module.report.service.ReportService;
import com.zhiji.smartbook.module.report.vo.CategoryRatioVO;
import com.zhiji.smartbook.module.report.vo.ReportSummaryVO;
import com.zhiji.smartbook.module.report.vo.TrendPointVO;
import com.zhiji.smartbook.module.report.vo.WarningVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Override
    public ReportSummaryVO getSummary(Long ledgerId, String periodType, String date) {
        ReportSummaryVO summary = new ReportSummaryVO();
        summary.setPeriodType(periodType);
        summary.setStartDate("2026-04-07");
        summary.setEndDate("2026-04-13");
        summary.setExpenseTotal(1256.00);
        summary.setIncomeTotal(0.00);
        summary.setAvgDailyExpense(179.43);
        summary.setLargestExpenseAmount(399.00);
        summary.setLargestExpenseCategoryName("数码");
        summary.setLargestExpenseMerchantName("耳机");
        summary.setExpenseChangeRate(12.0);
        return summary;
    }

    @Override
    public List<CategoryRatioVO> getCategoryRatio(Long ledgerId, String periodType, String date, String type) {
        CategoryRatioVO ratio = new CategoryRatioVO();
        ratio.setCategoryId(501L);
        ratio.setCategoryName("餐饮");
        ratio.setAmount(565.20);
        ratio.setRatio(45.0);
        return List.of(ratio);
    }

    @Override
    public List<TrendPointVO> getTrend(Long ledgerId, String periodType, String date, String type) {
        TrendPointVO point1 = new TrendPointVO();
        point1.setLabel("2026-04-08");
        point1.setAmount(120.50);

        TrendPointVO point2 = new TrendPointVO();
        point2.setLabel("2026-04-09");
        point2.setAmount(88.00);
        return List.of(point1, point2);
    }

    @Override
    public List<WarningVO> getWarnings(Long ledgerId, String periodType, String date) {
        WarningVO warning = new WarningVO();
        warning.setWarningType("BUDGET_EXCEEDED");
        warning.setCategoryName("娱乐");
        warning.setMessage("娱乐支出已达到周预算的95%");
        return List.of(warning);
    }
}
