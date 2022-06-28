package com.gdut.minsappofteachingforchildren.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig{

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //是否启动swagger 如果是false则不能在浏览器中使用
                .enable(true)
                .select()
                //RequestHandlerSelectors. 配置要扫描的方式
                //basePackage():指定要扫描的包
                //any():扫描全部
                //none()：不扫描
                //withClassAnnotation():扫描类上的注解，参数是一个注解的反射对象
                //withMethodAnnotation():扫描方法上的注解
                .apis(RequestHandlerSelectors.basePackage("com.gdut.minsappofteachingforchildren.controller"))
                //.paths() .过滤URL
                //any() 任何请求都扫描
                //none() 任何请求都不扫描
                //ant()	通过ant控制
                //regex() 通过正则表达式控制
                .paths(PathSelectors.any())
                .build();
//                .globalOperationParameters(getParameterList());     //测试接口时输入请求头
    }

    /**
     * 测试接口输入请求头
     * @return
     */
//    public List<Parameter> getParameterList() {
//        ParameterBuilder token = new ParameterBuilder();
//        List<Parameter> params = new ArrayList<>();
//        token.name("Authorization")											//这个是请求头的名字，
//                .description("令牌")				 				//在文档中的中文描述
//                .modelRef(new ModelRef("string"))
//                .parameterType("header")						//这个参数的类型
//                .required(false)									 //是否必填
//                .build();
//        params.add(token.build());
//        return params;
//    }


    private ApiInfo apiInfo(){

        return new ApiInfoBuilder().title("育婴小程序Api")
                                   .build();
//        return new ApiInfoBuilder()
//                .title("天天吃货，电商平台接口api").
//                contact(new Contact("imooc","https://www.imooc.com","abc@imooc.com"))
//                .description("专门为天天吃货提供的api文档")
//                .version("1.0.1")
//                .termsOfServiceUrl("https://www.imooc.com")
//                .build();
//
    }

}
