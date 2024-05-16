package third.feishu.config;

import com.lark.oapi.Client;
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

    private EventDispatcher EventDispatcher = null;

    private Client client = null;


    @Override
    public void afterPropertiesSet() throws Exception {
        client = Client.newBuilder(feishuConfig.getAppId(), feishuConfig.getAppSecret())
            .logReqAtDebug(true)
            .build();
    }

    /**
     * @return
     */
    public synchronized EventDispatcher getEventDispatcher(){
        if(Objects.nonNull(EventDispatcher)){
            return EventDispatcher;
        }

        synchronized (ClientConfig.class){
            if(Objects.nonNull(EventDispatcher)){
                return EventDispatcher;
            }

            EventDispatcher
                = com.lark.oapi.event.EventDispatcher.newBuilder(feishuConfig.getVerificationToken(),
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
            return EventDispatcher;
        }
    }

    public Client getClient() {
        return client;
    }
}
