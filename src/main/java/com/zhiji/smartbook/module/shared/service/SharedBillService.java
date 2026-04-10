package com.zhiji.smartbook.module.shared.service;

import com.zhiji.smartbook.module.shared.dto.SharedBillCreateDTO;
import com.zhiji.smartbook.module.shared.dto.SharedBillItemCreateDTO;
import com.zhiji.smartbook.module.shared.vo.SharedBillDetailVO;
import com.zhiji.smartbook.module.shared.vo.SharedBillVO;
import com.zhiji.smartbook.module.shared.vo.SettlementResultVO;

import java.util.List;
import java.util.Map;

public interface SharedBillService {

    List<SharedBillVO> listSharedBills();

    Map<String, Object> createSharedBill(SharedBillCreateDTO request, String idempotencyKey);

    SharedBillDetailVO getSharedBillDetail(Long sharedBillId);

    Map<String, Object> createSharedBillItem(Long sharedBillId, SharedBillItemCreateDTO request);

    SettlementResultVO getSettlements(Long sharedBillId);

    Map<String, Object> confirmSettlement(Long sharedBillId, Long settlementId);
}
