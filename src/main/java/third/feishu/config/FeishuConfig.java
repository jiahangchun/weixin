package third.feishu.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "feishu")
public class FeishuConfig {

    private String appId = "cli_xxxx";
    private String appSecret = "xxxx";
    private String encryptKey = "xxxx";
    private String verificationToken = "xxxx";
}
