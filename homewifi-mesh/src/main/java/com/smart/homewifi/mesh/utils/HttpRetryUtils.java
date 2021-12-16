package com.smart.homewifi.mesh.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import javax.net.ssl.*;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @Author:Z
 * @Date:2021/11/25 16:44
 * @Description: Http请求连接池实现超时重试工具类
 * @Version:1.0
 */
public class HttpRetryUtils {
    /**
     * post请求实现连接超时和响应超时重试
     * @param url
     * @param str
     * @return
     */
    public static String postRetryQuery(String url, String str) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject queryRoot = (JSONObject) JSONObject.parse(str);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(clientHttpRequestFactory());
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
        org.springframework.http.HttpEntity<JSONObject> entity = new org.springframework.http.HttpEntity<>(queryRoot, headers);
        return restTemplate.postForEntity(url, entity, JSONObject.class).getBody().toJSONString();
    }

    public static String getRetryQuery(String url, String str) {
        JSONObject queryRoot = (JSONObject) JSONObject.parse(str);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(clientHttpRequestFactory());
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
        return restTemplate.getForEntity(url, JSONObject.class, queryRoot).getBody().toJSONString();
    }

    public static void main(String[] args) {
        /*String result = postRetryQuery("https://nos9.189cube.com/plugin/post?appid=1000000273534113&secret=4463d6ab2f0a4afc" +
                "&MAC=C8F6C80314C0&PluginName=com.chinatelecom.all.smartgateway.inter_conndv3_mt7526&Version=3.3.04","{\"CmdType\": \"GetMeshStatus\",\"SequenceId\": \"76721\"}");
        System.out.println(result);*/

        /*String getResult = getRetryQuery("https://nos9.189cube.com/device/listplugin?" +
                "MAC=C8F6C80314C0&token&appid=1000000208455928&secret=f17ddb39a13ad0e7&" +
                "Plugin_Name=eLinkAP&Version=null","{\"RPCMethod\": \"ListPlugin\"," +
                "\"ID\": \"a2b15ce0-50da-11ec-8490-fa163ea2992d\",\"MAC\": \"C8F6C80314C0\"}");*/

        String getResult = postRetryQuery("https://apweb1.189cube.com:8443/plugin/post.php?MAC=584120BEE224&appid=1000000273534113" +
                "&secret=4463d6ab2f0a4afc&PluginName=&Version=1.0","{\n" +
                "    \"type\": \"ubus_call\",\n" +
                "    \"object\": \"ctcapd.wifi.mesh\",\n" +
                "    \"method\": \"get\",\n" +
                "    \"data\": {}\n" +
                "}");
        System.out.println("getResult:"+getResult);
    }


    @Bean
    public static HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
        try {
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    return true;
                }
            }).build();
            httpClientBuilder.setSSLContext(sslContext);
            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
                    hostnameVerifier);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", sslConnectionSocketFactory).build();// 注册http和https请求
            // 开始设置连接池
            PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(
                    socketFactoryRegistry);
            poolingHttpClientConnectionManager.setMaxTotal(2700); // 最大连接数2700
            poolingHttpClientConnectionManager.setDefaultMaxPerRoute(100); // 同路由并发数100
            httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);
            //默认的retryhandler无法捕获TimeoutException
            //httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(3, true)); // 重试次数
            httpClientBuilder.setRetryHandler(getMyRetryHandler());
            org.apache.http.client.HttpClient httpClient = httpClientBuilder.build();
            HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                    httpClient); // httpClient连接配置
            clientHttpRequestFactory.setConnectTimeout(10000); // 连接超时
            clientHttpRequestFactory.setReadTimeout(10000); // 数据读取超时时间
            clientHttpRequestFactory.setConnectionRequestTimeout(5000); // 连接不够用的等待时间
            return clientHttpRequestFactory;
        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            e.printStackTrace();
        }
        return null;
    }

    //自定义retryHandler
    public static HttpRequestRetryHandler getMyRetryHandler(){
        HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
            public boolean retryRequest( IOException exception, int executionCount,HttpContext context) {
                if (executionCount >= 3) {
                    // Do not retry if over max retry count
                    return false;
                }
                if (exception instanceof InterruptedIOException) {
                    // Retry if Timeout,TimeoutException 继承了InterruptedIOException
                    return true;
                }
                if (exception instanceof UnknownHostException) {
                    // Unknown host
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {
                    // Connection refused
                    return false;
                }
                if (exception instanceof SocketTimeoutException) {
                    // Read Timeout refused
                    return true;
                }
                if (exception instanceof SSLException) {
                    // SSL handshake exception
                    return false;
                }
                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                if (idempotent) {
                    // Retry if the request is considered idempotent
                    return true;
                }
                return false;
            }
        };
        return myRetryHandler;
    }
}
