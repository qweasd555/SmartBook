package com.zhiji.smartbook.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "sms.juhe")
public class SmsProperties {

    private boolean enabled;
    private String url;
    private String apiKey;
    private Integer tplId;
    private String ext;
}
