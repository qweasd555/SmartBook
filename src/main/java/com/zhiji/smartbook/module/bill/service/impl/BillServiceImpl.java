package com.zhiji.smartbook.module.bill.service.impl;

import com.zhiji.smartbook.module.bill.dto.BillCreateDTO;
import com.zhiji.smartbook.module.bill.dto.BillUpdateDTO;
import com.zhiji.smartbook.module.bill.service.BillService;
import com.zhiji.smartbook.module.bill.vo.BillDetailVO;
import com.zhiji.smartbook.module.bill.vo.BillGroupByDateVO;
import com.zhiji.smartbook.module.bill.vo.BillListItemVO;
import com.zhiji.smartbook.module.bill.vo.RecentBillVO;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class BillServiceImpl implements BillService {

    @Override
    public Map<String, Object> createBill(BillCreateDTO request, String idempotencyKey) {
        return Map.of(
                "transactionId", 30001L,
                "idempotencyKey", idempotencyKey == null ? "" : idempotencyKey
        );
    }

    @Override
    public BillDetailVO updateBill(Long transactionId, BillUpdateDTO request) {
        return buildDetail(transactionId, request.getType(), request.getAmount(), request.getCategoryId(),
                request.getMerchantName(), request.getOccurredAt(), request.getRemark(), request.getSource());
    }

    @Override
    public void deleteBill(Long transactionId) {
        // 首期联调阶段仅返回成功
    }

    @Override
    public BillDetailVO getBillDetail(Long transactionId) {
        return buildDetail(transactionId, "EXPENSE", 25.50, 502L,
                "滴滴出行", "2026-04-08 12:15:00", "打车回家", "MANUAL");
    }

    @Override
    public Map<String, Object> getBills(Integer pageNo, Integer pageSize, Long ledgerId, String range, String type, Long categoryId) {
        BillListItemVO item = new BillListItemVO();
        item.setId(30001L);
        item.setType(type == null ? "EXPENSE" : type);
        item.setAmount(18.00);
        item.setCategoryName("餐饮");
        item.setMerchantName("瑞幸咖啡");
        item.setOccurredAt("2026-04-08 14:30:00");
        item.setSource("MANUAL");

        Map<String, Object> page = new LinkedHashMap<>();
        page.put("pageNo", pageNo);
        page.put("pageSize", pageSize);
        page.put("total", 1);
        page.put("list", List.of(item));
        return page;
    }

    @Override
    public List<BillGroupByDateVO> groupByDate(Long ledgerId, String startDate, String endDate) {
        BillListItemVO item = new BillListItemVO();
        item.setId(30001L);
        item.setType("EXPENSE");
        item.setAmount(18.00);
        item.setCategoryName("餐饮");
        item.setMerchantName("瑞幸咖啡");
        item.setOccurredAt("2026-04-08 14:30:00");
        item.setSource("MANUAL");

        BillGroupByDateVO group = new BillGroupByDateVO();
        group.setDate("2026-04-08");
        group.setExpenseTotal(56.30);
        group.setIncomeTotal(0.00);
        group.setItems(List.of(item));
        return List.of(group);
    }

    @Override
    public List<RecentBillVO> getRecentBills(Long ledgerId, Integer limit) {
        RecentBillVO item = new RecentBillVO();
        item.setId(30001L);
        item.setType("EXPENSE");
        item.setAmount(18.00);
        item.setCategoryName("餐饮");
        item.setMerchantName("瑞幸咖啡");
        item.setOccurredAt("2026-04-08 14:30:00");
        item.setSource("MANUAL");
        return List.of(item);
    }

    private BillDetailVO buildDetail(Long transactionId, String type, Double amount, Long categoryId,
                                     String merchantName, String occurredAt, String remark, String source) {
        BillDetailVO detail = new BillDetailVO();
        detail.setId(transactionId);
        detail.setLedgerId(20001L);
        detail.setType(type);
        detail.setAmount(amount);
        detail.setCategoryId(categoryId);
        detail.setCategoryName("出行");
        detail.setMerchantName(merchantName);
        detail.setOccurredAt(occurredAt);
        detail.setRemark(remark);
        detail.setReceiptImageUrl("");
        detail.setSource(source);
        return detail;
    }
}
