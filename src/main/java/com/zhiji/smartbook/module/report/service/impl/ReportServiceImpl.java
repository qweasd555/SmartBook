package com.zhiji.smartbook.module.report.service.impl;

import com.zhiji.smartbook.module.bill.mapper.BillMapper; // ✅ 加上这个导入
import com.zhiji.smartbook.module.report.service.ReportService;
import com.zhiji.smartbook.module.report.vo.CategoryRatioVO;
import com.zhiji.smartbook.module.report.vo.ReportSummaryVO;
import com.zhiji.smartbook.module.report.vo.TrendPointVO;
import com.zhiji.smartbook.module.report.vo.WarningVO;
import lombok.RequiredArgsConstructor; // ✅ 确保有这个注解
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor // ✅ 这个注解会自动注入所有 final 变量
public class ReportServiceImpl implements ReportService {

    // ✅ 加上这一行，声明并注入 BillMapper
    private final BillMapper billMapper;

    @Override
    public ReportSummaryVO getSummary(Long ledgerId, String periodType, String date) {
        ReportSummaryVO summary = new ReportSummaryVO();
        summary.setPeriodType(periodType);
        summary.setStartDate("2026-04-07");
        summary.setEndDate("2026-04-13");
        summary.setExpenseTotal(BigDecimal.valueOf(1256.00));
        summary.setIncomeTotal(BigDecimal.ZERO);
        summary.setAvgDailyExpense(BigDecimal.valueOf(179.43));
        summary.setLargestExpenseAmount(BigDecimal.valueOf(399.00));
        summary.setLargestExpenseCategoryName("数码");
        summary.setLargestExpenseMerchantName("耳机");
        summary.setExpenseChangeRate(BigDecimal.valueOf(12.0));
        return summary;
    }

    @Override
    public List<CategoryRatioVO> getCategoryRatio(Long ledgerId, String periodType, String date, String type) {
        String startTime = "2026-04-01 00:00:00";
        String endTime = "2026-04-30 23:59:59";
        Integer typeValue = type != null ? Integer.valueOf(type) : 0;

        // ✅ 现在 billMapper 已经可以正常使用了
        return billMapper.selectCategoryStatistics(1L, startTime, endTime, typeValue);
    }

    @Override
    public List<TrendPointVO> getTrend(Long ledgerId, String periodType, String date, String type) {
        TrendPointVO point1 = new TrendPointVO();
        point1.setLabel("2026-04-08");
        point1.setAmount(BigDecimal.valueOf(120.50));

        TrendPointVO point2 = new TrendPointVO();
        point2.setLabel("2026-04-09");
        point2.setAmount(BigDecimal.valueOf(88.00));
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