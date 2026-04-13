package com.zhiji.smartbook.module.challenge.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("challenge_record")
public class Challenge {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private LocalDate startDate;

    private Integer completedDays;

    private String badges; // JSON字符串

    private Integer status; // 0=进行中 1=已完成 2=已放弃

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}