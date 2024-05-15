package third.feishu.config;

import com.lark.oapi.sdk.servlet.ext.ServletAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : JIA
 */
@Configuration
public class CallbackConfig {

    @Bean
    public ServletAdapter getServletAdapter() {
        return new ServletAdapter();
    }
}
