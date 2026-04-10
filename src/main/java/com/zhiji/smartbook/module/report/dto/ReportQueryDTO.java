package com.zhiji.smartbook.module.report.dto;

import lombok.Data;

@Data
public class ReportQueryDTO {
    private Long ledgerId;
    private String periodType;
    private String date;
    private String type;
}
