package com.zhiji.smartbook.module.shared.service.impl;

import com.zhiji.smartbook.module.shared.dto.SharedBillCreateDTO;
import com.zhiji.smartbook.module.shared.dto.SharedBillItemCreateDTO;
import com.zhiji.smartbook.module.shared.service.SharedBillService;
import com.zhiji.smartbook.module.shared.vo.SharedBillDetailVO;
import com.zhiji.smartbook.module.shared.vo.SharedBillVO;
import com.zhiji.smartbook.module.shared.vo.SettlementResultVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SharedBillServiceImpl implements SharedBillService {

    @Override
    public List<SharedBillVO> listSharedBills() {
        SharedBillVO sharedBill = new SharedBillVO();
        sharedBill.setId(50001L);
        sharedBill.setName("大理旅行");
        sharedBill.setTotalAmount(356.00);
        sharedBill.setCurrency("CNY");
        sharedBill.setStatus("UNSETTLED");
        sharedBill.setMemberCount(4);
        return List.of(sharedBill);
    }

    @Override
    public Map<String, Object> createSharedBill(SharedBillCreateDTO request, String idempotencyKey) {
        return Map.of(
                "sharedBillId", 50001L,
                "idempotencyKey", idempotencyKey == null ? "" : idempotencyKey
        );
    }

    @Override
    public SharedBillDetailVO getSharedBillDetail(Long sharedBillId) {
        SharedBillDetailVO detail = new SharedBillDetailVO();
        detail.setId(sharedBillId);
        detail.setName("大理旅行");
        detail.setTotalAmount(356.00);
        detail.setCurrency("CNY");
        detail.setStatus("UNSETTLED");
        detail.setMemberCount(4);
        detail.setMembers(List.of("我", "张三", "李四", "王五"));
        return detail;
    }

    @Override
    public Map<String, Object> createSharedBillItem(Long sharedBillId, SharedBillItemCreateDTO request) {
        return Map.of(
                "sharedBillId", sharedBillId,
                "itemId", 60001L
        );
    }

    @Override
    public SettlementResultVO getSettlements(Long sharedBillId) {
        SettlementResultVO result = new SettlementResultVO();
        result.setPayableAmount(50.00);
        result.setReceivableAmount(20.00);
        result.setSettlements(List.of(Map.of(
                "settlementId", 70001L,
                "fromUserName", "我",
                "toUserName", "张三",
                "amount", 120.00,
                "status", "PENDING"
        )));
        return result;
    }

    @Override
    public Map<String, Object> confirmSettlement(Long sharedBillId, Long settlementId) {
        return Map.of(
                "sharedBillId", sharedBillId,
                "settlementId", settlementId,
                "status", "CONFIRMED"
        );
    }
}
