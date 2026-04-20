package com.zhiji.smartbook.module.bill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhiji.smartbook.module.bill.entity.Bill;
import com.zhiji.smartbook.module.bill.vo.BillGroupByDateVO;
import com.zhiji.smartbook.module.bill.vo.BillListItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper
public interface BillMapper extends BaseMapper<Bill> {

    // 1. 分页查询（你已经有了，建议参数增加 userId）
    List<BillListItemVO> selectBillPage(@Param("userId") Long userId,
                                        @Param("startTime") String startTime,
                                        @Param("endTime") String endTime,
                                        @Param("type") Integer type, // 改为 Integer
                                        @Param("categoryName") String categoryName);

    // 2. 每日分组统计（你已经有了）
    List<BillGroupByDateVO> selectGroupByDate(@Param("userId") Long userId,
                                              @Param("startTime") String startTime,
                                              @Param("endTime") String endTime,
                                            @Param("categoryName") String categoryName); // 加上这一行

    // 3. 补充：区间总计（用于首页：今天花了多少，收了多少）
    // 返回值可以用 Map，也可以定义一个专门的统计 VO
    Map<String, BigDecimal> selectTotalAmount(@Param("userId") Long userId,
                                              @Param("startTime") String startTime,
                                              @Param("endTime") String endTime);

    // 4. 补充：分类统计（用于报表饼图）
    List<Map<String, Object>> selectCategoryStatistics(@Param("userId") Long userId,
                                                       @Param("startTime") String startTime,
                                                       @Param("endTime") String endTime,
                                                       @Param("type") Integer type);
}