package third.feishu.controller.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : JIA
 */
@Data
public class CallbackReq implements Serializable {

    private String encrypt;
}
