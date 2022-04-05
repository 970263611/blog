package com.dahuaboke.blog.dao;

import com.dahuaboke.blog.consts.BlogConst;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

/**
 * @author dahua
 * @time 2022/3/28 17:04
 */
@Component
public class CommentDao {

    public void writeComment(String fileName, String date, String comment) throws IOException {
        File file = new File(BlogConst.COMMENT_FILE_DIR + fileName);
        if (!file.isFile() || !file.exists() || !file.canRead()) {
            return;
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file, true);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        bufferedWriter.write(comment + "\n");
        bufferedWriter.write(date + "\n");
        bufferedWriter.close();
        outputStreamWriter.close();
        fileOutputStream.close();
    }

    public List<String> readComment(String name) throws IOException {
        File file = new File(BlogConst.COMMENT_FILE_DIR + name);
        if (!file.isFile() || !file.exists() || !file.canRead()) {
            return null;
        }
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        List<String> result = new ArrayList();
        String line;
        while ((line = br.readLine()) != null) {
            result.add(line);
        }
        br.close();
        isr.close();
        fis.close();
        return result;
    }

    public List<Map<String, String>> getComment(String id) throws IOException {
        List<Map<String, String>> result = new LinkedList();
        List<String> strings = readComment(id);
        if (strings != null && !strings.isEmpty()) {
            Map<String, String> temp = new HashMap();
            for (int a = strings.size() - 1; a >= 0; a--) {
                if (temp.size() >= 2) {
                    result.add(temp);
                    temp = new HashMap();
                }
                String data = strings.get(a);
                if (a % 2 == 0) {
                    temp.put("message", data);
                }
                if (a % 2 == 1) {
                    temp.put("date", data);
                }
            }
            result.add(temp);
        }
        return result;
    }
}
