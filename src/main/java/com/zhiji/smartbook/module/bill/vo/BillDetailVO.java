package com.zhiji.smartbook.module.bill.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BillDetailVO {
    private Long id;
  //  private Long ledgerId;

   // private String type;
   // 1. type 改成 Integer（0=支出，1=收入）
   private Integer type;

    private BigDecimal amount;

    private Long categoryId;

    private String categoryName;
    private String merchantName;
    private String occurredAt;
    private String remark;
    private String receiptImageUrl;

   // private String source;
}
