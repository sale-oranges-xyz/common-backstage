package com.github.geng.security.entity;

import com.github.geng.bread.CollectionOptional;
import com.github.geng.matcher.SpringAntMatcher;
import com.github.geng.util.ListUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 注意：AntPathMatcher 的匹配写法是 ant 写法，不是正则表达式写法
 * @author geng
 */
@Component
public class AuthorizeIgnore {
    @Value("${authorize.ignore:null}")
    private String authorizeIgnore; // 验证拦截白名单 多个使用英文,隔开,支持正则表达式
    // 不需要使用 volatile 关键字，因为spring bean 使用单例实现
    private boolean isAddConfig = false;
    private static final List<SpringAntMatcher> authorizeIgnoreList = new ArrayList<>();

    static {
        authorizeIgnoreList.add(new SpringAntMatcher("/**/*.css"));
        authorizeIgnoreList.add(new SpringAntMatcher("/**/*.js"));
        authorizeIgnoreList.add(new SpringAntMatcher("/**/*.png|jpg"));
        authorizeIgnoreList.add(new SpringAntMatcher("/**/fonts/**"));
        authorizeIgnoreList.add(new SpringAntMatcher("/images/**"));
        authorizeIgnoreList.add(new SpringAntMatcher("/**/*.ico"));
        // 系统配置方面
        authorizeIgnoreList.add(new SpringAntMatcher("/announce.php"));
        authorizeIgnoreList.add(new SpringAntMatcher("/announce"));
        // 添加swagger 配置部分
        authorizeIgnoreList.add(new SpringAntMatcher("/swagger-ui.html"));
        authorizeIgnoreList.add(new SpringAntMatcher("/swagger-resources/**"));
        authorizeIgnoreList.add(new SpringAntMatcher("/v2/api-docs"));
        authorizeIgnoreList.add(new SpringAntMatcher("/webjars/**"));
    }

    /**
     * 判断请求链接是否白名单
     * @param requestUri
     * @return
     */
    public boolean isMatch(String requestUri) {
        this.getAuthorizeIgnoreList();
        return authorizeIgnoreList.stream().anyMatch(springAntMatcher -> springAntMatcher.matches(requestUri));
    }

    /**
     * 获取用户自定义的请求链接白名单
     * @return
     */
    public List<String> getConfigAuthorizeIgnores() {
        List<String> authorizeIgnoreList = new ArrayList<>();

        if (null == authorizeIgnore) {
            return authorizeIgnoreList;
        }
        if (authorizeIgnore.contains(",")) {
            authorizeIgnoreList.addAll(ListUtils.array2List(authorizeIgnore.split(",")));
        } else {
            authorizeIgnoreList.add(authorizeIgnore);
        }
        return authorizeIgnoreList;
    }

    // -------------------------------------------------------------------------
    // private method
    private void getAuthorizeIgnoreList() {
        synchronized (this) {
            if (!isAddConfig) { // 没有添加配置文件数据
                // 处理自定义拦截白名单
                this.getConfigAuthorizeIgnores().forEach(str -> authorizeIgnoreList.add(new SpringAntMatcher(str)));
                isAddConfig = true; // 标记为添加配置文件数据
            }
        }
    }

//    public static void main(String[] args) {
//        // assert
//        AuthorizeIgnore authorizeIgnore = new AuthorizeIgnore();
//        System.out.println(authorizeIgnore.isMatch("/announce.php"));
//    }

}
