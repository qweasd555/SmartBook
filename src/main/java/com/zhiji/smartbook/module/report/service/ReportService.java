package com.zhiji.smartbook.module.report.service;

import com.zhiji.smartbook.module.report.vo.CategoryRatioVO;
import com.zhiji.smartbook.module.report.vo.ReportSummaryVO;
import com.zhiji.smartbook.module.report.vo.TrendPointVO;
import com.zhiji.smartbook.module.report.vo.WarningVO;

import java.util.List;

public interface ReportService {

    ReportSummaryVO getSummary(Long ledgerId, String periodType, String date);

    List<CategoryRatioVO> getCategoryRatio(Long ledgerId, String periodType, String date, String type);

    List<TrendPointVO> getTrend(Long ledgerId, String periodType, String date, String type);

    List<WarningVO> getWarnings(Long ledgerId, String periodType, String date);
}
