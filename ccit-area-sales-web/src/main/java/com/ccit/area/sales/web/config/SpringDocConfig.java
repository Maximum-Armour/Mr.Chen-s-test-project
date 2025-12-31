package com.ccit.area.sales.web.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;


/**
 *  swagger配置类
 *
 * @author: chinacoalit.com
 * @date 2024/1/9 15:55
 * @version: v1.0 初始化
 */
@Configuration
@Profile({"dev","test","local"})
public class SpringDocConfig {

    @Bean
    public OpenAPI myOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("中煤基础项目")
                .description("中煤基础项目")
                .version("v1.0.0"));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
            .group("api")
            .pathsToMatch("/api/**")
            .build();
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
            .group("webapi")
            .pathsToMatch("/webapi/**")
            .build();
    }

}