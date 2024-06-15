package third.feishu.controller;

import com.lark.oapi.Client;
import com.lark.oapi.core.request.RequestOptions;
import com.lark.oapi.core.utils.Jsons;
import com.lark.oapi.service.im.v1.enums.ListChatSortTypeEnum;
import com.lark.oapi.service.im.v1.enums.ListChatUserIdTypeEnum;
import com.lark.oapi.service.im.v1.model.ListChatReq;
import com.lark.oapi.service.im.v1.model.ListChatResp;
import java.io.Serializable;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import third.feishu.config.ClientConfig;
import third.feishu.controller.dto.BatchGetIdUserDto;
import third.feishu.controller.dto.ChatListDto;
import third.feishu.controller.dto.UserInfoResp;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : JIA
 */
@RestController(value = "group")
@Slf4j
public class GroupController implements Serializable {

    @Autowired
    private ClientConfig clientConfig;

    /**
     * 按照手机号 或者 邮箱，批量用户id
     *
     * @param dto
     * @throws Exception
     */
    @RequestMapping(value = "/chat/list", method = RequestMethod.GET)
    public void chatList(@RequestBody ChatListDto dto) throws Exception {
        Client client = clientConfig.getClient();

        // 创建请求对象
        // 发起请求
        ListChatResp resp = client.im().chat().list(
            ListChatReq.newBuilder().userIdType(ListChatUserIdTypeEnum.USER_ID)
                .pageSize(dto.getPageSize()).sortType(ListChatSortTypeEnum.BYACTIVETIMEDESC)
                .pageToken(dto.getPageToken()).build());

        // 处理服务端错误
        if (!resp.success()) {
            log.info(
                String.format("code:%s,msg:%s,reqId:%s", resp.getCode(), resp.getMsg(),
                    resp.getRequestId()));
            return;
        }

        // 业务数据处理
        log.info(Jsons.DEFAULT.toJson(resp.getData()));
    }
}
