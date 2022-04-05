package com.dahuaboke.blog.controller;

import com.dahuaboke.blog.service.BlogService;
import com.dahuaboke.mvc.anno.MvcRequestMapping;
import com.dahuaboke.mvc.anno.MvcRequestParam;
import com.dahuaboke.mvc.anno.MvcRestController;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * @author dahua
 * @time 2022/3/28 16:08
 */
@MvcRestController
public class OtherController {

    @Autowired
    private BlogService blogService;

    @MvcRequestMapping("/tag")
    public void tag(@MvcRequestParam("tag") String tag) {
        try {
            blogService.tag(tag);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @MvcRequestMapping("/leaveMessage")
    public void leaveMessage(@MvcRequestParam("leaveMessage") String leaveMessage) {
        try {
            blogService.leaveMessage(leaveMessage);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @MvcRequestMapping("/see")
    public void see(@MvcRequestParam("id") String id) {
        try {
            blogService.see(id);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
