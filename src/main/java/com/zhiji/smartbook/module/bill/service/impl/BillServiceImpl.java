package com.zhiji.smartbook.module.bill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhiji.smartbook.common.response.ListResult;
import com.zhiji.smartbook.common.response.Result;
import com.zhiji.smartbook.module.bill.dto.BillCreateDTO;
import com.zhiji.smartbook.module.bill.dto.BillUpdateDTO;
import com.zhiji.smartbook.module.bill.entity.Bill;
import com.zhiji.smartbook.module.bill.mapper.BillMapper;
import com.zhiji.smartbook.module.bill.service.BillService;
import com.zhiji.smartbook.module.bill.vo.BillDetailVO;
import com.zhiji.smartbook.module.bill.vo.BillGroupByDateVO;
import com.zhiji.smartbook.module.bill.vo.BillListItemVO;
import com.zhiji.smartbook.module.bill.vo.RecentBillVO;
import com.zhiji.smartbook.common.utils.UserContext;
import com.zhiji.smartbook.module.user.vo.UserDashboardVO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private final BillMapper billMapper;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Map<String, Object> createBill(BillCreateDTO request, String idempotencyKey) {
       // Long userId = UserContext.get();
       Long userId = 1L;
        Bill bill = new Bill();
        bill.setUserId(userId);
        bill.setType("EXPENSE".equals(request.getType()) ? 0 : 1);
        bill.setAmount(BigDecimal.valueOf(request.getAmount()));
        bill.setCategoryName(request.getCategoryName() != null ? request.getCategoryName() : "其他");
        bill.setMerchantName(request.getMerchantName());
        bill.setOccurredAt(request.getOccurredAt() != null
                ? LocalDateTime.parse(request.getOccurredAt(), FMT) : LocalDateTime.now());
        bill.setRemark(request.getRemark());
        bill.setReceiptImageUrl(request.getReceiptImageUrl());
        bill.setStatus(0);
        billMapper.insert(bill);
        return Map.of("transactionId", bill.getId());
    }

    @Override
    public BillDetailVO updateBill(Long transactionId, BillUpdateDTO request) {
        Bill bill = billMapper.selectById(transactionId);
        if (bill == null || !bill.getUserId().equals(1L)) return null;
        if (request.getType() != null) bill.setType("EXPENSE".equals(request.getType()) ? 0 : 1);
        if (request.getAmount() != null) bill.setAmount(BigDecimal.valueOf(request.getAmount()));
        if (request.getMerchantName() != null) bill.setMerchantName(request.getMerchantName());
        if (request.getOccurredAt() != null) bill.setOccurredAt(LocalDateTime.parse(request.getOccurredAt(), FMT));
        if (request.getRemark() != null) bill.setRemark(request.getRemark());
        billMapper.updateById(bill);
        return toDetailVO(bill);
    }

    @Override
    public void deleteBill(Long transactionId) {
        Bill bill = new Bill();
        bill.setId(transactionId);
        bill.setStatus(1);
        billMapper.updateById(bill);
    }

    @Override
    public BillDetailVO getBillDetail(Long transactionId) {
        Bill bill = billMapper.selectById(transactionId);
        return toDetailVO(bill);
    }

//    @Override
//    public Map<String, Object> getBills(Integer pageNo, Integer pageSize, Long ledgerId,
//                                        String range, String type, Long categoryId) {
//        String[] range2Time = resolveRange(range);
//        Page<BillListItemVO> page = new Page<>(pageNo, pageSize);
//        Integer typeValue = null;
//
//        if (type != null) {
//            typeValue = "EXPENSE".equals(type) ? 0 : 1;
//        }
//        List<BillListItemVO> list = billMapper.selectBillPage(
//                UserContext.get(),
//                range2Time[0],
//                range2Time[1],
//                typeValue,
//                null);
//        Map<String, Object> result = new LinkedHashMap<>();
//        result.put("pageNo", pageNo);
//        result.put("pageSize", pageSize);
//        result.put("total", list.size());
//        int from = (int) page.offset();
//        int to = Math.min(from + pageSize, list.size());
//        result.put("list", from < list.size() ? list.subList(from, to) : List.of());
//        return result;
//    }

    @Override
    public Map<String, Object> getBills(Integer pageNo, Integer pageSize, Long ledgerId,
                                        String range, String type, Long categoryId) {
        String[] range2Time = resolveRange(range);
        Integer typeValue = null;
        if (type != null) {
            typeValue = "EXPENSE".equals(type) ? 0 : 1;
        }

        // 直接查列表，不用手动分页
        List<BillListItemVO> list = billMapper.selectBillPage(
                1L, // 写死用户ID
                range2Time[0],
                range2Time[1],
                typeValue,
                null);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("pageNo", pageNo);
        result.put("pageSize", pageSize);
        result.put("total", list.size());
        result.put("list", list);
        return result;
    }

//    @Override
//    public List<BillGroupByDateVO> groupByDate(Long ledgerId, String startDate, String endDate) {
//        return billMapper.selectGroupByDate(1L,
//                startDate + " 00:00:00", endDate + " 23:59:59");
//    }
//
// 路径：com.zhiji.smartbook.module.bill.service.impl.BillServiceImpl.java
// ✅ 现在 @Override 就不会报错了
@Override
public List<BillGroupByDateVO> groupByDate(Long ledgerId, String startDate, String endDate, String categoryName) {
    String startTime = startDate != null ? startDate + " 00:00:00" : null;
    String endTime = endDate != null ? endDate + " 23:59:59" : null;

    return billMapper.selectGroupByDate(1L, startTime, endTime, categoryName);
}

    @Override
    public List<RecentBillVO> getRecentBills(Long ledgerId, Integer limit) {
        LambdaQueryWrapper<Bill> wrapper = new LambdaQueryWrapper<Bill>()
                .eq(Bill::getUserId, 1L)
                .eq(Bill::getStatus, 0)
                .orderByDesc(Bill::getOccurredAt)
                .last("LIMIT " + limit);
        return billMapper.selectList(wrapper).stream().map(this::toRecentVO).collect(Collectors.toList());
    }

    private String[] resolveRange(String range) {
        LocalDateTime now = LocalDateTime.now();
        String end = now.format(FMT);
        String start = switch (range == null ? "TODAY" : range) {
            case "WEEK" -> now.minusDays(6).withHour(0).withMinute(0).withSecond(0).format(FMT);
            case "MONTH" -> now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).format(FMT);
            default -> now.withHour(0).withMinute(0).withSecond(0).format(FMT);
        };
        return new String[]{start, end};
    }

    private BillDetailVO toDetailVO(Bill bill) {
        if (bill == null) return null;
        BillDetailVO vo = new BillDetailVO();
        vo.setId(bill.getId());

        //vo.setType(bill.getType() == 0 ? "EXPENSE" : "INCOME");
        // 原来的字符串 type 改成数字
        vo.setType(bill.getType()); // bill.getType() 是 0/1，直接 set

        vo.setAmount(bill.getAmount());
        vo.setCategoryName(bill.getCategoryName());
        vo.setMerchantName(bill.getMerchantName());
        vo.setOccurredAt(bill.getOccurredAt() != null ? bill.getOccurredAt().format(FMT) : null);
        vo.setRemark(bill.getRemark());
        vo.setReceiptImageUrl(bill.getReceiptImageUrl());
        // vo.setSource(bill.getOcrTaskId() != null ? "OCR" : "MANUAL");
        return vo;
    }

    private RecentBillVO toRecentVO(Bill bill) {
        RecentBillVO vo = new RecentBillVO();
        vo.setId(bill.getId());
        vo.setType(bill.getType());
        vo.setAmount(bill.getAmount());
        vo.setCategoryName(bill.getCategoryName());
        vo.setMerchantName(bill.getMerchantName());
        vo.setOccurredAt(bill.getOccurredAt() != null ? bill.getOccurredAt().format(FMT) : null);
        vo.setSource(bill.getOcrTaskId() != null ? "OCR" : "MANUAL");
        return vo;
    }


    @Override
    public Map<String, BigDecimal> getTotalAmount(String startTime, String endTime) {
        // 直接传3个参数给 Mapper，不用改 Mapper
        Map<String, BigDecimal> result = billMapper.selectTotalAmount(1L, startTime, endTime);
        return result != null ? result : new HashMap<>();
    }
    // 路径：com.zhiji.smartbook.module.user.service.impl.UserServiceImpl.java


}
