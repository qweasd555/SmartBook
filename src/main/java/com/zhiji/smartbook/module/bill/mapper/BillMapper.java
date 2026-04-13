package com.zhiji.smartbook.module.bill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhiji.smartbook.module.bill.entity.Bill;
import com.zhiji.smartbook.module.bill.vo.BillGroupByDateVO;
import com.zhiji.smartbook.module.bill.vo.BillListItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BillMapper extends BaseMapper<Bill> {

    List<BillListItemVO> selectBillPage(@Param("userId") Long userId,
                                        @Param("startTime") String startTime,
                                        @Param("endTime") String endTime,
                                        @Param("type") String type,
                                        @Param("categoryName") String categoryName);

    List<BillGroupByDateVO> selectGroupByDate(@Param("userId") Long userId,
                                              @Param("startTime") String startTime,
                                              @Param("endTime") String endTime);
}