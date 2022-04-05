package com.dahuaboke.blog.controller;

import com.dahuaboke.blog.model.Result;
import com.dahuaboke.blog.service.BlogService;
import com.dahuaboke.mvc.anno.MvcRequestMapping;
import com.dahuaboke.mvc.anno.MvcRequestParam;
import com.dahuaboke.mvc.anno.MvcRestController;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * auth: dahua
 * time: 2022/3/27 15:37
 */
@MvcRestController
public class ArticleController {

    @Autowired
    private BlogService blogService;

    @MvcRequestMapping("/search")
    public Result search(@MvcRequestParam("search") String search) {
        return Result.success(blogService.search(search));
    }

    @MvcRequestMapping("/getArticle")
    public Result getArticle(@MvcRequestParam("id") String id) {
        try {
            return Result.success(blogService.getArticle(id));
        } catch (IOException e) {
            System.out.println(e);
        }
        return Result.error();
    }

    @MvcRequestMapping("/getComment")
    public Result getComment(@MvcRequestParam("id") String id) {
        try {
            return Result.success(blogService.getComment(id));
        } catch (IOException e) {
            System.out.println(e);
        }
        return Result.error();
    }

    @MvcRequestMapping("/setComment")
    public void setComment(@MvcRequestParam("id") String id, @MvcRequestParam("date") String date, @MvcRequestParam("comment") String comment) {
        try {
            blogService.setComment(id, date, comment);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
