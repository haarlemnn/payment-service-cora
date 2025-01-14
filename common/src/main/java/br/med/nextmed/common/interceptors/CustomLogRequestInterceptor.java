package br.med.nextmed.common.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class CustomLogRequestInterceptor implements ClientHttpRequestInterceptor {

    public CustomLogRequestInterceptor() {

    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        logResponse(response);
        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) {
        String prefix = " > ";
        String url = request.getURI().toString();
        String baseUrl = url.substring(0, url.contains("?") ? url.indexOf("?") : url.length());

        StringBuilder requestContent = new StringBuilder()
            .append(String.format("%s %s", request.getMethod(), baseUrl))
            .append(System.lineSeparator())
            .append("=============== Request Details ===============")
            .append(System.lineSeparator())
            .append(String.format("%s Request: %s %s", prefix, request.getMethod(), request.getURI()))
            .append(System.lineSeparator())
            .append(System.lineSeparator());

        if (body.length > 0) {
            requestContent.append(String.format("%s Body: \n%s", prefix, new String(body, StandardCharsets.UTF_8)));
            requestContent.append(System.lineSeparator());
        }

        requestContent.append("================================================ ");

        log.info(requestContent.toString());
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
        String prefix = " < ";
        StringBuilder responseContent = new StringBuilder()
            .append(String.format("HTTP %s" ,response.getStatusCode()))
            .append(System.lineSeparator())
            .append("=============== Response Details ===============")
            .append(System.lineSeparator())
            .append(String.format("%s Response: %s %s", prefix, response.getStatusCode(), response.getStatusText()))
            .append(System.lineSeparator())
            .append(System.lineSeparator());

        String body = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
        if (body.length() > 0) {
            responseContent.append(String.format("%s Body: \n%s", prefix, body));
            responseContent.append(System.lineSeparator());
        }

        responseContent.append("================================================");

        log.info(responseContent.toString());
    }

}
