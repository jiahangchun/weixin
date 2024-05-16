package third.feishu.config;

import com.lark.oapi.core.utils.Jsons;
import com.lark.oapi.event.EventDispatcher;
import com.lark.oapi.service.contact.ContactService;
import com.lark.oapi.service.contact.v3.model.P2UserCreatedV3;
import com.lark.oapi.service.drive.DriveService;
import com.lark.oapi.service.drive.DriveService.P2FileBitableFieldChangedV1Handler;
import com.lark.oapi.service.drive.DriveService.P2FileDeletedV1Handler;
import com.lark.oapi.service.drive.DriveService.P2FileEditV1Handler;
import com.lark.oapi.service.drive.DriveService.P2FileTitleUpdatedV1Handler;
import com.lark.oapi.service.drive.DriveService.P2FileTrashedV1Handler;
import com.lark.oapi.service.drive.v1.model.P2FileBitableFieldChangedV1;
import com.lark.oapi.service.drive.v1.model.P2FileDeletedV1;
import com.lark.oapi.service.drive.v1.model.P2FileEditV1;
import com.lark.oapi.service.drive.v1.model.P2FileReadV1;
import com.lark.oapi.service.drive.v1.model.P2FileTitleUpdatedV1;
import com.lark.oapi.service.drive.v1.model.P2FileTrashedV1;
import com.lark.oapi.service.im.ImService;
import com.lark.oapi.service.im.v1.model.P1MessageReadV1;
import com.lark.oapi.service.im.v1.model.P2MessageReadV1;
import com.lark.oapi.service.im.v1.model.P2MessageReceiveV1;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.lark.oapi.ws.Client;
import third.weixin.utils.JsonUtils;

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

    private Client client = null;


    private static final String LOG_PRE = "===================>";


    @Override
    public void afterPropertiesSet() throws Exception {

    }

    public synchronized Client getWsClient() {
        if (Objects.nonNull(client)) {
            return client;
        }
        synchronized (ClientConfig.class) {
            if (Objects.nonNull(client)) {
                return client;
            }
            EventDispatcher eventDispatcher = this.getEventDispatcher();

            client = new Client.Builder(feishuConfig.getAppId(), feishuConfig.getAppSecret())
                .eventHandler(eventDispatcher)
                .build();
            client.start();
        }
        return client;
    }


    /**
     * @return
     */
    public EventDispatcher getEventDispatcher() {
        return EventDispatcher.newBuilder(feishuConfig.getVerificationToken(),
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
            }).onP2FileReadV1(new DriveService.P2FileReadV1Handler() {

                @Override
                public void handle(P2FileReadV1 event) throws Exception {
                    log.info(LOG_PRE + "onP2FileReadV1" + JsonUtils.toJson(event));
                }
            })
            .onP2FileEditV1(new P2FileEditV1Handler() {
                @Override
                public void handle(P2FileEditV1 event) throws Exception {
                    log.info(LOG_PRE + "onP2FileEditV1" + JsonUtils.toJson(event));
                }
            })
            .onP2FileDeletedV1(new P2FileDeletedV1Handler() {
                @Override
                public void handle(P2FileDeletedV1 event) throws Exception {
                    log.info(LOG_PRE + "onP2FileDeletedV1" + JsonUtils.toJson(event));
                }
            })
            .onP2FileBitableFieldChangedV1(new P2FileBitableFieldChangedV1Handler() {
                @Override
                public void handle(P2FileBitableFieldChangedV1 event) throws Exception {
                    log.info(LOG_PRE + "onP2FileBitableFieldChangedV1" + JsonUtils.toJson(event));
                }
            })
            .onP2FileTitleUpdatedV1(new P2FileTitleUpdatedV1Handler() {
                @Override
                public void handle(P2FileTitleUpdatedV1 event) throws Exception {
                    log.info(LOG_PRE + "onP2FileTitleUpdatedV1" + JsonUtils.toJson(event));
                }
            })
            .onP2FileTrashedV1(new P2FileTrashedV1Handler() {
                @Override
                public void handle(P2FileTrashedV1 event) throws Exception {
                    log.info(LOG_PRE + "onP2FileTrashedV1" + JsonUtils.toJson(event));
                }
            })
            .build();
    }

}
