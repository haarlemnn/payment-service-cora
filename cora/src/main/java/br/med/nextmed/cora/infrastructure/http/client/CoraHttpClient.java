package br.med.nextmed.cora.infrastructure.http.client;

import br.med.nextmed.common.configs.CustomObjectMapper;
import br.med.nextmed.common.interceptors.CustomLogRequestInterceptor;
import br.med.nextmed.cora.domain.exceptions.CoraHttpClientException;
import br.med.nextmed.cora.domain.models.CoraConfig;
import br.med.nextmed.cora.infrastructure.configs.CoraAuthConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Slf4j
public class CoraHttpClient {

    public static RestTemplate getClient(CoraConfig coraConfig) {
        try {
            log.info("Loading CORA http client...");
            var sslContext = CoraAuthConfig.getSSLContext(coraConfig);

            var connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
                .setSSLSocketFactory(SSLConnectionSocketFactoryBuilder.create()
                    .setSslContext(sslContext)
                    .build())
                .build();
            var httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .build();

            var requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
            var uriBuilderFactory = new DefaultUriBuilderFactory(coraConfig.getBaseUrl());

            var messageConverter = new MappingJackson2HttpMessageConverter(new CustomObjectMapper());

            var client = new RestTemplate(new BufferingClientHttpRequestFactory(requestFactory));
            client.setUriTemplateHandler(uriBuilderFactory);
            client.getInterceptors().add(new CustomLogRequestInterceptor());
            client.getMessageConverters().add(0, messageConverter);

            log.info("Loading CORA http client...OK");
            return client;
        } catch (Exception e) {
            log.error("Loading CORA http client...FAILED");
            throw new CoraHttpClientException(e);
        }
    }

}
