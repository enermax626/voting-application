package br.com.voting.votingapp.config;

import br.com.voting.votingapp.util.RestTemplateUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Value("${rest.template.connection.timeout}")
    private int connectionTimeout;

    @Value("${rest.template.read.timeout}")
    private int readTimeout;

    @Bean
    public RestTemplateUtil restTemplateUtils(RestTemplate restTemplate) {
        return new RestTemplateUtil(restTemplate);
    }

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        return new RestTemplate(factory);
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        Integer effectiveConnectTimeout = ObjectUtils.defaultIfNull(connectionTimeout, 8000);
        Integer effectiveReadTimeout = ObjectUtils.defaultIfNull(readTimeout, 8000);
        factory.setConnectTimeout(effectiveConnectTimeout);
        factory.setReadTimeout(effectiveReadTimeout);
        return factory;
    }
}
