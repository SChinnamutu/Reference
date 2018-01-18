package com.perficient.hr.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySources({
    @PropertySource("classpath:appconfig.properties"),
    @PropertySource("classpath:dbconfig.properties")
})
public class PropertyUtils {
			
	/*
     * PropertySourcesPlaceHolderConfigurer Bean only required for @Value("{}") annotations.
     * Remove this bean if you are not using @Value annotations for injecting properties.
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    	PropertySourcesPlaceholderConfigurer configurer=new PropertySourcesPlaceholderConfigurer();
    	configurer.setIgnoreResourceNotFound(true);
    	configurer.setIgnoreUnresolvablePlaceholders(true);
    	return configurer;
    }
    
}
