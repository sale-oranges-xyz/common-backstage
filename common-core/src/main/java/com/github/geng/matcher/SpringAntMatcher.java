package com.github.geng.matcher;

import org.springframework.util.AntPathMatcher;

/**
 * 这部分代码为 spring AntPathRequestMatcher 内部类源码, 有删减
 * @author geng
 */
public class SpringAntMatcher {
    // AntPathMatcher 源码解读，参考 https://www.cnblogs.com/zhangxiaoguang/p/5855113.html
    //                               https://blog.csdn.net/zxy861114/article/details/51459077
    private final AntPathMatcher antMatcher;
    private final String pattern;
    // ------------------------------------------------------------
    // constructors
    public SpringAntMatcher(String pattern, boolean caseSensitive) {
        this.pattern = pattern;
        this.antMatcher = create(caseSensitive);
    }

    public SpringAntMatcher(String pattern) {
        this.pattern = pattern;
        this.antMatcher = create(true);
    }

    // ---------------------------------------------------------
    // ant 路径匹配判断方法
    public boolean matches(String path) {
        return this.antMatcher.match(this.pattern, path);
    }

    // -------------------------------------------------------------
    // private method
    private static AntPathMatcher create(boolean caseSensitive) {
        AntPathMatcher matcher = new AntPathMatcher();
        matcher.setTrimTokens(false);
        matcher.setCaseSensitive(caseSensitive);
        return matcher;
    }
    // -------------------------------------------------------------
    // getter
    public String getPattern() {
        return pattern;
    }
}
