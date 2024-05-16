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
import third.feishu.config.ClientConfig;
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
    private ClientConfig clientConfig;



    //3. 创建路由处理器
    @RequestMapping("/webhook/event")
    public void event(HttpServletRequest request, HttpServletResponse response)
        throws Throwable {
        //3.1 回调扩展包提供的事件回调处理器
        servletAdapter.handleEvent(request, response, clientConfig.getEventDispatcher());
    }
}
