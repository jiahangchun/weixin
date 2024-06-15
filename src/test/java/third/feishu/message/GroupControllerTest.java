package third.feishu.message;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import third.feishu.controller.ChatController;
import third.feishu.controller.GroupController;
import third.feishu.controller.dto.ChatListDto;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : JIA
 */
@SpringBootTest
public class GroupControllerTest {

    @Autowired
    private GroupController groupController;

    @Test
    public void chatList() throws Exception {
        ChatListDto chatListDto=new ChatListDto();
        groupController.chatList(chatListDto);

    }


}
