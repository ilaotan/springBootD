package com.springBootD.wx.kit;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by tan on 2017/2/28.
 */
public class HttpUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    private static final String UTF_8 = "UTF-8";
    public  static final String tokenUrl = "apiResource/token";
    public  static final String alarmUrl = "deviceResource/devicePalpitate";
    public  static final String driveInOutUrl = "deviceResource/carStatusChange";

    private static HttpClient instance = null;

    static {

        HttpParams params = new BasicHttpParams();
        //设置连接超时时间
        Integer CONNECTION_TIMEOUT = 10 * 1000; //设置请求超时2秒钟 根据业务调整
        Integer SO_TIMEOUT = 10 * 1000; //设置等待数据超时时间2秒钟 根据业务调整
        //定义了当从ClientConnectionManager中检索ManagedClientConnection实例时使用的毫秒级的超时时间
        //这个参数期望得到一个java.lang.Long类型的值。如果这个参数没有被设置，默认等于CONNECTION_TIMEOUT，因此一定要设置
        Long CONN_MANAGER_TIMEOUT = 500L; //该值就是连接不够用的时候等待超时时间，一定要设置，而且不能太大 ()

        params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
        params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);
        params.setLongParameter(ClientPNames.CONN_MANAGER_TIMEOUT, CONN_MANAGER_TIMEOUT);
        //在提交请求之前 测试连接是否可用
        params.setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, true);


        SchemeRegistry schReg = new SchemeRegistry();
        schReg.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
        schReg.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));

        PoolingClientConnectionManager conMgr = new PoolingClientConnectionManager(schReg);
        conMgr.setMaxTotal(200); //设置整个连接池最大连接数 根据自己的场景决定
        //是路由的默认最大连接（该值默认为2），限制数量实际使用DefaultMaxPerRoute并非MaxTotal。
        //设置过小无法支持大并发(ConnectionPoolTimeoutException: Timeout waiting for connection from pool)，路由是对maxTotal的细分。
        conMgr.setDefaultMaxPerRoute(conMgr.getMaxTotal());//（目前只有一个路由，因此让他等于最大值）

        //另外设置http client的重试次数，默认是3次；当前是禁用掉（如果项目量不到，这个默认即可）
        //instance.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(0, false));

        instance = new DefaultHttpClient(conMgr,params);
    }

    public static String post(String url,Map<String,String> paramsMap) {

        HttpClient httpClient = getHttpClient();

        List<NameValuePair> param =new ArrayList<>();

        for(String key:paramsMap.keySet()){
            param.add(new BasicNameValuePair(key, paramsMap.get(key)));
        }

        HttpPost post = new HttpPost(url);
        // 创建参数队列
        UrlEncodedFormEntity uefEntity;
        String resStr = null;
        HttpResponse response = null;
        try {
            uefEntity = new UrlEncodedFormEntity(param, UTF_8);
            post.setEntity(uefEntity);
            response = httpClient.execute(post);

            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    resStr = EntityUtils.toString(entity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("POST-->"+url+" ===== "+ JSON.toJSONString(paramsMap)+" ===== "+e.getMessage());
        } finally {
            if(response != null) {
                try {
                    EntityUtils.consume(response.getEntity()); //会自动释放连接
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
//             httpClient.getConnectionManager().shutdown();
        }
        return resStr;
    }



    public static String get(String url,Map<String,String> paramsMap) {

        HttpClient httpClient = getHttpClient();

        url = buildGetUrl(url,paramsMap);

        String resStr = null;
        HttpResponse response = null;
        try {
            HttpGet httpget = new HttpGet(url);
            response = httpClient.execute(httpget);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    resStr= EntityUtils.toString(entity);
                }
            }
        }catch (Exception e){
           e.printStackTrace();
            logger.error("GET-->"+url+" ===== "+ JSON.toJSONString(paramsMap)+" ===== "+e.getMessage());
        }finally {
            if(response != null) {
                try {
                    EntityUtils.consume(response.getEntity()); //会自动释放连接
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
//             httpClient.getConnectionManager().shutdown();
        }
        return resStr;
    }

    public static HttpClient getHttpClient(){
        return instance;
    }



    private static String buildGetUrl(String url, Map<String, String> params) {

        StringBuilder uriStr = new StringBuilder(url);
        if (params != null) {
            List<NameValuePair> ps = new ArrayList<>();
            for (String key : params.keySet()) {
                ps.add(new BasicNameValuePair(key, params.get(key)));
            }
            uriStr.append("?");
            uriStr.append(URLEncodedUtils.format(ps, UTF_8));
        }
        return uriStr.toString();
    }


}
