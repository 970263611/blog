package com.dahuaboke.blog.dao;

import com.dahuaboke.blog.consts.BlogConst;
import com.dahuaboke.blog.model.Article;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dahua
 * @time 2022/3/28 16:56
 */
@Component
public class SystemDao {

    private static final File file = new File(BlogConst.SYSTEM_FILE_NAME);
    private static volatile List<String> originalArticles;
    private static volatile List<String> articles;

    static {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            articles = articles();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> articles() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        originalArticles = new ArrayList();
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                originalArticles.add(line + "\n");
            }
        } finally {
            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();
        }
        List<String> result = Collections.synchronizedList(new LinkedList<>());
        List<String> noTop = new ArrayList();
        for (String str : originalArticles) {
            String[] split = str.split("#");
            if ("1".equals(split[1])) {
                result.add(str);
            } else {
                noTop.add(str);
            }
        }
        result.addAll(noTop);
        return result;
    }

    public List<String> getArticleNames(int page, String search) {
        int start = (page - 1) * BlogConst.PAGE_SIZE;
        List<String> result = new ArrayList();
        int temp = 0;
        for (int a = 0; a < articles.size(); a++) {
            if (result.size() >= BlogConst.PAGE_SIZE) {
                break;
            }
            if (search == null) {
                if (a >= start) {
                    result.add(articles.get(a));
                }
            } else {
                String title = articles.get(a).split("#")[4];
                if (title.contains(search)) {
                    if (temp >= start) {
                        result.add(articles.get(a));
                    } else {
                        temp++;
                    }
                }
            }
        }
        return result;
    }

    public void writeArticleToSystem(Article article) {
        String line = article.getId() + "#" + article.getTop() + "#" + article.getSee() + "#" + article.getCreateTime() + "#" + article.getTitle() + "\n";
        originalArticles.add(line);
        try {
            flush();
        } catch (IOException e) {
            originalArticles.remove(line);
        }
    }

    public int getSize() {
        return articles.size();
    }

    public Object[] search(String search) {
        Object[] result = new Object[2];
        List<String> articleList = new ArrayList();
        AtomicInteger size = new AtomicInteger();
        articles.forEach(article -> {
            String[] split = article.split("#");
            if (split[4].contains(search)) {
                if (articleList.size() < BlogConst.PAGE_SIZE) {
                    articleList.add(article);
                }
                size.getAndIncrement();
            }
        });
        result[0] = articleList;
        result[1] = size.intValue();
        return result;
    }

    public void see(String id) throws IOException {
        String line = null;
        int index = 0;
        for (int a = 0; a < originalArticles.size(); a++) {
            String[] split = originalArticles.get(a).split("#");
            if (split[0].equals(id)) {
                int see = Integer.parseInt(split[2]);
                see++;
                line = split[0] + "#" + split[1] + "#" + see + "#" + split[3] + "#" + split[4];
                index = a;
                break;
            }
        }
        if (line != null) {
            originalArticles.remove(index);
            originalArticles.add(index, line);
            flush();
        }
    }

    public String getTitle(String id) {
        for (String article : articles) {
            String[] split = article.split("#");
            if (split[0].equals(id)) {
                return split[4];
            }
        }
        return null;
    }

    private void flush() throws IOException {
        synchronized (SystemDao.class) {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            for (String a : originalArticles) {
                bufferedWriter.write(a);
            }
            bufferedWriter.close();
            outputStreamWriter.close();
            fileOutputStream.close();
            articles = articles();
        }
    }
}

