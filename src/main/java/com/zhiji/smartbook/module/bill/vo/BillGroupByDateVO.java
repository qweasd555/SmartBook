package com.zhiji.smartbook.module.bill.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BillGroupByDateVO {
    private String date;
    private BigDecimal expenseTotal;
    private BigDecimal incomeTotal;
    private List<BillListItemVO> items;
}
