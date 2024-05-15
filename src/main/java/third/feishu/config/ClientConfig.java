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

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : JIA
 */
@Data
@Configuration
@EnableConfigurationProperties(FeishuConfig.class)
public class ClientConfig implements InitializingBean {

    @Autowired
    private FeishuConfig feishuConfig;

    private Client client=null;


    @Override
    public void afterPropertiesSet() throws Exception {
        client = Client.newBuilder(feishuConfig.getAppId(), feishuConfig.getAppSecret())
            .logReqAtDebug(true)
            .build();
    }

}
