package third.weixin.config.single;

import com.google.common.collect.Lists;
import third.weixin.utils.JsonUtils;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Data
@ConfigurationProperties(prefix = "wechat.cp")
public class WxCpProperties {

    /**
     * 设置企业微信的corpId
     */
    private String corpId = "123456";

    private List<AppConfig> appConfigs = Lists.newArrayList();

    @Getter
    @Setter
    public static class AppConfig {

        /**
         * 设置企业微信应用的AgentId
         */
        private Integer agentId = 1;

        /**
         * 设置企业微信应用的Secret
         */
        private String secret = "2";

        /**
         * 设置企业微信应用的token
         */
        private String token = "3";

        /**
         * 设置企业微信应用的EncodingAESKey
         */
        private String aesKey = "4";

    }

    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }

}
