package com.example.voice.configuration;

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
 * @author jianhui.Yang
 * @version $Id SwaggerConfig.java, v 0.1 2018-11-07 16:12 jianhui.Yang Exp $$
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket voiceApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.voice.controller"))
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("工具包项目")
                .description("系统服务监控……")
                .contact("jianhui.Yang")
                .version("1.0")
                .build();
    }

}
