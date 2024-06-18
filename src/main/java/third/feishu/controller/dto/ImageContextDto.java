package third.feishu.controller.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : JIA
 */
@Data
public class ImageContextDto implements Serializable {

    private String image_key;

    private String tag="img";
}
