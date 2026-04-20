// 路径：com.zhiji.smartbook.module.report.vo.CategoryRatioVO.java
package com.zhiji.smartbook.module.report.vo;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CategoryRatioVO {
    private String categoryName;
    // ✅ Double → BigDecimal
    private BigDecimal amount;
    private BigDecimal ratio;
}