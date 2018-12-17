package com.ys.mgr.util.http.httpclient;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HttpClient工具
 *
 *
 * Date: 2017/12/11 16:09
 */
@Slf4j
public class MyHttpClientUtils {

    private static PoolingHttpClientConnectionManager cm;
    private static SSLConnectionSocketFactory socketFactory;
    private static RequestConfig requestConfig;

    private static final String EMPTY_STR = "";
    private static final String CHARSET = "UTF-8";

    // 重写验证方法，取消检查ssl
    private static TrustManager trustManager = new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    };

    // 初始化initSocketFactory
    private static void initSocketFactory() {
        try {
            if (socketFactory == null) {
                // TLS1.0与SSL3.0基本上没有太大的差别,可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext 
                SSLContext sslContext = SSLContext.getInstance("TLS");
                // 使用TrustManager来初始化该上下文,TrustManager只是被SSL的Socket所使用 
                sslContext.init(null, new TrustManager[]{trustManager}, null);
                // 创建SSLConnectionSocketFactory，NoopHostnameVerifier：关掉了主机名验证，接受任何合法的SSL会话，并且匹配目标主机
                socketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
            }
        } catch (KeyManagementException e) {
            e.printStackTrace();
            log.error("enableSSL error: {}", e.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            log.error("enableSSL error: {}", e.toString());
        }
    }

    // 初始化PoolingHttpClientConnectionManager
    private static void init() {
        if (cm == null) {
            initSocketFactory();

            Registry<ConnectionSocketFactory> r = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE).register("https", socketFactory).build();

            cm = new PoolingHttpClientConnectionManager(r);
            cm.setMaxTotal(200);// 整个连接池最大连接数
            cm.setDefaultMaxPerRoute(20);// 每路由最大连接数

            // 默认的配置
            RequestConfig.Builder configBuilder = RequestConfig.custom();
            configBuilder.setConnectTimeout(5000);
            configBuilder.setSocketTimeout(10000);
            configBuilder.setConnectionRequestTimeout(10000);
            requestConfig = configBuilder.build();
        }
    }

    /**
     * 通过连接池获取HttpClient： CloseableHttpClient
     *
     * @return
     */
    private static CloseableHttpClient getHttpClient() {
        init();
        return HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(requestConfig).build();
    }

    /**
     * 处理不需要参数的get请求
     *
     * @param url               请求地址
     * @param connTimeOutSecond 连接超时时间：秒，一般1-2秒
     * @param readTimeOutSecond 读取超时时间：秒，一般2-5秒
     *
     * @return 请求返回值
     */
    public static MyHttpClientResult httpGetRequest(String url, int connTimeOutSecond, int readTimeOutSecond) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        return getResult(httpGet, connTimeOutSecond, readTimeOutSecond);
    }

    /**
     * 处理需要参数的get请求
     *
     * @param url               请求地址
     * @param params            请求参数
     * @param connTimeOutSecond 连接超时时间：秒，一般1-2秒
     * @param readTimeOutSecond 读取超时时间：秒，一般2-5秒
     *
     * @return 请求返回值
     *
     * @throws URISyntaxException
     */
    public static MyHttpClientResult httpGetRequest(String url, Map<String, String> params, int connTimeOutSecond, int readTimeOutSecond)
            throws Exception {
        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        String qs = EntityUtils.toString(new UrlEncodedFormEntity(pairs, CHARSET));
        HttpGet httpGet = new HttpGet(url + "?" + qs);

        return getResult(httpGet, connTimeOutSecond, readTimeOutSecond);
    }

    /**
     * 处理需要参数和特殊协议头的get请求
     *
     * @param url               请求地址
     * @param headers           请求头
     * @param params            请求参数
     * @param connTimeOutSecond 连接超时时间：秒，一般1-2秒
     * @param readTimeOutSecond 读取超时时间：秒，一般2-5秒
     *
     * @return 请求返回值
     *
     * @throws URISyntaxException
     */
    public static MyHttpClientResult httpGetRequest(String url, Map<String, String> headers, Map<String, String> params, int connTimeOutSecond,
                                                    int readTimeOutSecond) throws Exception {
        return httpGetRequest(url, headers, params, connTimeOutSecond, readTimeOutSecond, false);
    }

    /**
     * 处理需要参数和特殊协议头的get请求
     *
     * @param url               请求地址
     * @param headers           请求头
     * @param params            请求参数
     * @param connTimeOutSecond 连接超时时间：秒，一般1-2秒
     * @param readTimeOutSecond 读取超时时间：秒，一般2-5秒
     * @param useProxy 是否使用代理
     *
     * @return 请求返回值
     *
     * @throws URISyntaxException
     */
    public static MyHttpClientResult httpGetRequest(String url, Map<String, String> headers, Map<String, String> params, int connTimeOutSecond,
                                                    int readTimeOutSecond, Boolean useProxy) throws Exception {
        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        String qs = EntityUtils.toString(new UrlEncodedFormEntity(pairs, CHARSET));
        HttpGet httpGet = new HttpGet(url + "?" + qs);

        for (Map.Entry<String, String> param : headers.entrySet()) {
            httpGet.addHeader(param.getKey(), param.getValue());
        }
        if (useProxy != null && useProxy) {
            return getResult(httpGet, connTimeOutSecond, readTimeOutSecond, true);
        }
        return getResult(httpGet, connTimeOutSecond, readTimeOutSecond, false);
    }

    /**
     * 处理不需要参数的post请求
     *
     * @param url               请求地址
     * @param connTimeOutSecond 连接超时时间：秒，一般1-2秒
     * @param readTimeOutSecond 读取超时时间：秒，一般2-5秒
     *
     * @return 请求返回值
     */
    public static MyHttpClientResult httpPostRequest(String url, int connTimeOutSecond, int readTimeOutSecond) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        return getResult(httpPost, connTimeOutSecond, readTimeOutSecond);
    }

    /**
     * 处理需要参数的post请求
     *
     * @param url               请求地址
     * @param params            请求参数
     * @param connTimeOutSecond 连接超时时间：秒，一般1-2秒
     * @param readTimeOutSecond 读取超时时间：秒，一般2-5秒
     *
     * @return 请求返回值
     *
     * @throws URISyntaxException
     */
    public static MyHttpClientResult httpPostRequest(String url, Map<String, String> params, int connTimeOutSecond, int readTimeOutSecond)
            throws Exception {
        HttpPost httpPost = new HttpPost(url);
        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, CHARSET));
        return getResult(httpPost, connTimeOutSecond, readTimeOutSecond);
    }

    /**
     * 处理需要参数和特殊协议头的post请求
     *
     * @param url               请求地址
     * @param headers           请求头
     * @param params            请求参数
     * @param connTimeOutSecond 连接超时时间：秒，一般1-2秒
     * @param readTimeOutSecond 读取超时时间：秒，一般2-5秒
     *
     * @return 请求返回值
     *
     * @throws URISyntaxException
     */
    public static MyHttpClientResult httpPostRequest(String url, Map<String, String> headers, Map<String, String> params, int connTimeOutSecond,
                                                     int readTimeOutSecond) throws Exception {
        return httpPostRequest(url, headers, params, connTimeOutSecond, readTimeOutSecond, false);
    }

    /**
     * 处理需要参数和特殊协议头的post请求
     *
     * @param url               请求地址
     * @param headers           请求头
     * @param params            请求参数
     * @param connTimeOutSecond 连接超时时间：秒，一般1-2秒
     * @param readTimeOutSecond 读取超时时间：秒，一般2-5秒
     * @param useProxy 是否使用代理
     *
     * @return 请求返回值
     *
     * @throws URISyntaxException
     */
    public static MyHttpClientResult httpPostRequest(String url, Map<String, String> headers, Map<String, String> params, int connTimeOutSecond,
                                                     int readTimeOutSecond, Boolean useProxy) throws Exception {
        HttpPost httpPost = new HttpPost(url);

        for (Map.Entry<String, String> param : headers.entrySet()) {
            httpPost.addHeader(param.getKey(), param.getValue());
        }

        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, CHARSET));
        if (useProxy != null && useProxy) {
            return getResult(httpPost, connTimeOutSecond, readTimeOutSecond, true);
        }
        return getResult(httpPost, connTimeOutSecond, readTimeOutSecond, false);
    }

    /**
     * 处理需要参数的post请求，json参数
     *
     * @param url               请求地址
     * @param jsonParams        json参数
     * @param connTimeOutSecond 连接超时时间：秒，一般1-2秒
     * @param readTimeOutSecond 读取超时时间：秒，一般2-5秒
     *
     * @return 请求返回值
     *
     * @throws URISyntaxException
     */
    public static MyHttpClientResult httpPostRequest(String url, String jsonParams, int connTimeOutSecond, int readTimeOutSecond)
            throws Exception {
        HttpPost httpPost = new HttpPost(url);
        if (jsonParams != null && jsonParams.trim().length() > 0) {
            StringEntity entity = new StringEntity(jsonParams, ContentType.APPLICATION_JSON);
            entity.setContentEncoding(CHARSET);
            httpPost.setEntity(entity);
        }
        return getResult(httpPost, connTimeOutSecond, readTimeOutSecond);
    }

    /**
     * 处理需要参数和特殊协议头的post请求，json参数
     *
     * @param url               请求地址
     * @param headers           请求头
     * @param jsonParams        json参数
     * @param connTimeOutSecond 连接超时时间：秒，一般1-2秒
     * @param readTimeOutSecond 读取超时时间：秒，一般2-5秒
     *
     * @return 请求返回值
     *
     * @throws URISyntaxException
     */
    public static MyHttpClientResult httpPostRequest(String url, Map<String, String> headers, String jsonParams, int connTimeOutSecond, int readTimeOutSecond)
            throws Exception {
        HttpPost httpPost = new HttpPost(url);

        for (Map.Entry<String, String> param : headers.entrySet()) {
            httpPost.addHeader(param.getKey(), param.getValue());
        }

        if (jsonParams != null && jsonParams.trim().length() > 0) {
            StringEntity entity = new StringEntity(jsonParams, ContentType.APPLICATION_JSON);
            entity.setContentEncoding(CHARSET);
            httpPost.setEntity(entity);
        }
        return getResult(httpPost, connTimeOutSecond, readTimeOutSecond);
    }

    /**
     * 处理文件上传
     *
     * @param url    请求地址
     * @param files  文件
     * @param params 其他参数
     *
     * @return 请求返回值
     *
     * @throws Exception
     */
    public static MyHttpClientResult httpUploadFile(String url, Map<String, File> files, Map<String, String> params) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();

        if (files != null && !files.isEmpty()) {
            for (Map.Entry<String, File> entry : files.entrySet()) {
                entityBuilder.addBinaryBody(entry.getKey(), entry.getValue());
            }
        }

        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                entityBuilder.addTextBody(entry.getKey(), entry.getValue());
            }
        }
        HttpEntity requestEntity = entityBuilder.build();
        httpPost.setEntity(requestEntity);

        return getResult(httpPost, 10, 20);
    }

    /**
     * 处理文件上传
     *
     * @param url    请求地址
     * @param files  文件
     * @param params 其他参数
     *
     * @return 请求返回值
     *
     * @throws Exception
     */
    public static MyHttpClientResult httpUploadFile(String url, Map<String, String> headers, Map<String, File> files, Map<String, String> params) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        for (Map.Entry<String, String> param : headers.entrySet()) {
            httpPost.addHeader(param.getKey(), param.getValue());
        }

        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        if (files != null && !files.isEmpty()) {
            for (Map.Entry<String, File> entry : files.entrySet()) {
                entityBuilder.addBinaryBody(entry.getKey(), entry.getValue());
            }
        }

        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                entityBuilder.addTextBody(entry.getKey(), entry.getValue());
            }
        }
        HttpEntity requestEntity = entityBuilder.build();
        httpPost.setEntity(requestEntity);

        return getResult(httpPost, 10, 20);
    }

    public static MyHttpClientResult fileSync(String url, String token, List<MyFileSyncEntity> fileSyncEntitieList) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        entityBuilder.addTextBody("token", token);
        for (int i = 0; i < fileSyncEntitieList.size(); i++) {
            MyFileSyncEntity fileSyncEntity = fileSyncEntitieList.get(i);
            entityBuilder.addTextBody("fileMd5" + i, fileSyncEntity.getFileMd5());
            entityBuilder.addTextBody("webPath" + i, fileSyncEntity.getWebPath());
            entityBuilder.addBinaryBody("file" + i, fileSyncEntity.getFile());
        }
        HttpEntity requestEntity = entityBuilder.build();
        httpPost.setEntity(requestEntity);
        return getResult(httpPost, 10, 20);
    }

    /**
     * 设置转换参数
     *
     * @param params
     *
     * @return
     */
    private static ArrayList<NameValuePair> covertParams2NVPS(Map<String, String> params) throws Exception {
        if (params == null || params.isEmpty()) {
            return new ArrayList<NameValuePair>(0);
        }

        ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
        for (Map.Entry<String, String> param : params.entrySet()) {
            pairs.add(new BasicNameValuePair(param.getKey(), param.getValue()));
        }
        return pairs;
    }

    /**
     * 处理Http get/post请求
     *
     * @param request
     *
     * @return
     */
    private static MyHttpClientResult getResult(HttpRequestBase request, int connTimeOutSecond, int readTimeOutSecond) throws Exception {
        return getResult(request, connTimeOutSecond, readTimeOutSecond, false);
    }

    /**
     * 处理Http get/post请求
     *
     * @param request
     *
     * @return
     */
    private static MyHttpClientResult getResult(HttpRequestBase request, int connTimeOutSecond, int readTimeOutSecond, boolean useProxy) throws Exception {
        CloseableHttpClient httpClient = getHttpClient();
        MyHttpClientResult rstObj = new MyHttpClientResult();
        try {

            // 设置超时时间
            if (connTimeOutSecond < 1)
                connTimeOutSecond = 1;
            if (readTimeOutSecond < 1)
                readTimeOutSecond = 1;

            RequestConfig requestConfig;
            if(useProxy) {
                MyProxyIp proxyIp = MyProxyIpUtils.getProxyIp();
                HttpHost proxy=new HttpHost(proxyIp.getIp(), proxyIp.getPort());
                requestConfig = RequestConfig.custom().setProxy(proxy).setConnectTimeout(connTimeOutSecond * 1000)
                        .setSocketTimeout(readTimeOutSecond * 1000).build();
            } else {
                requestConfig = RequestConfig.custom().setConnectTimeout(connTimeOutSecond * 1000)
                        .setSocketTimeout(readTimeOutSecond * 1000).build();
            }
            request.setConfig(requestConfig);

            log.debug("request url: {} ", request.toString());
            CloseableHttpResponse response = httpClient.execute(request);

            rstObj.setCode(response.getStatusLine().getStatusCode());
            log.debug("response statusLine: {}", response.getStatusLine().toString());

            HttpEntity entity;
            String result = EMPTY_STR;
            try {
                entity = response.getEntity();
                if (entity != null) {
                    log.debug("resultContentLength: {}", entity.getContentLength());// -1 表示长度未知
                    result = EntityUtils.toString(entity, CHARSET);// 设置为utf-8编码，否则服务器端没有设置编码的话会出现中文乱码
                }
            } finally {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    log.error("response error ", e);
                }
            }

            log.info("request url: {}，response result: {} ", request.toString(), result == null ? "" : (result.trim().length() > 100 ? result
                    .trim().subSequence(0, 100) : result.trim()));
            rstObj.setResult(result);
            return rstObj;
        } catch (ClientProtocolException e) {
            log.error("getResult error: {}", e.toString());
            throw e;
        } catch (IOException e) {
            log.error("getResult error: {}", e.toString());
            throw e;
        } finally {
            // httpClient.close(); //TODO 需要手动关闭吗？
        }
    }

    /**
     * 处理不需要参数的get请求
     *
     * @param url               请求地址
     * @param connTimeOutSecond 连接超时时间：秒，一般1-2秒
     * @param readTimeOutSecond 读取超时时间：秒，一般2-5秒
     *
     * @return 请求返回值
     */
    public static MyHttpClientResult httpDownloadRequest(String url, String outPath, int connTimeOutSecond, int readTimeOutSecond) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        return downloadResult(outPath, httpGet, connTimeOutSecond, readTimeOutSecond);
    }

    /**
     * 处理不需要参数的get请求
     *
     * @param url               请求地址
     * @param connTimeOutSecond 连接超时时间：秒，一般1-2秒
     * @param readTimeOutSecond 读取超时时间：秒，一般2-5秒
     *
     * @return 请求返回值
     */
    public static MyHttpClientResult httpDownloadRequest(String url, String outPath, Map<String, String> params,
                                                         int connTimeOutSecond, int readTimeOutSecond) throws Exception {
        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        String qs = EntityUtils.toString(new UrlEncodedFormEntity(pairs, CHARSET));
        HttpGet httpGet = new HttpGet(url + "?" + qs);

        return downloadResult(outPath, httpGet, connTimeOutSecond, readTimeOutSecond);
    }

    /**
     * 处理不需要参数的get请求
     *
     * @param url               请求地址
     * @param connTimeOutSecond 连接超时时间：秒，一般1-2秒
     * @param readTimeOutSecond 读取超时时间：秒，一般2-5秒
     *
     * @return 请求返回值
     */
    public static MyHttpClientResult httpDownloadRequest(String url, String outPath, Map<String, String> headers,
                                                         Map<String, String> params, int connTimeOutSecond, int readTimeOutSecond) throws Exception {
        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        String qs = EntityUtils.toString(new UrlEncodedFormEntity(pairs, CHARSET));
        HttpGet httpGet = new HttpGet(url + "?" + qs);

        for (Map.Entry<String, String> param : headers.entrySet()) {
            httpGet.addHeader(param.getKey(), param.getValue());
        }

        return downloadResult(outPath, httpGet, connTimeOutSecond, readTimeOutSecond);
    }

    /**
     * 处理Http get/post请求
     *
     * @param request
     *
     * @return
     */
    private static MyHttpClientResult downloadResult(String outPath, HttpRequestBase request, int connTimeOutSecond, int readTimeOutSecond) throws Exception {
        CloseableHttpClient httpClient = getHttpClient();
        MyHttpClientResult rstObj = new MyHttpClientResult();
        try {

            // 设置超时时间
            if (connTimeOutSecond < 1)
                connTimeOutSecond = 3;
            if (readTimeOutSecond < 1)
                readTimeOutSecond = 5;

            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connTimeOutSecond * 1000)
                    .setSocketTimeout(readTimeOutSecond * 1000).build();
            request.setConfig(requestConfig);

            log.debug("request url: {} ", request.toString());
            CloseableHttpResponse response = httpClient.execute(request);

            rstObj.setCode(response.getStatusLine().getStatusCode());
            log.debug("response statusLine: {}", response.getStatusLine().toString());

            HttpEntity entity;

            String result = outPath;
            InputStream input = null;
            FileOutputStream out = null;
            try {
                entity = response.getEntity();
                if (entity != null) {
                    input = entity.getContent();
                    int i = -1;
                    byte[] byt = new byte[1024];
                    out = new FileOutputStream(result);
                    while ((i = input.read(byt)) != -1) {
                        out.write(byt);
                    }
                }
            } finally {
                try {
                    EntityUtils.consume(response.getEntity());
                    out.close();
                    input.close();
                } catch (IOException e) {
                    log.error("response error ", e);
                }
            }

            rstObj.setResult(result);
            return rstObj;
        } catch (ClientProtocolException e) {
            log.error("getResult error: {}", e.toString());
            throw e;
        } catch (IOException e) {
            log.error("getResult error: {}", e.toString());
            throw e;
        } finally {
            // httpClient.close(); //TODO 需要手动关闭吗？
        }
    }

}
