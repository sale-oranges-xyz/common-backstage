package com.github.geng.swagger;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import springfox.documentation.RequestHandler;

import java.util.regex.Pattern;


/**
 * 重写swagger的项目路径匹配方法
 * <p>该方法匹配 com.github.geng.**.api | com.github.geng.* 写法</p>
 * @author geng
 */
public class SwaggerSelectors {

    public static Predicate<RequestHandler> basePackage(final String[] basePackages) {
        return (input) -> declaringClass(input).transform(handlerPackage(basePackages)).or(true);
    }

    // -----------------------------------------------------------------------------
    // private methods
    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.fromNullable(input.declaringClass());
    }

    private static String regexTemplate = "^%s$";
    private static Function<Class<?>, Boolean> handlerPackage(final String[] basePackages) {
        return (input) -> {
            for (String basePackage : basePackages) {
                String regex;
                if (basePackage.contains("**")) {
                    regex = String.format(regexTemplate, basePackage.replace("**", "*"));
                } else {
                    regex = String.format(regexTemplate, basePackage);
                }
                if (Pattern.matches(regex, input.getPackage().getName())) {
                    return true;
                }
            }
            return false;
        };
    }

    // public static void main(String[] args) {
        // String className = "com.github.geng.admin.business.api";
        // String regex = "^com.github.geng.*$";
        // Pattern pattern = Pattern.compile(regex);
        // System.out.println(pattern.matcher(className).matches());
        // System.out.println(Pattern.matches(regex, className));
        // System.out.println("com.github.geng.**.api".contains("**"));
    // }
}
