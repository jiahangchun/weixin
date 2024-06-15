package third.feishu.controller.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : JIA
 */
@Data
public class ChatListDto implements Serializable {

    private String userId;

    private String pageToken;

    private Integer pageSize = 10;

}
