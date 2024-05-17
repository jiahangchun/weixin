package third.feishu.config;

import com.lark.oapi.card.CardActionHandler;
import com.lark.oapi.card.model.CardAction;
import com.lark.oapi.card.model.MessageCard;
import com.lark.oapi.core.utils.Jsons;
import com.lark.oapi.event.EventDispatcher;
import com.lark.oapi.service.contact.ContactService;
import com.lark.oapi.service.contact.v3.model.P2UserCreatedV3;
import com.lark.oapi.service.im.ImService;
import com.lark.oapi.service.im.v1.model.P1MessageReadV1;
import com.lark.oapi.service.im.v1.model.P2MessageReadV1;
import com.lark.oapi.service.im.v1.model.P2MessageReceiveV1;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : JIA
 */
@Component
@Slf4j
public class ClientConfig implements InitializingBean {

    @Autowired
    private FeishuConfig feishuConfig;

    private EventDispatcher eventDispatcher;

    private CardActionHandler cardActionHandler;


    private static final String LOG_PRE = "===================>";


    /**
     * 卡片处理器
     *
     * @return
     */
    public synchronized CardActionHandler getCardActionHandler() {
        if (Objects.nonNull(cardActionHandler)) {
            return cardActionHandler;
        }
        synchronized (ClientConfig.class) {
            if (Objects.nonNull(cardActionHandler)) {
                return cardActionHandler;
            }
            cardActionHandler = CardActionHandler.newBuilder("v", "e",
                new CardActionHandler.ICardHandler() {
                    @Override
                    public Object handle(CardAction cardAction) {
                        System.out.println(LOG_PRE+Jsons.DEFAULT.toJson(cardAction));
                        System.out.println(LOG_PRE+cardAction.getRequestId());

                        return null;
                        // 1.2 构建响应卡片内容
//                        MessageCard card = MessageCard.newBuilder()
//                            .cardLink("wwww.baidu.com")
//                            .config(config)
//                            .header(header)
//                            .elements(new MessageCardElement[]{div, note, image, cardAction, hr})
//                            .build();
//                        return card;
                    }
                }).build();
            return cardActionHandler;
        }
    }

    /**
     * 获取事件处理器
     *
     * @return
     */
    public synchronized EventDispatcher getEventDispatch() {
        if (Objects.nonNull(eventDispatcher)) {
            return eventDispatcher;
        }
        synchronized (ClientConfig.class) {
            if (Objects.nonNull(eventDispatcher)) {
                return eventDispatcher;
            }
            eventDispatcher = EventDispatcher.newBuilder(feishuConfig.getVerificationToken(),
                    feishuConfig.getEncryptKey())
                .onP2MessageReceiveV1(new ImService.P2MessageReceiveV1Handler() {
                    @Override
                    public void handle(P2MessageReceiveV1 event) {
                        System.out.println(LOG_PRE + Jsons.DEFAULT.toJson(event));
                        System.out.println(LOG_PRE + event.getRequestId());



                    }
                }).onP2UserCreatedV3(new ContactService.P2UserCreatedV3Handler() {
                    @Override
                    public void handle(P2UserCreatedV3 event) {
                        System.out.println(LOG_PRE + Jsons.DEFAULT.toJson(event));
                        System.out.println(LOG_PRE + event.getRequestId());
                    }
                })
                .onP2MessageReadV1(new ImService.P2MessageReadV1Handler() {
                    @Override
                    public void handle(P2MessageReadV1 event) {
                        System.out.println(LOG_PRE + Jsons.DEFAULT.toJson(event));
                        System.out.println(LOG_PRE + event.getRequestId());
                    }
                }).onP1MessageReadV1(new ImService.P1MessageReadV1Handler() {
                    @Override
                    public void handle(P1MessageReadV1 event) {
                        System.out.println(LOG_PRE + Jsons.DEFAULT.toJson(event));
                        System.out.println(LOG_PRE + event.getRequestId());
                    }
                })
                .build();
            return eventDispatcher;
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
