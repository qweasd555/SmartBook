//package com.zhiji.smartbook.module.bill.controller;
//
//import com.zhiji.smartbook.common.response.Result;
//import com.zhiji.smartbook.module.bill.dto.BillCreateDTO;
//import com.zhiji.smartbook.module.bill.dto.BillUpdateDTO;
//import com.zhiji.smartbook.module.bill.service.BillService;
//import com.zhiji.smartbook.module.bill.vo.BillDetailVO;
//import com.zhiji.smartbook.module.bill.vo.BillGroupByDateVO;
//import com.zhiji.smartbook.module.bill.vo.BillListItemVO;
//import com.zhiji.smartbook.module.bill.vo.RecentBillVO;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/transactions")
//@RequiredArgsConstructor
//public class BillController {
//
//    private final BillService billService;
//
//    @PostMapping
//    public Result<Map<String, Object>> createBill(@RequestBody BillCreateDTO request,
//                                                  @RequestHeader(value = "Idempotency-Key", required = false) String idempotencyKey) {
//        return Result.success(billService.createBill(request, idempotencyKey));
//    }
//
//    @PutMapping("/{transactionId}")
//    public Result<BillDetailVO> updateBill(@PathVariable Long transactionId, @RequestBody BillUpdateDTO request) {
//        return Result.success(billService.updateBill(transactionId, request));
//    }
//
//    @DeleteMapping("/{transactionId}")
//    public Result<Void> deleteBill(@PathVariable Long transactionId) {
//        billService.deleteBill(transactionId);
//        return Result.success();
//    }
//
//    @GetMapping("/{transactionId}")
//    public Result<BillDetailVO> getBillDetail(@PathVariable Long transactionId) {
//        return Result.success(billService.getBillDetail(transactionId));
//    }
//
//    @GetMapping
//    public Result<Map<String, Object>> getBills(
//            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
//            @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize,
//            @RequestParam(name = "ledgerId") Long ledgerId,
//            @RequestParam(name = "range", required = false) String range,
//            @RequestParam(name = "type", required = false) String type,
//            @RequestParam(name = "categoryId", required = false) Long categoryId) {
//        return Result.success(billService.getBills(pageNo, pageSize, ledgerId, range, type, categoryId));
//    }
//
//    @GetMapping("/group-by-date")
//    public Result<List<BillGroupByDateVO>> groupByDate(
//            @RequestParam(name = "ledgerId") Long ledgerId,
//            @RequestParam(name = "startDate") String startDate,
//            @RequestParam(name = "endDate") String endDate) {
//        return Result.success(billService.groupByDate(ledgerId, startDate, endDate));
//    }
//
//    @GetMapping("/recent")
//    public Result<List<RecentBillVO>> getRecentBills(
//            @RequestParam(name = "ledgerId") Long ledgerId,
//            @RequestParam(name = "limit", defaultValue = "5") Integer limit) {
//        return Result.success(billService.getRecentBills(ledgerId, limit));
//    }
//    // 加在类的最后面
//    @GetMapping("/test")
//    public Result<String> testController(){
//        return Result.success("BillController 正常！接口路径是 /api/transactions");
//    }
//}
package com.zhiji.smartbook.module.bill.controller;

import com.zhiji.smartbook.common.response.ListResult;
import com.zhiji.smartbook.common.response.Result;
import com.zhiji.smartbook.module.bill.dto.BillCreateDTO;
import com.zhiji.smartbook.module.bill.dto.BillUpdateDTO;
import com.zhiji.smartbook.module.bill.service.BillService;
import com.zhiji.smartbook.module.bill.vo.BillDetailVO;
import com.zhiji.smartbook.module.bill.vo.BillGroupByDateVO;
import com.zhiji.smartbook.module.bill.vo.BillListItemVO;
import com.zhiji.smartbook.module.bill.vo.RecentBillVO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
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

    // 分页查询：手动拿参数，彻底绕过Spring参数校验
    @GetMapping
    public Result<Map<String, Object>> getBills(HttpServletRequest request) {
        // 动态接收参数，带默认值
        Integer pageNo = request.getParameter("pageNo") != null
                ? Integer.parseInt(request.getParameter("pageNo")) : 1;
        Integer pageSize = request.getParameter("pageSize") != null
                ? Integer.parseInt(request.getParameter("pageSize")) : 10;
        Long ledgerId = request.getParameter("ledgerId") != null
                ? Long.parseLong(request.getParameter("ledgerId")) : 1L;
        String range = request.getParameter("range"); // 支持 TODAY/WEEK/MONTH
        String type = request.getParameter("type");
        Long categoryId = request.getParameter("categoryId") != null
                ? Long.parseLong(request.getParameter("categoryId")) : null;

        return Result.success(billService.getBills(pageNo, pageSize, ledgerId, range, type, categoryId));
    }

    // 按日期分组：手动拿参数
    @GetMapping("/group-by-date")
    public Result<ListResult<BillGroupByDateVO>> groupByDate(HttpServletRequest request) {
        Long ledgerId = request.getParameter("ledgerId") != null
                ? Long.parseLong(request.getParameter("ledgerId")) : 1L;
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String categoryName = request.getParameter("categoryName");

        List<BillGroupByDateVO> list = billService.groupByDate(ledgerId, startDate, endDate, categoryName);
        // ✅ 必须用 ListResult 包装
        return Result.success(ListResult.of(list));
    }

    // 最近账单：手动拿参数
    @GetMapping("/recent")
    public Result<List<RecentBillVO>> getRecentBills(HttpServletRequest request) {
        Long ledgerId = request.getParameter("ledgerId") != null
                ? Long.parseLong(request.getParameter("ledgerId")) : 1L;
        Integer limit = request.getParameter("limit") != null
                ? Integer.parseInt(request.getParameter("limit")) : 5;

        return Result.success(billService.getRecentBills(ledgerId, limit));
    }

    // 测试接口
    @GetMapping("/test")
    public Result<String> testController(){
        return Result.success("BillController 正常！接口路径是 /api/transactions");
    }
}