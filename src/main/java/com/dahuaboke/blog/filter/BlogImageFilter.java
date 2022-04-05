package com.dahuaboke.blog.filter;

import com.dahuaboke.blog.consts.BlogConst;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author dahua
 * @time 2022/3/29 14:19
 */
@Component
public class BlogImageFilter extends HttpFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI();
        if (uri != null && uri.startsWith(BlogConst.IMAGE_URL_PREFIX)) {
            String imageName = uri.replaceFirst(BlogConst.IMAGE_URL_PREFIX, "");
            File file = new File(BlogConst.IMAGE_FILE_DIR + imageName);
            FileInputStream fis = new FileInputStream(file);
            byte[] bytes = new byte[fis.available()];
            OutputStream out = response.getOutputStream();
            fis.read(bytes);
            out.write(bytes);
            out.flush();
        } else {
            super.doFilter(request, response, chain);
        }
    }
}
