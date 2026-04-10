package com.zhiji.smartbook.module.report;

import com.zhiji.smartbook.common.response.Result;
import com.zhiji.smartbook.module.report.service.ReportService;
import com.zhiji.smartbook.module.report.vo.CategoryRatioVO;
import com.zhiji.smartbook.module.report.vo.ReportSummaryVO;
import com.zhiji.smartbook.module.report.vo.TrendPointVO;
import com.zhiji.smartbook.module.report.vo.WarningVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/summary")
    public Result<ReportSummaryVO> getReportSummary(@RequestParam Long ledgerId,
                                                    @RequestParam String periodType,
                                                    @RequestParam String date) {
        return Result.success(reportService.getSummary(ledgerId, periodType, date));
    }

    @GetMapping("/category-ratio")
    public Result<List<CategoryRatioVO>> getCategoryRatio(@RequestParam Long ledgerId,
                                                          @RequestParam String periodType,
                                                          @RequestParam String date,
                                                          @RequestParam String type) {
        return Result.success(reportService.getCategoryRatio(ledgerId, periodType, date, type));
    }

    @GetMapping("/trend")
    public Result<List<TrendPointVO>> getTrend(@RequestParam Long ledgerId,
                                               @RequestParam String periodType,
                                               @RequestParam String date,
                                               @RequestParam String type) {
        return Result.success(reportService.getTrend(ledgerId, periodType, date, type));
    }

    @GetMapping("/warnings")
    public Result<List<WarningVO>> getWarnings(@RequestParam Long ledgerId,
                                               @RequestParam String periodType,
                                               @RequestParam String date) {
        return Result.success(reportService.getWarnings(ledgerId, periodType, date));
    }
}
