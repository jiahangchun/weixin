package third.feishu.config;

import com.lark.oapi.Client;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import third.weixin.config.single.WxCpProperties;
import third.weixin.handler.LogHandler;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : JIA
 */
@Data
@Configuration
@EnableConfigurationProperties(FeishuConfig.class)
public class ClientConfig implements InitializingBean {

    private FeishuConfig feishuConfig;

    private Client client = null;

    @Autowired
    public ClientConfig(FeishuConfig feishuConfig) {
        this.feishuConfig = feishuConfig;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        client = Client.newBuilder(feishuConfig.getAppId(), feishuConfig.getAppSecret())
            .logReqAtDebug(true)
            .build();
    }

}
