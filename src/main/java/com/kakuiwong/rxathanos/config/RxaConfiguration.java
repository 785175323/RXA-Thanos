package com.kakuiwong.rxathanos.config;

import com.kakuiwong.rxathanos.core.Interception.RxaFeignRequestInterception;
import com.kakuiwong.rxathanos.core.Interception.RxaHandlerInterceptor;
import com.kakuiwong.rxathanos.core.Interception.RxaRequestInterception;
import com.kakuiwong.rxathanos.core.aop.RxaAdvisor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

/**
 * @author gaoyang
 * @email 785175323@qq.com
 */
@Configuration
public class RxaConfiguration implements WebMvcConfigurer, InitializingBean {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private PlatformTransactionManager txManager;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("__________ ____  ___   _____          ___________.__                                    \n" +
                "\\______   \\\\   \\/  /  /  _  \\         \\__    ___/|  |__  _____     ____    ____   ______\n" +
                " |       _/ \\     /  /  /_\\  \\   ______ |    |   |  |  \\ \\__  \\   /    \\  /  _ \\ /  ___/\n" +
                " |    |   \\ /     \\ /    |    \\ /_____/ |    |   |   Y  \\ / __ \\_|   |  \\(  <_> )\\___ \\ \n" +
                " |____|_  //___/\\  \\\\____|__  /         |____|   |___|  /(____  /|___|  / \\____//____  >\n" +
                "        \\/       \\_/        \\/                        \\/      \\/      \\/             \\/     1.0-SNAPSHOT by GY");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RxaHandlerInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public RxaFeignRequestInterception rxaFeignRequestInterception() {
        return new RxaFeignRequestInterception();
    }

    @Bean
    public RestTemplate rxaRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(new RxaRequestInterception()));
        return restTemplate;
    }

    @Bean
    public RxaAdvisor rxaAdvisor() {
        return new RxaAdvisor(txManager);
    }
}