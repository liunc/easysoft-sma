package com.easysoft.sma;

import javax.validation.Validator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@ComponentScan("com.easysoft")
@EnableJpaAuditing
@SpringBootApplication
public class EasysoftSmaApplication {

	private ResourceBundleMessageSource getMessageSource() throws Exception {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setBasenames("message/messages");
		return messageSource;
	}

	// 服务端验证功能调用本地资源
	@Bean
	public Validator getValidator() throws Exception {
		LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		validator.setValidationMessageSource(getMessageSource());
		return validator;
	}
	
	//让Spring管理JPAQueryFactory
//    @Bean
//    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager){
//        return new JPAQueryFactory(entityManager);
//    }
    
	public static void main(String[] args) {
		SpringApplication.run(EasysoftSmaApplication.class, args);
	}

}
