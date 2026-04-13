package com.zhiji.smartbook.module.shared.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("shared_bill")
public class SharedBill {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String currency;

    private BigDecimal totalAmount;

    private Integer status; // 0=未结算 1=已结算

    private Long createdBy;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}