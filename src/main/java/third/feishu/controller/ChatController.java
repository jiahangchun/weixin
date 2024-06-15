package third.feishu.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.lark.oapi.Client;
import com.lark.oapi.core.utils.Jsons;
import com.lark.oapi.service.contact.v3.model.*;
import com.lark.oapi.service.im.v1.model.CreateMessageReq;
import com.lark.oapi.service.im.v1.model.CreateMessageReqBody;
import com.lark.oapi.service.im.v1.model.CreateMessageResp;
import java.util.HashMap;
import com.lark.oapi.core.request.RequestOptions;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import third.feishu.config.ClientConfig;
import third.feishu.controller.dto.BatchGetIdUserDto;
import third.feishu.controller.dto.ChatMessageDto;
import third.feishu.controller.dto.TextContextDto;
import third.feishu.controller.dto.UserInfoResp;
import third.weixin.utils.JsonUtils;

/**
 * Created by IntelliJ IDEA. 聊天相关接口，对外开放
 *
 * @Author : JIA
 */
@RestController(value = "chat")
@Slf4j
public class ChatController {

    @Autowired
    private ClientConfig clientConfig;

    /**
     * 按照手机号 或者 邮箱，批量用户id
     *
     * @param dto
     * @throws Exception
     */
    @RequestMapping(value = "/batch/get/id", method = RequestMethod.GET)
    public List<UserInfoResp> batchGetIdUser(@RequestBody BatchGetIdUserDto dto) throws Exception {
        Client client = clientConfig.getClient();

        //配置参数
        BatchGetIdUserReqBody body = BatchGetIdUserReqBody.newBuilder()
            .includeResigned(true)
            .build();
        List<String> emailList = dto.getEmailList();
        if (CollectionUtil.isNotEmpty(emailList)) {
            body.setEmails(emailList.toArray(new String[0]));
        } else {
            body.setEmails(new String[]{});
        }
        List<String> mobileList = dto.getMobileList();
        if (CollectionUtil.isNotEmpty(mobileList)) {
            body.setMobiles(mobileList.toArray(new String[0]));
        }
        //发起请求
        BatchGetIdUserReq req = BatchGetIdUserReq.newBuilder()
            .batchGetIdUserReqBody(body)
            .userIdType(dto.getUserType())
            .build();

        // 发起请求
        BatchGetIdUserResp resp = client.contact().user().batchGetId(req);

        // 处理服务端错误
        if (!resp.success()) {
            System.out.println(
                String.format("code:%s,msg:%s,reqId:%s", resp.getCode(), resp.getMsg(),
                    resp.getRequestId()));
            return Lists.newArrayList();
        }
        BatchGetIdUserRespBody respData = resp.getData();
        if (Objects.isNull(respData)) {
            return Lists.newArrayList();
        }
        UserContactInfo[] userList = respData.getUserList();
        if (Objects.isNull(userList)) {
            return Lists.newArrayList();
        }
        List<UserInfoResp> userInfoList = Lists.newArrayList();
        for (UserContactInfo userContactInfo : userList) {
            String userId = userContactInfo.getUserId();
            String mobile = userContactInfo.getMobile();
            String email = userContactInfo.getEmail();
            UserStatus status = userContactInfo.getStatus();
            userInfoList.add(
                UserInfoResp.builder().userId(userId).mobile(mobile).email(email).status(status)
                    .build());
        }
        return userInfoList;
    }

    /**
     * 向某人发送指定消息
     */
    @RequestMapping(value = "/send/chat/message", method = RequestMethod.GET)
    public void sendChatMessage(@RequestBody ChatMessageDto chatMessageDto) throws Exception {
        // 构建client
        Client client = clientConfig.getClient();

        // 创建请求对象
        CreateMessageReq req = CreateMessageReq.newBuilder()
            .createMessageReqBody(CreateMessageReqBody.newBuilder()
                .receiveId(chatMessageDto.getUserId())
                .msgType(chatMessageDto.getMsgType())
                .content(
                    this.formatContent(chatMessageDto.getMsgType(), chatMessageDto.getMessage()))
                .uuid(chatMessageDto.getSeed())
                .build())
            .build();
        req.setReceiveIdType("user_id");

        // 发起请求
        CreateMessageResp resp = client.im().message().create(req);

        // 处理服务端错误
        if (!resp.success()) {
            System.out.println(
                String.format("code:%s,msg:%s,reqId:%s", resp.getCode(), resp.getMsg(),
                    resp.getRequestId()));
            return;
        }

        // 业务数据处理
        log.info("===============>" + Jsons.DEFAULT.toJson(resp.getData()));
    }

    /**
     * 发送text文本格式的消息
     *
     * @param msgType
     * @param value
     * @return
     */
    private String formatContent(String msgType, String value) {
        switch (msgType) {
            case "text": {
                TextContextDto textContextDto = new TextContextDto();
                textContextDto.setText(value);
                return JsonUtils.toJson(textContextDto);
            }
            default:
                return "";
        }
    }

}
