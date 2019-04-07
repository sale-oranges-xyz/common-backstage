package com.github.geng.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


/**
 * swagger 配置 ，参考 https://blog.csdn.net/xiaojin21cen/article/details/78653506
 * <p>
 *     swagger ui 访问链接 /swagger-ui.html
 *     第三方ui 访问链接 /doc.html
 * </p>
 * @author geng
 */
@Configuration
@ConditionalOnProperty(value = {"swagger.enabled"}, matchIfMissing = true) // 看情况动态创建bean
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.scanner}")
    private String[] swaggerScanPackage;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(SwaggerSelectors.basePackage(createSwaggerScanPackage()))
                .paths(PathSelectors.any())
                .build();
    }

    // private methods ------------------------------------------------------------------

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Restful Apis")
                .description("Restful Apis")
                //.termsOfServiceUrl("")
                .version("1.0")
                .build();
    }

    private String[] createSwaggerScanPackage() {
        String basePackage = "com.github.geng.**.api.**" ;
//        if (null != swaggerScanPackage) {
//            String[] newArr = new String[1 + swaggerScanPackage.length];
//            System.arraycopy(swaggerScanPackage, 0,  newArr, 0, swaggerScanPackage.length);
//            newArr[newArr.length -1] = basePackage;
//            return newArr;
//        }

        // TODO: 扫描路径根据实际情况修改
        return new String[] { "com.github.geng.**.api.**", "com.bo.geng.**.api.**" };
    }
}
