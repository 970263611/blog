package com.dahuaboke.blog.model;

/**
 * auth: dahua
 * time: 2022/3/27 15:42
 */
public class Article {

    private String id;
    private String title;
    private String content;
    private String type;
    private String see;
    private String top;
    private String createTime;

    public Article() {
    }

    public Article(String id, String title, String content, String type, String top, String see, String createTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.type = type;
        this.top = top;
        this.see = see;
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSee() {
        return see;
    }

    public void setSee(String see) {
        this.see = see;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
