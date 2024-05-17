package third.feishu.config;

import com.lark.oapi.Client;
import com.lark.oapi.sdk.servlet.ext.ServletAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : JIA
 */
@Configuration
public class CallbackConfig {

    @Autowired
    private FeishuConfig feishuConfig;

    /**
     * 自建应用获取处理器
     * @return
     */
    @Bean
    public Client getClient() {
        return Client.newBuilder(feishuConfig.getAppId(), feishuConfig.getAppSecret()).build();
    }


    @Bean
    public ServletAdapter getServletAdapter() {
        return new ServletAdapter();
    }
}
