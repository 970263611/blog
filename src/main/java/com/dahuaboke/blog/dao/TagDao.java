package com.dahuaboke.blog.dao;

import com.dahuaboke.blog.consts.BlogConst;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dahua
 * @time 2022/3/28 16:55
 */
@Component
public class TagDao {

    private static final File file = new File(BlogConst.TAG_FILE_NAME);

    static {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getTags() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        List<String> result;
        try {
            String line;
            result = new ArrayList();
            while ((line = bufferedReader.readLine()) != null) {
                result.add(line);
            }
        } finally {
            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();
        }
        return result;
    }

    public void writeTag(String tag) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file, true);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        bufferedWriter.write(tag + "\n");
        bufferedWriter.write(System.currentTimeMillis() + "\n");
        bufferedWriter.close();
        outputStreamWriter.close();
        fileOutputStream.close();
    }
}
