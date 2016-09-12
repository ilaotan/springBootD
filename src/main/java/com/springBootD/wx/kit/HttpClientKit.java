//package com.springBootD.wx.kit;
//
//import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
//import org.apache.http.*;
//import org.apache.http.client.CookieStore;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.config.AuthSchemes;
//import org.apache.http.client.config.CookieSpecs;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.utils.URIBuilder;
//import org.apache.http.config.Registry;
//import org.apache.http.config.RegistryBuilder;
//import org.apache.http.conn.routing.HttpRoute;
//import org.apache.http.conn.socket.ConnectionSocketFactory;
//import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
//import org.apache.http.conn.socket.PlainConnectionSocketFactory;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.apache.http.cookie.Cookie;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.*;
//import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
//import org.apache.http.impl.cookie.BasicClientCookie;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.protocol.BasicHttpContext;
//import org.apache.http.protocol.HttpContext;
//import org.apache.http.util.EntityUtils;
//import org.apache.tomcat.util.http.fileupload.IOUtils;
//
//import java.io.*;
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//import java.util.*;
//
///**
// * Created by tan on 2016/9/8.
// *
// *  本工具类的初衷是学习httpClient 4.5 系列最新版的使用姿势
// *
// */
//public class HttpClientKit {
//
//    private static CookieStore cookieStore = new BasicCookieStore();
//
//    private static String charset = "UTF8";
//
//    private static CloseableHttpClient httpClient_;
//
//    public static CloseableHttpClient instance() {
//        return httpClient_;
//    }
//
//    public static String doGet(String url,Map<String,String> params){
//
//        URIBuilder builder = null;
//        try {
//            builder = new URIBuilder(url);
//            if(params!=null && params.size()>0){
//                List<NameValuePair> nvps = new ArrayList<>();
//                for (String key : params.keySet()) {
//                    nvps.add(new BasicNameValuePair(key, params.get(key)));
//                }
//                builder.setParameters(nvps);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
////        CloseableHttpClient httpclient = HttpClients.createDefault();
//        CloseableHttpClient httpclient = HttpClientKit.instance();
////        CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
//
//        String res="";
//        try {
//            HttpGet httpGet = new HttpGet(builder.build());
//            CloseableHttpResponse response = httpclient.execute(httpGet);
//            res= getResponse(response);
//            closeResponse(response);
//            return res;
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            closeHttpClient(httpclient);
//        }
//        return res;
//    }
//
//    public static String doGetAndSaveCookie(String url, Map<String,String> params){
//
//        URIBuilder builder = null;
//        try {
//            builder = new URIBuilder(url);
//            if(params!=null && params.size()>0){
//                List<NameValuePair> nvps = new ArrayList<>();
//                for (String key : params.keySet()) {
//                    nvps.add(new BasicNameValuePair(key, params.get(key)));
//                }
//                builder.setParameters(nvps);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
////        CloseableHttpClient httpclient = HttpClients.createDefault();
////        CloseableHttpClient httpclient = HttpClientKit.instance();
//        CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
//
//        HttpContext httpContext = new BasicHttpContext();
//
//        String res="";
//        try {
//            HttpGet httpGet = new HttpGet(builder.build());
//            CloseableHttpResponse response = httpclient.execute(httpGet,httpContext);
//            System.out.println(ReflectionToStringBuilder.toString(httpContext));
//            mergeCookies( (BasicCookieStore) httpContext.getAttribute("http.cookie-store"));
//            res= getResponse(response);
//            closeResponse(response);
//            return res;
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            closeHttpClient(httpclient);
//        }
//        return res;
//    }
//    public static String doGetAndNoRedirectSaveCookie(String url, Map<String,String> params){
//
//        URIBuilder builder = null;
//        try {
//            builder = new URIBuilder(url);
//            if(params!=null && params.size()>0){
//                List<NameValuePair> nvps = new ArrayList<>();
//                for (String key : params.keySet()) {
//                    nvps.add(new BasicNameValuePair(key, params.get(key)));
//                }
//                builder.setParameters(nvps);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).disableRedirectHandling().build();
////        CloseableHttpClient httpclient = HttpClients.createDefault();
////        CloseableHttpClient httpclient = HttpClientKit.instance();
//        HttpContext httpContext = new BasicHttpContext();
//
//        String res="";
//        try {
//            HttpGet httpGet = new HttpGet(builder.build());
//            CloseableHttpResponse response = httpclient.execute(httpGet,httpContext);
//            System.out.println(ReflectionToStringBuilder.toString(httpContext));
//            mergeCookies( (BasicCookieStore) httpContext.getAttribute("http.cookie-store"));
//            res= getResponse(response);
//            closeResponse(response);
//            return res;
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            closeHttpClient(httpclient);
//        }
//        return res;
//    }
//
//
//    public static String doPost(String url,StringEntity stringEntity){
//
////        CloseableHttpClient httpclient =  HttpClients.createDefault();
////        CloseableHttpClient httpclient = HttpClientKit.instance();
//        CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
//
//
//        String res = "";
//        try {
//            HttpPost httpPost = new HttpPost(url);
//            if(stringEntity!=null){
//                httpPost.setEntity(stringEntity);
//            }
//            CloseableHttpResponse response = httpclient.execute(httpPost);
//            res= getResponse(response);
//            closeResponse(response);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            closeHttpClient(httpclient);
//        }
//        return res;
//    }
//
//    public static void doGetFile(String url,Map<String,String> params,String fileSavePath){
//
//        URIBuilder builder = null;
//        try {
//            builder = new URIBuilder(url);
//            if(params!=null && params.size()>0){
//                List<NameValuePair> nvps = new ArrayList<>();
//                for (String key : params.keySet()) {
//                    nvps.add(new BasicNameValuePair(key, params.get(key)));
//                }
//                builder.setParameters(nvps);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
////        CloseableHttpClient httpclient = HttpClientKit.instance();
//        CloseableHttpClient httpclient =  HttpClients.createDefault();
//        try {
//            HttpGet httpGet = new HttpGet(builder.build());
//            CloseableHttpResponse response = httpclient.execute(httpGet);
//            getFileResponse(response,fileSavePath);
//            closeResponse(response);
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            closeHttpClient(httpclient);
//        }
//    }
//
//    private static void getFileResponse(CloseableHttpResponse response,String fileSavePath){
//        InputStream is=null;
//        try {
//            HttpEntity entity = response.getEntity();
//            is = entity.getContent();
//            OutputStream output = new FileOutputStream(new File(fileSavePath));
////            OutputStream output = new FileOutputStream(new File("E:\\qr.jpg"));
//            IOUtils.copy(is, output);
//            output.flush();
//            is.close();
//
////            EntityUtils.consume(entity);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    private static String getResponse(CloseableHttpResponse response){
//        String res=null;
//        try {
//            HttpEntity entity = response.getEntity();
//            res= EntityUtils.toString(entity, "UTF8");
//            EntityUtils.consume(entity);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        return res;
//    }
//
//    private static void closeResponse(CloseableHttpResponse response){
//        try {
//            response.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void closeHttpClient( CloseableHttpClient httpclient){
//        System.out.println("关闭"+httpclient);
////        try {
////            httpclient.close();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//    }
////
////    private static void setCookieStore(HttpResponse httpResponse) {
////        // JSESSIONID
////        Header header = httpResponse.getFirstHeader("Set-Cookie");
////        if(header==null){
////            return;
////        }
////        String setCookie = header.getValue();
////        System.out.println("setCookie------->"+setCookie);
////        String JSESSIONID = setCookie.substring("JSESSIONID=".length(),setCookie.indexOf(";"));
////        // 新建一个Cookie
////        BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", JSESSIONID);
////        cookie.setVersion(0);
////        cookie.setDomain("127.0.0.1");
////        cookie.setPath("/CwlProClient");
////        // cookie.setAttribute(ClientCookie.VERSION_ATTR, "0");
////        // cookie.setAttribute(ClientCookie.DOMAIN_ATTR, "127.0.0.1");
////        // cookie.setAttribute(ClientCookie.PORT_ATTR, "8080");
////        // cookie.setAttribute(ClientCookie.PATH_ATTR, "/CwlProWeb");
////        cookieStore.addCookie(cookie);
////    }
//
//    private static void mergeCookies(BasicCookieStore basicCookieStore){
//        if(basicCookieStore!=null){
//            List<Cookie> cookieList = basicCookieStore.getCookies();
//            for(Cookie cookie:cookieList){
//                cookieStore.addCookie(cookie);
//            }
//        }
//    }
//
//    public static String getCookieVal(String key){
//        List<Cookie> cookieList = cookieStore.getCookies();
//        for(Cookie cookie:cookieList){
//            if(cookie.getName().equals("key")){
//                return cookie.getValue();
//            }
//        }
//        return null;
//    }
//
////
////    private List<Header> defaultHeader() {
////        ArrayList<Header> headers = new ArrayList<Header>();
////        Header header = new BasicHeader(HttpHeaders.USER_AGENT, UserAgent);
////        headers.add(header);
////        if (!StringUtils.isEmpty(referer)) {
////            headers.add(new BasicHeader(HttpHeaders.REFERER, referer));
////        }
////        if (!StringUtils.isEmpty(cookie)) {
////            headers.add(new BasicHeader("Cookie", cookie));
////        }
////        return headers;
////    }
//
//    static {
//        try {
//            httpClient_ = HttpClients.custom()
//                    .setConnectionManager(createDefaultConnectionManager())
//                    .setDefaultRequestConfig(createDefaultRequestConfig())
//                    .setDefaultCookieStore(cookieStore)
//                    .build();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static PoolingHttpClientConnectionManager createDefaultConnectionManager() throws UnknownHostException{
//        System.setProperty ("jsse.enableSNIExtension", "false");    //防止https请求出错  see: //http://stackoverflow.com/questions/7615645/ssl-handshake-alert-unrecognized-name-error-since-upgrade-to-java-1-7-0
//        ConnectionSocketFactory socketFactory = PlainConnectionSocketFactory.getSocketFactory();
//        LayeredConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactory.getSocketFactory();
//        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
//                .register("http", socketFactory)
//                .register("https", sslSocketFactory)
//                .build();
//
//        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
//        connectionManager.setMaxTotal(200);
//        connectionManager.setDefaultMaxPerRoute(20);
//        HttpHost localhost = new HttpHost(InetAddress.getLocalHost());
//        connectionManager.setMaxPerRoute(new HttpRoute(localhost), 50);
//        return connectionManager;
//    }
//
//    private static RequestConfig createDefaultRequestConfig() {
//        return RequestConfig.custom()
//                .setCookieSpec(CookieSpecs.STANDARD_STRICT)
//                .setExpectContinueEnabled(true)
//                .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
//                .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
//    }
//
//
//
//
//    public static void main(String[] args) throws Exception{
//
//        Map<String,Object> map = new HashMap<>();
//
//        String html = doGet("https://segmentfault.com/q/1010000000576240",null);
//        System.out.println("333333333333333");
//
////        HttpGet request = new HttpGet("http://example.com/?var=1&var=2");
////
////        URIBuilder newBuilder = new URIBuilder(request.getURI());
////        List<NameValuePair> params = newBuilder.getQueryParams();
//
//    }
//
//}
