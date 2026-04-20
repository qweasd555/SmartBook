package com.zhiji.smartbook.module.bill.controller;

import com.zhiji.smartbook.common.response.Result;
import com.zhiji.smartbook.module.bill.service.BillService;
import com.zhiji.smartbook.module.report.service.ReportService; // ✅ 加上这个导入
import com.zhiji.smartbook.module.report.vo.CategoryRatioVO; // ✅ 加上这个导入
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final BillService billService;
    // ✅ 注入 ReportService（之前漏掉了）
    private final ReportService reportService;

    // ✅ 分类统计接口（现在调用 ReportService）
    @GetMapping("/category")
    public Result<List<CategoryRatioVO>> getCategoryStatistics(
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime,
            @RequestParam(required = false) String type,
            @RequestParam(defaultValue = "1") Long ledgerId,
            @RequestParam(defaultValue = "MONTH") String periodType,
            @RequestParam(required = false) String date
    ) {
        // ✅ 改成调用 reportService.getCategoryRatio
        List<CategoryRatioVO> data = reportService.getCategoryRatio(ledgerId, periodType, date, type);
        return Result.success(data);
    }

    // ✅ 总收支统计接口（修正字段名 balance → netBalance）
    @GetMapping("/total")
    public Result<Map<String, Object>> getTotal(HttpServletRequest request) {
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");

        Map<String, BigDecimal> data = billService.getTotalAmount(startTime, endTime);

        BigDecimal expense = BigDecimal.ZERO;
        BigDecimal income = BigDecimal.ZERO;
        if (data != null) {
            expense = data.get("expenseTotal") != null ? data.get("expenseTotal") : BigDecimal.ZERO;
            income = data.get("incomeTotal") != null ? data.get("incomeTotal") : BigDecimal.ZERO;
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("expenseTotal", expense);
        result.put("incomeTotal", income);
        // ✅ 字段名从 balance 改成 netBalance（对齐文档）
        result.put("netBalance", income.subtract(expense));

        return Result.success(result);
    }
}