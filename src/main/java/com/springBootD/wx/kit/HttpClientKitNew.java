package com.springBootD.wx.kit;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.*;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.*;

/**
 * Created by tan on 2016/9/8.
 *
 *  本工具类的初衷是学习httpClient 4.5 系列最新版的使用姿势
 *
 */
public class HttpClientKitNew {

    private static CookieStore cookieStore = new BasicCookieStore();

    private static HttpClientContext context  = HttpClientContext.create();

    private static String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36";


    private static CloseableHttpClient httpClient_;

    public static CloseableHttpClient instance() {
        System.setProperty ("jsse.enableSNIExtension", "false");    //防止https请求出错  see: //http://stackoverflow.com/questions/7615645/ssl-handshake-alert-unrecognized-name-error-since-upgrade-to-java-1-7-0
//        context.setCookieStore(cookieStore);

//        CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();

        httpClient_ = HttpClients.custom()
//                .setConnectionManager(createDefaultConnectionManager())
                .setDefaultRequestConfig(createDefaultRequestConfig())
                .setDefaultCookieStore(cookieStore)
                .setDefaultHeaders(createDefaultHeader("",userAgent))
                .build();
        return httpClient_;
    }

    public static CloseableHttpClient instanceNoRedirect() {
        System.setProperty ("jsse.enableSNIExtension", "false");    //防止https请求出错
//        context.setCookieStore(cookieStore);

        httpClient_ = HttpClients.custom()
//                .setConnectionManager(createDefaultConnectionManager())
                .setDefaultRequestConfig(createDefaultRequestConfig())
                .setDefaultCookieStore(cookieStore)
                .setDefaultHeaders(createDefaultHeader("",userAgent))
                .disableRedirectHandling()
                .build();
        return httpClient_;
    }

    private static URI makeURI(String url, Map<String,String> params){
        URIBuilder builder = null;
        try {
            builder = new URIBuilder(url);
            if(params!=null && params.size()>0){
                List<NameValuePair> nvps = new ArrayList<>();
                for (String key : params.keySet()) {
                    nvps.add(new BasicNameValuePair(key, params.get(key)));
                }
                builder.setParameters(nvps);
            }
            return builder.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String doGet(String url,Map<String,String> params){

//        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpClient httpclient = HttpClientKitNew.instance();

        return doGetReal(httpclient,makeURI(url, params));
    }

    public static String doGetAndNoRedirect(String url, Map<String,String> params){

//        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpClient httpclient = HttpClientKitNew.instanceNoRedirect();
        return doGetReal(httpclient,makeURI(url, params));

    }

    private static String doGetReal(CloseableHttpClient httpclient,URI uri){
        String res="";
        try {
            HttpGet httpGet = new HttpGet(uri);
            CloseableHttpResponse response = httpclient.execute(httpGet,context);
            res= getResponse(response);
            closeResponse(response);
            return res;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeHttpClient(httpclient);
        }
        return res;
    }


    public static String doPost(String url,StringEntity stringEntity){

        CloseableHttpClient httpclient = HttpClientKitNew.instance();

        String res = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            if(stringEntity!=null){
                httpPost.setEntity(stringEntity);
            }
            CloseableHttpResponse response = httpclient.execute(httpPost,context);
            res= getResponse(response);
            closeResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeHttpClient(httpclient);
        }
        return res;
    }

    public static void doGetFile(String url,Map<String,String> params,String fileSavePath){

        CloseableHttpClient httpclient = HttpClientKitNew.instance();
//        CloseableHttpClient httpclient =  HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(makeURI(url, params));
            CloseableHttpResponse response = httpclient.execute(httpGet,context);
            getFileResponse(response,fileSavePath);
            closeResponse(response);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeHttpClient(httpclient);
        }
    }

    private static void getFileResponse(CloseableHttpResponse response,String fileSavePath){
        InputStream is=null;
        try {
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            OutputStream output = new FileOutputStream(new File(fileSavePath));
//            OutputStream output = new FileOutputStream(new File("E:\\qr.jpg"));
            IOUtils.copy(is, output);
            output.flush();
            is.close();

//            EntityUtils.consume(entity);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static String getResponse(CloseableHttpResponse response){
        String res=null;
        try {
            HttpEntity entity = response.getEntity();
            res= EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
        } catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    private static void closeResponse(CloseableHttpResponse response){
        try {
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void closeHttpClient( CloseableHttpClient httpclient){
        try {
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String getCookieVal(String key){
        List<Cookie> cookieList = cookieStore.getCookies();
        for(Cookie cookie:cookieList){
            if(cookie.getName().equals(key)){
                return cookie.getValue();
            }
        }
        return null;
    }

    public static void saveCookie(){

        List<Cookie> cookieList = cookieStore.getCookies();
        for(Cookie cookie:cookieList){
            System.out.println(cookie.toString());
            System.out.println(JSON.toJSONString(cookie));
        }
        System.out.println(cookieStore);
        System.out.println(JSON.toJSONString(cookieStore));
    }


    private static List<Header> createDefaultHeader(String referer,String userAgent) {
        ArrayList<Header> headers = new ArrayList<>();
        Header header = new BasicHeader(HttpHeaders.USER_AGENT, userAgent);
        headers.add(header);
        if (!StringUtils.isEmpty(referer)) {
            headers.add(new BasicHeader(HttpHeaders.REFERER, referer));
        }
        return headers;
    }


    private static PoolingHttpClientConnectionManager createDefaultConnectionManager(){
        PoolingHttpClientConnectionManager connectionManager = null;
        try {
            System.setProperty ("jsse.enableSNIExtension", "false");    //防止https请求出错  see: //http://stackoverflow.com/questions/7615645/ssl-handshake-alert-unrecognized-name-error-since-upgrade-to-java-1-7-0
            ConnectionSocketFactory socketFactory = PlainConnectionSocketFactory.getSocketFactory();
            LayeredConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactory.getSocketFactory();
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", socketFactory)
                    .register("https", sslSocketFactory)
                    .build();

            connectionManager = new PoolingHttpClientConnectionManager(registry);
            connectionManager.setMaxTotal(200);
            connectionManager.setDefaultMaxPerRoute(20);
            HttpHost localhost = new HttpHost(InetAddress.getLocalHost());
            connectionManager.setMaxPerRoute(new HttpRoute(localhost), 50);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return connectionManager;
    }

    private static RequestConfig createDefaultRequestConfig() {
        return RequestConfig.custom()
                .setCookieSpec(CookieSpecs.STANDARD_STRICT)
                .setExpectContinueEnabled(true)
                .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
    }




    public static void main(String[] args) throws Exception{

        Map<String,Object> map = new HashMap<>();

        String html = doGet("https://segmentfault.com/q/1010000000576240",null);
        System.out.println("333333333333333");

//        HttpGet request = new HttpGet("http://example.com/?var=1&var=2");
//
//        URIBuilder newBuilder = new URIBuilder(request.getURI());
//        List<NameValuePair> params = newBuilder.getQueryParams();

    }

}
