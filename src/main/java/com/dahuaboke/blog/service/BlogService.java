package com.dahuaboke.blog.service;

import com.dahuaboke.blog.dao.*;
import com.dahuaboke.blog.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * auth: dahua
 * time: 2022/3/28 21:01
 */
@Service
public class BlogService {

    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private SystemDao systemDao;
    @Autowired
    private LeaveMessageDao leaveMessageDao;
    @Autowired
    private TagDao tagDao;

    public List<Article> toPage(int page, String search) {
        List<String> articleNames = systemDao.getArticleNames(page, search);
        List<Article> articles = new ArrayList<>();
        buildArticle(articleNames, articles);
        return articles;
    }

    public int getPages() {
        return systemDao.getSize();
    }

    public void tag(String tag) throws IOException {
        tagDao.writeTag(tag);
    }

    public void leaveMessage(String leaveMessage) throws IOException {
        leaveMessageDao.writeLeaveMessage(leaveMessage);
    }

    public Object[] search(String search) {
        if ("".equals(search)) {
            Object[] result = new Object[2];
            result[0] = toPage(1, search);
            result[1] = getPages();
            return result;
        }
        Object[] result = systemDao.search(search);
        List<Article> articles = new LinkedList();
        List<String> names = (List<String>) result[0];
        buildArticle(names, articles);
        result[0] = articles;
        return result;
    }

    public void see(String id) throws IOException {
        systemDao.see(id);
    }

    private void buildArticle(List<String> names, List<Article> articles) {
        names.forEach(name -> {
            try {
                String[] split = name.split("#");
                Article article = articleDao.getArticle(split[0]);
                if (article != null) {
                    article.setTop(split[1]);
                    article.setSee(split[2]);
                    article.setCreateTime(split[3]);
                    article.setTitle(split[4]);
                    articles.add(article);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public Article getArticle(String id) throws IOException {
        if (id == null) {
            return null;
        }
        Article article = articleDao.getArticle(id);
        article.setTitle(systemDao.getTitle(id));
        return article;
    }

    public List<Map<String, String>> getComment(String id) throws IOException {
        if (id == null) {
            return null;
        }
        return commentDao.getComment(id);
    }

    public void setComment(String id, String date, String comment) throws IOException {
        commentDao.writeComment(id, date, comment);
    }
}
