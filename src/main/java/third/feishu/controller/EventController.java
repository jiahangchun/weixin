package third.feishu.controller;

import com.lark.oapi.core.utils.Jsons;
import com.lark.oapi.event.EventDispatcher;
import com.lark.oapi.sdk.servlet.ext.ServletAdapter;
import com.lark.oapi.service.contact.ContactService;
import com.lark.oapi.service.contact.v3.model.P2UserCreatedV3;
import com.lark.oapi.service.im.ImService;
import com.lark.oapi.service.im.v1.model.P1MessageReadV1;
import com.lark.oapi.service.im.v1.model.P2MessageReadV1;
import com.lark.oapi.service.im.v1.model.P2MessageReceiveV1;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import third.feishu.config.FeishuConfig;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : JIA
 */
@RestController
@Slf4j
public class EventController {

    //2. 注入 ServletAdapter 实例
    @Autowired
    private ServletAdapter servletAdapter;
    @Autowired
    private FeishuConfig feishuConfig;

    //1. 注册消息处理器
    private final EventDispatcher EVENT_DISPATCHER
        = EventDispatcher.newBuilder(feishuConfig.getVerificationToken(),
            feishuConfig.getEncryptKey())
        .onP2MessageReceiveV1(new ImService.P2MessageReceiveV1Handler() {
            @Override
            public void handle(P2MessageReceiveV1 event) {
                log.info(Jsons.DEFAULT.toJson(event));
            }
        }).onP2UserCreatedV3(new ContactService.P2UserCreatedV3Handler() {
            @Override
            public void handle(P2UserCreatedV3 event) {
                log.info(Jsons.DEFAULT.toJson(event));
                log.info(event.getRequestId());
            }
        })
        .onP2MessageReadV1(new ImService.P2MessageReadV1Handler() {
            @Override
            public void handle(P2MessageReadV1 event) {
                log.info(Jsons.DEFAULT.toJson(event));
                log.info(event.getRequestId());
            }
        }).onP1MessageReadV1(new ImService.P1MessageReadV1Handler() {
            @Override
            public void handle(P1MessageReadV1 event) {
                log.info(Jsons.DEFAULT.toJson(event));
                log.info(event.getRequestId());
            }
        })
        .build();



    //3. 创建路由处理器
    @RequestMapping("/webhook/event")
    public void event(HttpServletRequest request, HttpServletResponse response)
        throws Throwable {
        //3.1 回调扩展包提供的事件回调处理器
        servletAdapter.handleEvent(request, response, EVENT_DISPATCHER);
    }
}
