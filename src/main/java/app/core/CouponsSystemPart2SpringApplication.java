package app.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import app.core.filters.TokenFilter;
import app.core.threads.CouponsExpirationDailyJob;
import app.core.util.JwtUtil;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2

public class CouponsSystemPart2SpringApplication {

	public static void main(String[] args) throws InterruptedException {
		ConfigurableApplicationContext ctx = SpringApplication.run(CouponsSystemPart2SpringApplication.class, args);
		ctx.getBean(CouponsExpirationDailyJob.class);

	}

	@Bean
	public FilterRegistrationBean<TokenFilter> tokenFilterRegistration(JwtUtil jwtUtil) {
		FilterRegistrationBean<TokenFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		TokenFilter tokenFilter = new TokenFilter(jwtUtil);
		filterRegistrationBean.setFilter(tokenFilter);
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;

	}

}
