package com.zhiji.smartbook.module.bill.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("transactions")
public class Bill {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Integer type; // 0=支出 1=收入

    private BigDecimal amount;

    private String categoryName;

    private String merchantName;

    private LocalDateTime occurredAt;

    private String remark;

    private String receiptImageUrl;

    private String ocrTaskId;

    private Integer status; // 0=正常 1=已删除

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}