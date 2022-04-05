package com.dahuaboke.blog.controller;

import com.dahuaboke.blog.model.Article;
import com.dahuaboke.blog.model.Result;
import com.dahuaboke.blog.service.BlogService;
import com.dahuaboke.mvc.anno.MvcRequestMapping;
import com.dahuaboke.mvc.anno.MvcRequestParam;
import com.dahuaboke.mvc.anno.MvcRestController;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author dahua
 * @time 2022/3/28 9:19
 */
@MvcRestController
public class PageController {

    @Autowired
    private BlogService blogService;

    @MvcRequestMapping("/pages")
    public Result getPages() {
        return Result.success(blogService.getPages());
    }

    @MvcRequestMapping("/toPage")
    public Result toPage(@MvcRequestParam("page") int page, @MvcRequestParam("search") String search) {
        List<Article> list = blogService.toPage(page, search);
        return Result.success(list);
    }
}
