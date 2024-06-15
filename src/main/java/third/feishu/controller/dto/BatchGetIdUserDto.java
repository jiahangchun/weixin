package third.feishu.controller.dto;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : JIA
 */
@Data
public class BatchGetIdUserDto implements Serializable {

    private List<String> emailList;

    private List<String> mobileList;

    private String userType="user_id";


}
