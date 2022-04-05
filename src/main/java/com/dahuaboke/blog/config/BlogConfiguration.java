package com.dahuaboke.blog.config;

import com.dahuaboke.blog.filter.BlogImageFilter;
import com.dahuaboke.mvc.model.MvcFilter;
import com.dahuaboke.mvc.web.filter.MvcFilterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dahua
 * @time 2022/3/29 14:18
 */
@Configuration
public class BlogConfiguration {

    @Autowired
    private BlogImageFilter blogImageFilter;

    @Bean
    public MvcFilterFactory mvcFilterFactory() {
        MvcFilterFactory mvcFilterFactory = new MvcFilterFactory();
        MvcFilter mvcFilter = new MvcFilter();
        mvcFilter.setName("mvcFilter");
        mvcFilter.setOrder(1);
        mvcFilter.setPattern("/*");
        mvcFilter.setFilter(blogImageFilter);
        mvcFilterFactory.addFilter(mvcFilter);
        return mvcFilterFactory;
    }
}
