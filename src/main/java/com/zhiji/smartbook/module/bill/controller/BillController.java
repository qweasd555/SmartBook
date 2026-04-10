package com.zhiji.smartbook.module.bill.controller;

import com.zhiji.smartbook.common.response.Result;
import com.zhiji.smartbook.module.bill.dto.BillCreateDTO;
import com.zhiji.smartbook.module.bill.dto.BillUpdateDTO;
import com.zhiji.smartbook.module.bill.service.BillService;
import com.zhiji.smartbook.module.bill.vo.BillDetailVO;
import com.zhiji.smartbook.module.bill.vo.BillGroupByDateVO;
import com.zhiji.smartbook.module.bill.vo.BillListItemVO;
import com.zhiji.smartbook.module.bill.vo.RecentBillVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;

    @PostMapping
    public Result<Map<String, Object>> createBill(@RequestBody BillCreateDTO request,
                                                  @RequestHeader(value = "Idempotency-Key", required = false) String idempotencyKey) {
        return Result.success(billService.createBill(request, idempotencyKey));
    }

    @PutMapping("/{transactionId}")
    public Result<BillDetailVO> updateBill(@PathVariable Long transactionId, @RequestBody BillUpdateDTO request) {
        return Result.success(billService.updateBill(transactionId, request));
    }

    @DeleteMapping("/{transactionId}")
    public Result<Void> deleteBill(@PathVariable Long transactionId) {
        billService.deleteBill(transactionId);
        return Result.success();
    }

    @GetMapping("/{transactionId}")
    public Result<BillDetailVO> getBillDetail(@PathVariable Long transactionId) {
        return Result.success(billService.getBillDetail(transactionId));
    }

    @GetMapping
    public Result<Map<String, Object>> getBills(@RequestParam(defaultValue = "1") Integer pageNo,
                                                @RequestParam(defaultValue = "20") Integer pageSize,
                                                @RequestParam Long ledgerId,
                                                @RequestParam(required = false) String range,
                                                @RequestParam(required = false) String type,
                                                @RequestParam(required = false) Long categoryId) {
        return Result.success(billService.getBills(pageNo, pageSize, ledgerId, range, type, categoryId));
    }

    @GetMapping("/group-by-date")
    public Result<List<BillGroupByDateVO>> groupByDate(@RequestParam Long ledgerId,
                                                       @RequestParam String startDate,
                                                       @RequestParam String endDate) {
        return Result.success(billService.groupByDate(ledgerId, startDate, endDate));
    }

    @GetMapping("/recent")
    public Result<List<RecentBillVO>> getRecentBills(@RequestParam Long ledgerId,
                                                     @RequestParam(defaultValue = "5") Integer limit) {
        return Result.success(billService.getRecentBills(ledgerId, limit));
    }
}
