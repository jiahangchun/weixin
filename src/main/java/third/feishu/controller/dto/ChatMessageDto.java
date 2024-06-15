package third.feishu.controller.dto;

import java.io.Serializable;
import java.util.UUID;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : JIA
 */
@Data
public class ChatMessageDto implements Serializable {

    private String message;

    private String userId;


    private String msgType = "text";

    private String seed= UUID.randomUUID().toString();


}
