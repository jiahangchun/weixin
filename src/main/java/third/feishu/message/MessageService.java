package third.feishu.message;

import java.io.Serializable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import third.feishu.config.ClientConfig;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : JIA
 */
@Component
@Slf4j
public class MessageService implements Serializable {

    @Autowired
    private ClientConfig clientConfig;

    public void sendMessage(String title,String folderToken) throws Exception {

    }


}
