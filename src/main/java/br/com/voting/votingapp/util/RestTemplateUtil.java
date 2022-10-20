package br.com.voting.votingapp.util;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * This class is responsible for providing the methods to make requests to external APIs.
 * it can be improved to make the requests more generic or to use a library like Feign.
 */
@Slf4j
@RequiredArgsConstructor
public class RestTemplateUtil {

    private final RestTemplate restTemplate;

    public <R> Optional<R> doGetRes(String url, Map<String, String> queryParams, HttpHeaders httpHeaders, ParameterizedTypeReference<R> responseType) {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        if (queryParams != null) {
            params.setAll(queryParams);
        }
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParams(params)
                .encode().build().toUri();
        log.info("get uri:{}", uri);

        ResponseEntity<R> exchange = restTemplate.exchange(uri, HttpMethod.GET,
                                                           new HttpEntity<>(httpHeaders), responseType);

        return Optional.ofNullable(exchange.getBody());
    }

    public <T, R> R doPostRes(String url, T body, HttpHeaders httpHeaders, ParameterizedTypeReference<R> responseType) {
        log.debug("post url:{},body:{}", url, body);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<T> entity = new HttpEntity<>(body, httpHeaders);

        ResponseEntity<R> exchange = restTemplate.exchange(url, HttpMethod.POST, entity, responseType);

        return exchange.getBody();
    }
}