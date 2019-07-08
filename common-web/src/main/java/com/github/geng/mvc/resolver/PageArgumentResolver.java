package com.github.geng.mvc.resolver;

import com.github.geng.util.ConverterUtils;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import sun.util.locale.StringTokenIterator;

import java.util.StringTokenizer;


/**
 * 自定义处理分页参数绑定,统一绑定
 * @author geng
 */
public class PageArgumentResolver implements HandlerMethodArgumentResolver {
    private static final int PAGE_NO = 1;
    private static final int PAGE_SIZE = 20;
    private static final String DESC = "desc";
    private static final String ASC = "asc";
    private static final String PAGE_NO_NAME = "page_no";
    private static final String PAGE_SIZE_NAME = "page_size";
    // private static final String SORT = "sort";
    private static final String SORT_NAME = "page_sort";

    /**
     * 用于判定是否需要处理该参数分解，返回true为需要，并会去调用下面的方法resolveArgument
     * @param methodParameter MethodParameter
     * @return 返回true时需要参数处理，并会去调用下面的方法resolveArgument
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
       return Pageable.class.equals(methodParameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {

        String pageNoStr = nativeWebRequest.getParameter(PAGE_NO_NAME);
        String pageSizeStr = nativeWebRequest.getParameter(PAGE_SIZE_NAME);
        int pageNo = PAGE_NO;
        int pageSize = PAGE_SIZE;
        if (StringUtils.hasText(pageNoStr) && StringUtils.hasText(pageSizeStr)) {
            pageNo = ConverterUtils.StringToIntDefault(pageNoStr, PAGE_NO);
            pageSize = ConverterUtils.StringToIntDefault(pageSizeStr, PAGE_SIZE);
        }

        // 排序参数处理
        String sortName = nativeWebRequest.getParameter(SORT_NAME);
        Sort sort = null;
        Sort subSort;
        if (StringUtils.hasText(sortName)) {
            StringTokenizer stringTokenizer = new StringTokenizer(sortName, ",");
            while (stringTokenizer.hasMoreElements()) {
                String sortValue = stringTokenizer.nextToken();
                if (sortValue.contains("_")) {
                    // 排序参数内容 字段名_排序方式
                    String[] sortValueArr = sortValue.split("_");
                    subSort = new Sort(new Sort.Order(Sort.Direction.fromString(sortValueArr[1]), sortValueArr[0]));

                    if (null == sort) {
                        sort = subSort;
                    } else {
                        sort = sort.and(subSort);
                    }
                }
            }
        }

        return new PageRequest(pageNo-1, pageSize, sort);
    }
}
