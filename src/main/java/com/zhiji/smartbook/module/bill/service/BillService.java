package com.zhiji.smartbook.module.bill.service;

import com.zhiji.smartbook.module.bill.dto.BillCreateDTO;
import com.zhiji.smartbook.module.bill.dto.BillUpdateDTO;
import com.zhiji.smartbook.module.bill.vo.BillDetailVO;
import com.zhiji.smartbook.module.bill.vo.BillGroupByDateVO;
import com.zhiji.smartbook.module.bill.vo.RecentBillVO;

import java.util.List;
import java.util.Map;

public interface BillService {

    Map<String, Object> createBill(BillCreateDTO request, String idempotencyKey);

    BillDetailVO updateBill(Long transactionId, BillUpdateDTO request);

    void deleteBill(Long transactionId);

    BillDetailVO getBillDetail(Long transactionId);

    Map<String, Object> getBills(Integer pageNo, Integer pageSize, Long ledgerId, String range, String type, Long categoryId);

    List<BillGroupByDateVO> groupByDate(Long ledgerId, String startDate, String endDate);

    List<RecentBillVO> getRecentBills(Long ledgerId, Integer limit);
}
