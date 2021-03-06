package com.springBootD.framework.config;

import io.swagger.annotations.ApiOperation;
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
 * 本类是配置Swagger UI测试API功能
 * 
 */

@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = "springbootd", name = "swagger-open", havingValue = "true")
public class SwaggerConfig{

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				//这里采用包含注解的方式来确定要显示的接口
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("springd Doc")
				.description("springd Api文档")
				.termsOfServiceUrl("www.baidu.com")
				.contact("springd")
				.version("2.0")
				.build();
	}

}
