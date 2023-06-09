package com.enjoytrip.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	// 이 포트는 application.properties에서 확인 필요
	// http://localhost:9990/swagger-ui/index.html
	
	private String title = "Enjoy Trip API";
	
	@Bean
	public Docket boardApi() {
	     title = "Board API ";
		
		return new Docket(DocumentationType.SWAGGER_2).consumes(getConsumeContentTypes()).produces(getProduceContentTypes())
					.apiInfo(apiInfo()).groupName("Board").select()
					.apis(RequestHandlerSelectors.basePackage("com.enjoytrip.board.controller"))
					.paths(regex("/board/.*")).build()
					.useDefaultResponseMessages(false);
	}

	@Bean
	public Docket hotplaceApi() {
		title = "hotplace API ";
	
		return new Docket(DocumentationType.SWAGGER_2).consumes(getConsumeContentTypes())
				.produces(getProduceContentTypes()).apiInfo(apiInfo()).groupName("Hotplace").select()
				.apis(RequestHandlerSelectors.basePackage("com.enjoytrip.hotplace.controller"))
				.paths(regex("/hotplace/.*")).build().useDefaultResponseMessages(false);
	}
	
	@Bean
	public Docket noticeApi() {
		title = "notice API ";
	
		return new Docket(DocumentationType.SWAGGER_2).consumes(getConsumeContentTypes())
				.produces(getProduceContentTypes()).apiInfo(apiInfo()).groupName("Notice").select()
				.apis(RequestHandlerSelectors.basePackage("com.enjoytrip.notice.controller"))
				.paths(regex("/notice/.*")).build().useDefaultResponseMessages(false);
	}
	
	
	@Bean
	public Docket userApi() { 
        title = "User API ";

        return new Docket(DocumentationType.SWAGGER_2)
        		.apiInfo(apiInfo()).groupName("User").select()
				.apis(RequestHandlerSelectors.basePackage("com.enjoytrip.user.controller"))
				.paths(regex("/user/.*")).build()
				.useDefaultResponseMessages(false);
    }
	
	@Bean
	public Docket itineraryApi() {
		title = "Itinerary API ";

		return new Docket(DocumentationType.SWAGGER_2).consumes(getConsumeContentTypes())
				.produces(getProduceContentTypes()).apiInfo(apiInfo()).groupName("Itinerary").select()
				.apis(RequestHandlerSelectors.basePackage("com.enjoytrip.itinerary.controller")).paths(regex("/itinerary/.*"))
				.build().useDefaultResponseMessages(false);
	}
	
	private Set<String> getConsumeContentTypes() {
		Set<String> consumes = new HashSet<>();
		consumes.add("application/json;charset=UTF-8");
		consumes.add("application/x-www-form-urlencoded");
		return consumes;
	}

	private Set<String> getProduceContentTypes() {
		Set<String> produces = new HashSet<>();
		produces.add("application/json;charset=UTF-8");
		return produces;
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title(title).description("<h3>EnjoyTrip API Reference </h3>").version("1.0")
				.build();
	}
}
