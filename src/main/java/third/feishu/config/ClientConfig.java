package third.feishu.config;

import com.lark.oapi.Client;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : JIA
 */
@Component
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

    public Client getClient() {
        return client;
    }
}
