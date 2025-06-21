package sdk.mssearch.javasdk.core;

import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sdk.mssearch.javasdk.core.utility.JacksonUtils;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApiRequester {
    private static final Logger logger = LoggerFactory.getLogger(ApiRequester.class);
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().build();
    private static final String[] defaultHeader = getDefaultRestHeader();

    private static String[] getDefaultRestHeader() {
        List<String> headers = new ArrayList<>();
        headers.add("Accept");
        headers.add("application/json");

        headers.add("Content-Type");
        headers.add("application/json");
        return headers.toArray(new String[0]);
    }

    //prevent init
    private ApiRequester() {}


    public static <R> R execGet(String url, Map<String, String> headers, TypeReference<R> responseType, boolean shouldRetry) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .headers(getHeader(headers))
                .GET()
                .build();
        return sendRequest(request, responseType, shouldRetry);
    }

    public static <T, R> R execPost(String url, T requestBody, Map<String, String> headers, TypeReference<R> responseType, boolean shouldRetry) throws Exception {
        String jsonBody = JacksonUtils.objectToJson(requestBody);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .headers(getHeader(headers))
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        return sendRequest(request, responseType, shouldRetry);
    }

    public static <T, R> R execPut(String url, T requestBody, Map<String, String> headers, TypeReference<R> responseType, boolean shouldRetry) throws Exception {
        String jsonBody = JacksonUtils.objectToJson(requestBody);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .headers(getHeader(headers))
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        return sendRequest(request, responseType, shouldRetry);
    }

    public static <R> R execDelete(String url, Map<String, String> headers, TypeReference<R> responseType, boolean shouldRetry) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .headers(getHeader(headers))
                .DELETE()
                .build();
        return sendRequest(request, responseType, shouldRetry);
    }

    private static <R> R sendRequest(HttpRequest request, TypeReference<R> responseType, boolean shouldRetry) throws Exception {
        int attempt = 0;
        Exception lastException = null;

        int retryTimes = shouldRetry ? 3 : 1;
        long retryDelayMs = 1000;
        while (attempt < retryTimes) {
            try {
                HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

                //No Content
                if (response.statusCode() == 204) {
                    return null;
                }

                if (response.statusCode() < 500) {
                    return JacksonUtils.jsonToObject(response.body(), responseType);
                }
            } catch (Exception e) {
                lastException = e;
                logger.error("Request failed with exception: {}, retrying... , error: ", e.getMessage(), e);
            }

            attempt++;
            if (shouldRetry && attempt < retryTimes) {
                long waitTime = retryDelayMs * (1L << (attempt - 1)); // 指數退避 (2^n)
                logger.warn("{} Waiting {} ms before retry...", request.uri(),  waitTime);
                Thread.sleep(waitTime);
            }
        }
        Exception e = new RuntimeException("Failed to send request after " + retryTimes + " attempts", lastException);
        logger.error("{} request error: ", request.uri(), e);
        throw e;
    }


    private static String[] getHeader (Map<String, String> headers) {
        if (headers == null || headers.isEmpty()) {
            return defaultHeader;
        }
        List<String> list = new ArrayList<>();
        headers.forEach((key, value) -> {
            list.add(key);
            list.add(value);
        });
        return list.toArray(new String[0]);
    }
}
