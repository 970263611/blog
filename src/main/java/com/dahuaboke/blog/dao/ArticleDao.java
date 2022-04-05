package com.dahuaboke.blog.dao;

import com.dahuaboke.blog.consts.BlogConst;
import com.dahuaboke.blog.model.Article;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * @author dahua
 * @time 2022/3/28 16:54
 */
@Component
public class ArticleDao {

    public Article getArticle(String name) throws IOException {
        String[] articleStr = readFile(name);
        if (articleStr == null) {
            return null;
        }
        Article article = new Article();
        article.setId(articleStr[0]);
        article.setType(articleStr[1]);
        article.setContent(articleStr[2]);
        return article;
    }

    public String[] readFile(String name) throws IOException {
        File file = new File(BlogConst.ARTICLE_FILE_DIR + name);
        if (!file.isFile() || !file.exists() || !file.canRead()) {
            return null;
        }
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        StringBuffer stringBuffer = new StringBuffer();
        String line;
        int a = 0;
        String[] result = new String[3];
        while ((line = br.readLine()) != null) {
            if (a >= 2) {
                stringBuffer.append(new StringBuffer(line));
                continue;
            }
            result[a] = line;
            a++;
        }
        result[2] = new String(stringBuffer);
        br.close();
        isr.close();
        fis.close();
        return result;
    }

    public void writeArticle(Article article) throws IOException {
        File file = new File(BlogConst.ARTICLE_FILE_DIR + article.getId());
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        bufferedWriter.write(article.getId() + "\n");
        bufferedWriter.write(article.getType() + "\n");
        bufferedWriter.write(article.getContent() + "\n");
        bufferedWriter.close();
        outputStreamWriter.close();
        fileOutputStream.close();
    }
}
