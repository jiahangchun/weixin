package third.feishu.controller;

import com.lark.oapi.sdk.servlet.ext.ServletAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import third.feishu.config.ClientConfig;

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

    /**
     * 事件路由器
     *
     * @param request
     * @param response
     * @throws Throwable
     */
    @RequestMapping("/webhook/event")
    public void event(HttpServletRequest request, HttpServletResponse response)
        throws Throwable {
        servletAdapter.handleEvent(request, response, clientConfig.getEventDispatch());
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
        //3.1 回调扩展包卡片行为处理回调
        servletAdapter.handleCardAction(request, response, clientConfig.getCardActionHandler());
    }


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(@RequestParam String value)
        throws Throwable {
        return "test" + value;
    }

}
