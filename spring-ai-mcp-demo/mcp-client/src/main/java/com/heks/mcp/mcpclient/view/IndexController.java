package com.heks.mcp.mcpclient.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author singgel
 * 2025/03/11/上午10:51
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String chat(Model model) {
        //model.addAttribute("name", "User");
        // 返回视图名称，对应 templates/index.html
        return "index";
    }

}
