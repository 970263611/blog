package com.dahuaboke.blog.consts;

import java.io.File;

/**
 * @author dahua
 * @time 2022/3/28 17:06
 */
public class BlogConst {

    public static final String HOME_DIR = System.getProperty("user.home") + "/dahuaboke/";
    public static final String TAG_FILE_NAME = HOME_DIR + "tag";
    public static final String LEAVE_MESSAGE_FILE_NAME = HOME_DIR + "leaveMessage";
    public static final String SYSTEM_FILE_NAME = HOME_DIR + "system";
    public static final String ARTICLE_FILE_DIR = HOME_DIR + "article/";
    public static final String COMMENT_FILE_DIR = HOME_DIR + "comment/";
    public static final String IMAGE_FILE_DIR = HOME_DIR + "image";
    public static final String IMAGE_URL_PREFIX = "/dahuaboke/image";
    public static final int PAGE_SIZE = 5;

    static {
        File file = new File(HOME_DIR);
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File(ARTICLE_FILE_DIR);
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File(COMMENT_FILE_DIR);
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File(IMAGE_FILE_DIR);
        if (!file.exists()) {
            file.mkdir();
        }
    }
}
