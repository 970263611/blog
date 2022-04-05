package com.dahuaboke.blog.controller;

import com.dahuaboke.blog.service.BlogService;
import com.dahuaboke.mvc.anno.MvcController;
import com.dahuaboke.mvc.anno.MvcRequestMapping;
import com.dahuaboke.mvc.anno.MvcRequestParam;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * auth: dahua
 * time: 2022/3/27 11:48
 */
@MvcController
public class HtmlController {

    @Autowired
    private BlogService blogService;

    @MvcRequestMapping("/")
    public String toIndex() {
        return "index";
    }

    @MvcRequestMapping("/index")
    public String index() {
        return "index";
    }

    @MvcRequestMapping("/article")
    public String article(@MvcRequestParam("id") String id) {
        if (id == null) {
            return null;
        }
        try {
            blogService.see(id);
        } catch (IOException e) {
            System.out.println(e);
        }
        return "article";
    }
}
