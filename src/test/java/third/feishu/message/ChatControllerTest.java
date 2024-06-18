package third.feishu.message;

import com.google.common.collect.Lists;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import third.feishu.controller.ChatController;
import third.feishu.controller.dto.BatchGetIdUserDto;
import third.feishu.controller.dto.ChatMessageDto;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : JIA
 */

@SpringBootTest
public class ChatControllerTest {


    @Autowired
    private ChatController chatController;

    @Test
    public void sendChatMessage() throws Exception {
        ChatMessageDto dto=new ChatMessageDto();
        dto.setMessage("hello world");
        dto.setUserId("cbe8c91c");
        dto.setSeed(UUID.randomUUID().toString());
        chatController.sendChatMessage(dto);
    }



    @Test
    public void sendImageMessage() throws Exception {
        ChatMessageDto dto=new ChatMessageDto();
        dto.setMessage("img_v3_02bv_c9b3efc1-10a8-4262-8628-66e2ce02d2eg");
        dto.setUserId("cbe8c91c");
        dto.setMsgType("image");
        dto.setSeed(UUID.randomUUID().toString());
//        chatController.sendImageMessage(dto);
        chatController.sendChatMessage(dto);
    }



    @Test
    public void batchGetIdUser() throws Exception {
        BatchGetIdUserDto dto=new BatchGetIdUserDto();
        dto.setMobileList(Lists.newArrayList("15700082376"));
        chatController.batchGetIdUser(dto);
    }
}
