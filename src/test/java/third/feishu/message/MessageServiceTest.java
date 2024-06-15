package third.feishu.message;

import com.google.common.collect.Lists;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import third.feishu.controller.ChatController;
import third.feishu.controller.dto.BatchGetIdUserDto;
import third.feishu.message.MessageService;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : JIA
 */
@SpringBootTest
public class MessageServiceTest {

    @Resource
    private MessageService messageService;
    @Resource
    private ChatController chatController;

    @Test
    public void a() throws Exception {
        messageService.sendMessage("","");
    }



}
