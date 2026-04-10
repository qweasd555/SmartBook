package com.zhiji.smartbook.module.bill.vo;

import lombok.Data;

import java.util.List;

@Data
public class BillGroupByDateVO {
    private String date;
    private Double expenseTotal;
    private Double incomeTotal;
    private List<BillListItemVO> items;
}
