package third.feishu.message;

import com.lark.oapi.service.docx.v1.model.CreateDocumentReq;
import com.lark.oapi.service.docx.v1.model.CreateDocumentReqBody;
import com.lark.oapi.service.docx.v1.model.CreateDocumentResp;
import com.lark.oapi.service.docx.v1.model.CreateDocumentRespBody;
import java.io.Serializable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import third.feishu.config.ClientConfig;
import third.weixin.utils.JsonUtils;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : JIA
 */
@Component
@Slf4j
public class MessageService implements Serializable {

    @Autowired
    private ClientConfig clientConfig;

    public void sendMessage(String title,String folderToken) throws Exception {
        // 发起请求
        CreateDocumentResp resp = clientConfig.getClient().docx().document()
            .create(CreateDocumentReq.newBuilder()
                .createDocumentReqBody(CreateDocumentReqBody.newBuilder()
                    .title("title")
                    .folderToken("fldcniHf40Vcv1DoEc8SXeuA0Zd")
                    .build())
                .build()
            );

        if (!resp.success()) {
            log.error(
                String.format("code:%s,msg:%s,reqId:%s"
                , resp.getCode(), resp.getMsg(), resp.getRequestId()));
        }

        CreateDocumentRespBody data=resp.getData();
        log.info("result {}", JsonUtils.toJson(data));
    }


}
