package com.hussain.mycrm.config;

import java.util.Arrays;

import javax.sql.DataSource;

import org.apache.catalina.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.hussain.mycrm.model.User;
import com.hussain.mycrm.service.UserService;

@SpringBootApplication
@ComponentScan(basePackages={"com.hussain"})
public class NewApplication {
	
	@Autowired
	private static UserService userService;
	
	public static void main(String[] args) {
		SpringApplication.run(NewApplication.class, args);
			
			
		
	}
	
	@Bean(name = "DataSource")
    @ConfigurationProperties(prefix="datasource")
    public DataSource createDataSource() {
       return DataSourceBuilder.create().build();
    }

    @Bean(name = "Jdbc")
    @DependsOn("DataSource")
    public NamedParameterJdbcTemplate createJdbcTemplate(@Qualifier("DataSource") DataSource DataSource) {
        return new NamedParameterJdbcTemplate(DataSource);
    }
    
    @Bean
    public ViewResolver getViewResolver(){
    	InternalResourceViewResolver viewResolver=new InternalResourceViewResolver();
    	viewResolver.setSuffix(".html");
    	return viewResolver;
    }
    
   
    
    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.setTomcatContextCustomizers(Arrays.asList(new CustomCustomizer()));
        return factory;
    }

    static class CustomCustomizer implements TomcatContextCustomizer {

		@Override
		public void customize(Context context) {
			// TODO Auto-generated method stub
			context.setUseHttpOnly(false);
		}
      
    }

}
