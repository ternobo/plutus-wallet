package com.ternobo.wallet.user.http.filters;

import com.ternobo.wallet.client.services.ApplicationClientService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterRegistry {
    @Bean
    public FilterRegistrationBean<SignupFilter> registerFilter(ApplicationClientService applicationClientService) {
        FilterRegistrationBean<SignupFilter> registrationBean
                = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SignupFilter(applicationClientService));
        registrationBean.addUrlPatterns("/api/v1/user/register");
        return registrationBean;
    }
}
