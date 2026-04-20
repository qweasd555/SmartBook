package com.zhiji.smartbook.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListResult<T> {
    private List<T> list;

    // 静态工厂方法，方便使用
    public static <T> ListResult<T> of(List<T> list) {
        return new ListResult<>(list);
    }
}