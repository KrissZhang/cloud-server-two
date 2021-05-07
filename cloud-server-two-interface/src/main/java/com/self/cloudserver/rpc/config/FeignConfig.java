package com.self.cloudserver.rpc.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.self.cloudserver.rpc.feign.TestFeign;
import feign.Feign;
import feign.Request;
import feign.Retryer;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class FeignConfig {

    @Value("${CLOUD_SERVER_TWO_HOST:http://127.0.0.1:8081}")
    private String cloudServerTwoHost;

    public String getCloudServerTwoHost() {
        return cloudServerTwoHost;
    }

    public void setCloudServerTwoHost(String cloudServerTwoHost) {
        this.cloudServerTwoHost = cloudServerTwoHost;
    }

    public ObjectFactory<HttpMessageConverters> objectFactory() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter(objectMapper);
        ObjectFactory<HttpMessageConverters> converter = () -> new HttpMessageConverters(jsonConverter);
        return converter;
    }

    public Feign.Builder getBuilder() {
        return Feign.builder()
                .encoder(new SpringEncoder(objectFactory()))
                .decoder(new SpringDecoder(objectFactory()))
                .contract(new SpringMvcContract())
                .options(new Request.Options(30000, 35000))
                .retryer(new Retryer.Default(5000, 5000, 1));
    }

    @Bean
    public TestFeign testFeign(){
        return getBuilder().target(TestFeign.class, cloudServerTwoHost);
    }

}
