package com.zhiji.smartbook.module.shared.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhiji.smartbook.module.shared.dto.SharedBillCreateDTO;
import com.zhiji.smartbook.module.shared.dto.SharedBillItemCreateDTO;
import com.zhiji.smartbook.module.shared.entity.SharedBill;
import com.zhiji.smartbook.module.shared.mapper.SharedBillMapper;
import com.zhiji.smartbook.module.shared.service.SharedBillService;
import com.zhiji.smartbook.module.shared.vo.SettlementResultVO;
import com.zhiji.smartbook.module.shared.vo.SharedBillDetailVO;
import com.zhiji.smartbook.module.shared.vo.SharedBillVO;
import com.zhiji.smartbook.common.utils.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SharedBillServiceImpl implements SharedBillService {

    private final SharedBillMapper sharedBillMapper;

    @Override
    public List<SharedBillVO> listSharedBills() {
        List<SharedBill> list = sharedBillMapper.selectList(
                new LambdaQueryWrapper<SharedBill>().eq(SharedBill::getCreatedBy, UserContext.get()));
        return list.stream().map(this::toVO).collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> createSharedBill(SharedBillCreateDTO request, String idempotencyKey) {
        SharedBill bill = new SharedBill();
        bill.setName(request.getName());
        bill.setCurrency(request.getCurrency() != null ? request.getCurrency() : "CNY");
        bill.setTotalAmount(BigDecimal.ZERO);
        bill.setStatus(0);
        bill.setCreatedBy(UserContext.get());
        sharedBillMapper.insert(bill);
        // TODO: 批量插入 shared_bill_member
        return Map.of("sharedBillId", bill.getId());
    }

    @Override
    public SharedBillDetailVO getSharedBillDetail(Long sharedBillId) {
        SharedBill bill = sharedBillMapper.selectById(sharedBillId);
        if (bill == null) return null;
        SharedBillDetailVO vo = new SharedBillDetailVO();
        vo.setId(bill.getId());
        vo.setName(bill.getName());
        vo.setTotalAmount(bill.getTotalAmount().doubleValue());
        vo.setCurrency(bill.getCurrency());
        vo.setStatus(bill.getStatus() == 0 ? "UNSETTLED" : "SETTLED");
        // TODO: 查询成员列表
        return vo;
    }

    @Override
    public Map<String, Object> createSharedBillItem(Long sharedBillId, SharedBillItemCreateDTO request) {
        // TODO: 插入 shared_bill_item + shared_bill_item_member，更新 shared_bill.total_amount
        return Map.of("sharedBillId", sharedBillId, "itemId", 0L);
    }

    @Override
    public SettlementResultVO getSettlements(Long sharedBillId) {
        // TODO: 查询 shared_bill_settlement
        SettlementResultVO result = new SettlementResultVO();
        result.setPayableAmount(0.0);
        result.setReceivableAmount(0.0);
        result.setSettlements(List.of());
        return result;
    }

    @Override
    public Map<String, Object> confirmSettlement(Long sharedBillId, Long settlementId) {
        // TODO: 更新 shared_bill_settlement.status = 1
        return Map.of("sharedBillId", sharedBillId, "settlementId", settlementId, "status", "CONFIRMED");
    }

    private SharedBillVO toVO(SharedBill bill) {
        SharedBillVO vo = new SharedBillVO();
        vo.setId(bill.getId());
        vo.setName(bill.getName());
        vo.setTotalAmount(bill.getTotalAmount().doubleValue());
        vo.setCurrency(bill.getCurrency());
        vo.setStatus(bill.getStatus() == 0 ? "UNSETTLED" : "SETTLED");
        return vo;
    }
}
