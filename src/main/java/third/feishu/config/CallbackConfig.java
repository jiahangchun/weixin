package third.feishu.config;

import com.lark.oapi.ws.Client;
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
    private ClientConfig clientConfig;

    @Bean
    public Client getClient() {
        return clientConfig.getWsClient();
    }
}
