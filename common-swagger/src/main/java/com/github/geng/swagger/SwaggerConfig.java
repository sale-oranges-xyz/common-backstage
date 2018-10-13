package com.github.geng.swagger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

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
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(SwaggerSelectors.basePackage(new String[] {"com.github.geng.**.api"}))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Restful Apis")
                .description("Restful Apis")
                //.termsOfServiceUrl("")
                .version("1.0")
                .build();
    }
}
