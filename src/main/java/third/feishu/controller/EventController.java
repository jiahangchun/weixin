package third.feishu.controller;

import static com.lark.oapi.sdk.servlet.ext.ServletAdapter.HTTP_TRANSLATOR;

import cn.hutool.json.JSONUtil;
import com.lark.oapi.Client;
import com.lark.oapi.core.request.EventReq;
import com.lark.oapi.core.response.EventResp;
import com.lark.oapi.sdk.servlet.ext.HttpTranslator;
import com.lark.oapi.sdk.servlet.ext.ServletAdapter;
import com.lark.oapi.service.im.v1.model.CreateMessageReq;
import com.lark.oapi.service.im.v1.model.CreateMessageReqBody;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import third.feishu.config.ClientConfig;
import third.feishu.config.FeishuConfig;
import third.feishu.controller.dto.CallbackDto;
import third.feishu.controller.dto.CallbackReq;
import third.feishu.controller.dto.CallbackResponse;
import third.feishu.message.Decrypt;
import third.weixin.utils.JsonUtils;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : JIA
 */
@RestController
@Slf4j
public class EventController {

    @Autowired
    private ClientConfig clientConfig;

    @Autowired
    private ServletAdapter servletAdapter;

    @Autowired
    private FeishuConfig feishuConfig;

    /**
     * 事件路由器
     *
     * @param request
     * @param response
     * @throws Throwable
     */
    @RequestMapping(value = "/webhook/event", method = RequestMethod.GET)
    public void event(@RequestParam String sendMsg) {
        Client client = clientConfig.getClient();
        // 发送消息
//        CreateMessageReq createMessageReq = CreateMessageReq.newBuilder()
//            .receiveIdType(request.getReceiveIdType())
//            .createMessageReqBody(CreateMessageReqBody.newBuilder()
//                .receiveId(request.getReceiveId())
//                .msgType("file")
//                .content(Jsons.DEFAULT.toJson(createFileResp.getData()))
//                .uuid(request.getUuid())
//                .build())
//            .build();
//
//        CreateMessageResp createMessageResp = client.im().message().create(createMessageReq);
//        if (!createMessageResp.success()) {
//            System.out.printf("client.im.message.create failed, code: %d, msg: %s, logId: %s%n",
//                createMessageResp.getCode(), createMessageResp.getMsg(), createMessageResp.getRequestId());
//            return createMessageResp;
//        }
//
//        // 返回结果
//        SendFileResponse response = new SendFileResponse();
//        response.setCode(0);
//        response.setMsg("success");
//        response.setCreateFileResponse(createFileResp.getData());
//        response.setCreateMessageResponse(createMessageResp.getData());

    }

    /**
     * 消息回调验证
     *
     * @param callbackReq
     * @return
     */
    @RequestMapping(value = "/webhook/callback", method = RequestMethod.POST)
    public CallbackResponse callback(@RequestBody CallbackReq callbackReq) {
        try {
            String value = callbackReq.getEncrypt();
            Decrypt d = new Decrypt(feishuConfig.getEncryptKey());

            String a = d.decrypt(value);
            log.info("callback result {}", a);
            if(a.contains("challenge")){
                CallbackDto callbackDto = JSONUtil.toBean(a, CallbackDto.class);
                CallbackResponse callbackResponse = new CallbackResponse();
                callbackResponse.setChallenge(callbackDto.getChallenge());
                return callbackResponse;
            }else{

            }

        } catch (Exception e) {
            log.error("callback {}", e.getMessage(), e);
            return null;
        }
    }


    /**
     * 卡片路由器
     *
     * @param request
     * @param response
     * @throws Throwable
     */
    @RequestMapping("/webhook/card")
    public void card(HttpServletRequest request, HttpServletResponse response)
        throws Throwable {

        EventReq req = new EventReq();
        req.setHeaders(toHeaderMap(request));
        req.setBody(bodyStr.getBytes(StandardCharsets.UTF_8));
        req.setHttpPath(request.getRequestURI());

        // 处理请求
        EventResp resp = handler.handle(eventReq);

        //3.1 回调扩展包卡片行为处理回调
        servletAdapter.handleCardAction(request, response, clientConfig.getCardActionHandler());
    }


}
