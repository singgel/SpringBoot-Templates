package io.ymq.logback.controller;

import io.ymq.logback.config.annotation.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 描述:
 *
 * @author yanpenglei
 * @create 2017-10-30 14:20
 **/
@RestController
@RequestMapping(value = "/index")
public class IndexController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /**
     * http://127.0.0.1:8080/index/?content="我是测试内容"
     *
     * @param content
     * @return
     */
    @Log("首页IndexController")
    @RequestMapping(value="", method= RequestMethod.GET)
    public String index(@RequestParam String content) {
        LocalDateTime localDateTime = LocalDateTime.now();

        LOGGER.trace("请求参数：content:{}", content);
        LOGGER.debug("请求参数：content:{}", content);
        LOGGER.info("请求参数：content:{}", content);
        LOGGER.warn("请求参数：content:{}", content);
        LOGGER.error("请求参数：content:{}", content);

        return localDateTime + ",content:" + content;
    }
}
