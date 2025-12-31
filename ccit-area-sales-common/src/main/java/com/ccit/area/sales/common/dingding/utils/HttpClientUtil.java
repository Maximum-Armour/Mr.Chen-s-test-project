package com.ccit.area.sales.common.dingding.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * HttpClient工具类
 */
@Slf4j
public class HttpClientUtil {

    private static final Integer CONNECTION_TIMEOUT = 10000; // 连接超时时间（毫秒）
    private static final Integer SOCKET_TIMEOUT = 10000;     // 读取超时时间（毫秒）
    private static final Integer CONNECTION_REQUEST_TIMEOUT = 2000; //连接请求超时时间(毫秒);

    /**
     * 发送GET请求
     *
     * @param url     请求的URL
     * @param headers 请求头信息
     * @return 响应结果
     */
    public static String sendGet(String url, Map<String, String> headers) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectTimeout(CONNECTION_TIMEOUT)
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
                .build();

        httpGet.setConfig(requestConfig);

        // 设置请求头
        if (headers != null) {
            log.info("requestHeaders: {}", headers);
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpGet.setHeader(entry.getKey(), entry.getValue());
            }
        }

        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            String httpResponse = EntityUtils.toString(response.getEntity());
            log.info("Url: {} HttpResponse: {}", url, httpResponse);
            return httpResponse;
        } catch (IOException e) {
            log.error("发起http请求失败!", e);
            throw new RuntimeException();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                log.error("关闭HTTP客户端时发生错误", e);
            }
        }

    }

    /**
     * 发送不带请求头的POST请求
     *
     * @param url  请求URL
     * @param body 请求体
     * @return 响应内容
     */
    public static String sendPost(String url, String body) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        // 添加 Content-Type 头
        httpPost.setHeader("Content-Type", "application/json");

        // 设置请求体
        if (body != null && !body.trim().isEmpty()) {
            StringEntity stringEntity = new StringEntity(body, StandardCharsets.UTF_8);
            httpPost.setEntity(stringEntity);
        }

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectTimeout(CONNECTION_TIMEOUT)
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
                .build();

        httpPost.setConfig(requestConfig);

        log.info("POST requestURL: {} requestBody: {}", url,body);
        String httpResponse = "";
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            httpResponse = EntityUtils.toString(response.getEntity());
            log.info("Url: {} HttpResponse: {}", url, httpResponse);
            return httpResponse;
        } catch (IOException e) {
            log.error("发起http请求失败!", e);
            throw new RuntimeException();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                log.error("关闭HTTP客户端时发生错误", e);
            }
        }
    }

    /**
     * 发送带请求头的POST请求
     *
     * @param url     请求URL
     * @param headers 请求头
     * @param body    请求体
     * @return 响应内容
     */
    public static String sendPostWithHeaders(String url, Map<String, String> headers, String body) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        // 设置请求头
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }

        // 添加 Content-Type 头
        httpPost.setHeader("Content-Type", "application/json");

        // 设置请求体
        if (body != null && !body.trim().isEmpty()) {
            StringEntity stringEntity = new StringEntity(body, StandardCharsets.UTF_8);
            httpPost.setEntity(stringEntity);
        }

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectTimeout(CONNECTION_TIMEOUT)
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
                .build();

        httpPost.setConfig(requestConfig);

        log.info("POST requestURL: {} RequestHeaders: {} requestBody: {}", url,headers,body);
        String httpResponse = "";
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            httpResponse = EntityUtils.toString(response.getEntity());
            log.info("Url: {} HttpResponse: {}", url, httpResponse);
            return httpResponse;
        } catch (Exception e) {
            log.error("发起http请求失败!", e);
            throw new RuntimeException();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                log.error("关闭HTTP客户端时发生错误", e);
            }
        }
    }

    public static String sendPutWithHeaders(String url, Map<String, String> headers, String body){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);  // 更改请求类型为PUT

        // 设置请求头
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPut.setHeader(entry.getKey(), entry.getValue());
            }
        }

        // 添加 Content-Type 头
        httpPut.setHeader("Content-Type", "application/json");

        // 设置请求体
        if (body != null && !body.trim().isEmpty()) {
            StringEntity stringEntity = new StringEntity(body, StandardCharsets.UTF_8);
            httpPut.setEntity(stringEntity);
        }

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectTimeout(CONNECTION_TIMEOUT)
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
                .build();

        httpPut.setConfig(requestConfig);

        log.info("PUT requestURL: {} RequestHeaders: {} requestBody: {}", url,headers,body);
        String httpResponse = "";
        try (CloseableHttpResponse response = httpClient.execute(httpPut)) {  // 执行PUT请求
            httpResponse = EntityUtils.toString(response.getEntity());
            log.info("Url: {} HttpResponse: {}", url, httpResponse);
            return httpResponse;
        } catch (IOException e) {
            log.error("发起http请求失败!", e);
            throw new RuntimeException();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                log.error("关闭HTTP客户端时发生错误", e);
            }
        }
    }

}