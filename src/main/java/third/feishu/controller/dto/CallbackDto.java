package third.feishu.controller.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : JIA
 */
@Data
public class CallbackDto implements Serializable {

    private String challenge;

    private String token;

    private String type;

}
