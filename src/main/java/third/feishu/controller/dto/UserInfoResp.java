package third.feishu.controller.dto;

import com.lark.oapi.service.contact.v3.model.UserStatus;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : JIA
 */
@Data
@Builder
public class UserInfoResp implements Serializable {

    private String userId;

    private String mobile;

    private String email;

    private UserStatus status;
}
