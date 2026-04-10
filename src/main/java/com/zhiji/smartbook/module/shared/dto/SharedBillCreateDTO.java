package com.zhiji.smartbook.module.shared.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SharedBillCreateDTO {
    private String name;
    private String currency;
    private List<Map<String, Object>> members;
}
