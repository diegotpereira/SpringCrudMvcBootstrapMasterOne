package br.com.java.config;

import java.util.Locale;
import java.util.Properties;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.servlet.Filter;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.zaxxer.hikari.HikariDataSource;

import br.com.java.config.security.SecurityConfig;

@Configuration
@ComponentScan(basePackages = { "br.com.java" })
@EnableWebMvc
@EnableJpaRepositories(basePackages = "br.com.java")
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@Import({ SecurityConfig.class })
public class AppConfig extends WebMvcConfigurerAdapter{

	private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
	private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
	private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
	private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";
	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
	private static final String PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY = "hibernate.ejb.naming_strategy";
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";

	@Resource
	private Environment environment;

	@Bean
	public InternalResourceViewResolver resolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class);
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Bean
	public DataSource dataSource() {

		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
		dataSource.setJdbcUrl(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
		dataSource.setUsername(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
		dataSource.setPassword(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));

		return dataSource;
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() {

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("br.com.java.model");

		Properties jpaProterties = new Properties();
		jpaProterties.put(PROPERTY_NAME_HIBERNATE_DIALECT,
				environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
		jpaProterties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO,
				environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO));
		jpaProterties.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL,
				environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_FORMAT_SQL));
		jpaProterties.put(PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY,
				environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY));
		jpaProterties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL,
				environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));

		jpaProterties.put("hibernate.connection.driver_class",
				environment.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
		jpaProterties.put("hibernate.connection.url", environment.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
		jpaProterties.put("hibernate.connection.username",
				environment.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
		jpaProterties.put("hibernate.connection.password",
				environment.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));

		factory.setJpaProperties(jpaProterties);
		factory.afterPropertiesSet();

		return factory.getObject();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {

		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory());
		return txManager;
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		 super.addResourceHandlers(registry);
	}

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasename("messages");
		source.setUseCodeAsDefaultMessage(true);
		source.setDefaultEncoding("UTF-8");
		return source;
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver resolver = new SessionLocaleResolver();
		resolver.setDefaultLocale(new Locale("pt_BR"));
		return resolver;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName("lang");
		registry.addInterceptor(interceptor);
	}

	@Bean
	public Filter characterEncodingFilter() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return characterEncodingFilter;
	}

}
