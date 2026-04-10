package com.zhiji.smartbook.module.shared.controller;

import com.zhiji.smartbook.common.response.Result;
import com.zhiji.smartbook.module.shared.dto.SharedBillCreateDTO;
import com.zhiji.smartbook.module.shared.dto.SharedBillItemCreateDTO;
import com.zhiji.smartbook.module.shared.service.SharedBillService;
import com.zhiji.smartbook.module.shared.vo.SharedBillDetailVO;
import com.zhiji.smartbook.module.shared.vo.SharedBillVO;
import com.zhiji.smartbook.module.shared.vo.SettlementResultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/shared-bills")
@RequiredArgsConstructor
public class SharedBillController {

    private final SharedBillService sharedBillService;

    @GetMapping
    public Result<List<SharedBillVO>> listSharedBills() {
        return Result.success(sharedBillService.listSharedBills());
    }

    @PostMapping
    public Result<Map<String, Object>> createSharedBill(@RequestBody SharedBillCreateDTO request,
                                                        @RequestHeader(value = "Idempotency-Key", required = false) String idempotencyKey) {
        return Result.success(sharedBillService.createSharedBill(request, idempotencyKey));
    }

    @GetMapping("/{sharedBillId}")
    public Result<SharedBillDetailVO> getSharedBillDetail(@PathVariable Long sharedBillId) {
        return Result.success(sharedBillService.getSharedBillDetail(sharedBillId));
    }

    @PostMapping("/{sharedBillId}/items")
    public Result<Map<String, Object>> createSharedBillItem(@PathVariable Long sharedBillId,
                                                            @RequestBody SharedBillItemCreateDTO request) {
        return Result.success(sharedBillService.createSharedBillItem(sharedBillId, request));
    }

    @GetMapping("/{sharedBillId}/settlements")
    public Result<SettlementResultVO> getSettlements(@PathVariable Long sharedBillId) {
        return Result.success(sharedBillService.getSettlements(sharedBillId));
    }

    @PostMapping("/{sharedBillId}/settlements/{settlementId}/confirm")
    public Result<Map<String, Object>> confirmSettlement(@PathVariable Long sharedBillId,
                                                         @PathVariable Long settlementId) {
        return Result.success(sharedBillService.confirmSettlement(sharedBillId, settlementId));
    }
}
