package com.ternobo.wallet.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {

    @Bean
    public OkHttpClient httpClient(){
        return new OkHttpClient();
    }

    public ObjectMapper jObjectMapper(){
        return new ObjectMapper();
    }

}
