package third.weixin.controller.config;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Slf4j
@Controller
@RequestMapping("config")
public class ConfigController {

    @NacosValue(value = "${useLocalCache:false}", autoRefreshed = true)
    private boolean useLocalCache;

    @RequestMapping(value = "/getCache", method = GET)
    @ResponseBody
    public boolean get() {
        log.info("ConfigController cache========> {}","-=======================");
        return useLocalCache;
    }

    @RequestMapping(value = "/test", method = GET)
    @ResponseBody
    public boolean test() {
        System.out.println("tttttttttttttttttttttttttttttttt");
        log.info("test cache========> {}","-=======================");
        return useLocalCache;
    }
}
