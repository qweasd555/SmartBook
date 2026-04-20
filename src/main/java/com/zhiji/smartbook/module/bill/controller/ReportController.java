package com.zhiji.smartbook.module.bill.controller;

import com.zhiji.smartbook.common.response.Result;
import com.zhiji.smartbook.module.bill.service.BillService;
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

    @GetMapping("/category")
    public Result getCategoryStatistics(
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime,
            @RequestParam(required = false) String type
    ) {

        List<Map<String, Object>> data = billService.getCategoryStatistics(
                startTime,
                endTime,
                type
        );

        return Result.success(data);
    }
    @GetMapping("/total")
    public Result<Map<String, Object>> getTotal(HttpServletRequest request) {
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");

        // 调用 Service（3个参数）
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
        result.put("balance", income.subtract(expense));

        return Result.success(result);
    }
}