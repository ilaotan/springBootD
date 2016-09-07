package com.springBootD.wx.service;

import com.alibaba.fastjson.JSON;
import com.springBootD.wx.model.Fan;
import com.springBootD.wx.model.LoginJson;
import com.springBootD.wx.model.WeChatErrorCode;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tan on 2016/9/6.
 */
@Service
public class WxService implements WechatContents{

    private static final Log logger = LogFactory.getLog(WxService.class);


    private String cookiestr;
    private String token;
    private int loginErrCode;
    private List<Fan> fans = new ArrayList<Fan>();
    public boolean isLogin = false;
    private HttpClient client = new HttpClient();

    @Value("${wechat.appid}")  private String wechatAppid;
    @Value("${wechat.username}") private String wechatUsername;
    @Value("${wechat.password}") private String wechatPassword;

    public void weChatLogin() {
        try {
            PostMethod post = new PostMethod(LOGIN_URL);
            post.setRequestHeader(REFERER_H, WECHAT_PF_URL);
            post.setRequestHeader(USER_AGENT_H, USER_AGENT);
            NameValuePair[] params = new NameValuePair[] {
                    new NameValuePair("username", wechatUsername),
                    new NameValuePair("pwd", DigestUtils.md5Hex(wechatPassword
                            .getBytes())), new NameValuePair("f", "json"),
                    new NameValuePair("imagecode", "") };
            post.setQueryString(params);
            if (client.executeMethod(post) == HttpStatus.SC_OK) {
                String ret = post.getResponseBodyAsString();
                LoginJson retcode = JSON.parseObject(ret, LoginJson.class);

                if (retcode.getBase_resp().get("err_msg").equals("ok")) {
                    StringBuffer cookie = new StringBuffer();
                    for (Cookie c : client.getState().getCookies()) {
                        cookie.append(c.getName()).append("=")
                                .append(c.getValue()).append(";");
                    }
                    this.cookiestr = cookie.toString();
                    this.isLogin = true;
                    System.out.println(ret);
                    this.token = getToken(retcode.getRedirect_url());
                }
                int errCode = Integer.parseInt(retcode.getBase_resp().get("ret"));
                this.loginErrCode = errCode;
                String loginErrMsg;
                if (WeChatErrorCode.getItems().containsKey(loginErrCode)) {
                    loginErrMsg = WeChatErrorCode.getValue(loginErrCode);
                } else {
                    loginErrMsg = "未知的返回";
                }
                logger.info(loginErrMsg);
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     * 从登录成功的信息中分离出token信息
     *
     * @param s
     * @return
     */
    private String getToken(String s) {
        System.out.println(s);
        try {
            if (StringUtils.isBlank(s))
                return null;
            String[] ss = StringUtils.split(s, "?");
            String[] params = null;
            if (ss.length == 2) {
                if (!StringUtils.isBlank(ss[1]))
                    params = StringUtils.split(ss[1], "&");
            } else if (ss.length == 1) {
                if (!StringUtils.isBlank(ss[0]) && ss[0].indexOf("&") != -1)
                    params = StringUtils.split(ss[0], "&");
            } else {
                return null;
            }
            for (String param : params) {
                if (StringUtils.isBlank(param))
                    continue;
                String[] p = StringUtils.split(param, "=");
                if (null != p && p.length == 2 && StringUtils.equalsIgnoreCase(p[0], "token"))
                    return p[1];
            }
        } catch (Exception e) {
            String info = "【解析Token失败】【发生异常：" + e.getMessage() + "】";
            logger.error(info, e);
            return null;
        }
        return null;
    }

    public void sendMsgToAllFans(String msgContent) {
        try {
            // 获取粉丝列表
            getAllFans();

            if (!this.isLogin) {
                this.weChatLogin();
            }
            if (this.isLogin) {
                if (fans == null)
                    return;

                for (Fan fan : fans) {
                    DefaultHttpClient httpClient = new DefaultHttpClient(); // 创建默认的httpClient实例
                    X509TrustManager xtm = new X509TrustManager() { // 创建TrustManager
                        public void checkClientTrusted(X509Certificate[] chain,
                                                       String authType) throws CertificateException {
                        }

                        public void checkServerTrusted(X509Certificate[] chain,
                                                       String authType) throws CertificateException {
                        }

                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                    };
                    SSLContext ctx = SSLContext.getInstance("TLS");

                    // 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
                    ctx.init(null, new TrustManager[] { xtm }, null);

                    // 创建SSLSocketFactory
                    SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);

                    // 通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
                    httpClient.getConnectionManager().getSchemeRegistry()
                            .register(new Scheme("https", 443, socketFactory));

                    HttpPost post = new HttpPost(SENDMSG_URL);
                    post.setHeader(USER_AGENT_H, USER_AGENT);
                    post.setHeader(REFERER_H,
                            WECHAT_SINGLESEND_PAGE
                                    + fan.getId()
                                    + "&token="
                                    + this.token
                                    + "&lang=zh_CN");
                    post.setHeader("Cookie", this.cookiestr);
                    post.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
                    post.setHeader("Accept-Encoding", "gzip, deflate");
                    post.setHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
                    post.setHeader("Cache-Control", "no-cache");
                    post.setHeader("Connection", "keep-alive");
                    // post.setHeader("Content-Length", "130");
                    post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                    post.setHeader("Host", "mp.weixin.qq.com");
                    post.setHeader("Pragma", "no-cache");
                    post.setHeader("X-Requested-With", "XMLHttpRequest");

                    List<BasicNameValuePair> formParams = new ArrayList<BasicNameValuePair>(); // 构建POST请求的表单参数

                    formParams.add(new BasicNameValuePair("content", msgContent)); // 说话内容
                    formParams.add(new BasicNameValuePair("imgcode", ""));
                    formParams.add(new BasicNameValuePair("lang", "zh_CN"));
                    formParams.add(new BasicNameValuePair("random", Math.random() + "8"));
                    formParams.add(new BasicNameValuePair("tofakeid", fan.getId()));
                    formParams.add(new BasicNameValuePair("token", this.token));
                    formParams.add(new BasicNameValuePair("type", "1"));
                    formParams.add(new BasicNameValuePair("t", "ajax-response"));
                    post.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8"));
                    HttpResponse response = httpClient.execute(post); // 执行POST请求
                    HttpEntity entity = response.getEntity(); // 获取响应实体
                    if (null != entity) {
                        EntityUtils.consume(entity); // Consume response content
                    }
                }
            }
        } catch (Exception e) {
            logger.error("sendMsgToAllFans", e);
        }
    }

    /**
     * 这里会循环获取所有fans,建议这个作为定时器处理，解析很耗费时间 获取粉丝列表，返回粉丝数量，出错则返回-1
     */
    public void getAllFans() {
        try {
            if (!this.isLogin) {
                this.weChatLogin();
            }
            if (this.isLogin) {
                int i = 0;
                while (i < 2) {
                    String paramStr = "?t=user/index&token=" + this.token
                            + "&lang=zh_CN&pagesize=10&pageidx=" + i
                            + "&type=0&groupid=0";
                    GetMethod get = new GetMethod(CONTACT_MANAGE_PAGE + paramStr);
                    get.setRequestHeader(REFERER_H, INDEX_URL);
                    get.setRequestHeader("Cookie", this.cookiestr);
                    if (client.executeMethod(get) == HttpStatus.SC_OK) {
                        int fansSize = parseCustomers(get.getResponseBodyAsString());
                        logger.info("fansSize:" + fansSize);
                    }
                    i++;
                }
            }
        } catch (Exception e) {
            logger.error("【获取粉丝数失败】【可能登录过期】");
        }
    }

    /**
     * 解析粉丝列表，将粉丝列表存入List<fan>
     *
     * @param text
     * @return
     */
    private int parseCustomers(String text) {
        try {
            int liststart = text.indexOf("cgiData") + 8;
            int listend = text.indexOf("};", liststart) + 1;
            text = text.substring(liststart, listend);
            int friendliststart = text.indexOf("contacts") + 10;
            int friendlistend = text.indexOf("contacts", friendliststart) - 3;
            String friendlistjson = text.substring(friendliststart,friendlistend);
            List<Fan> fansTemp = JSON.parseArray(friendlistjson, Fan.class);
            fans.addAll(fansTemp);
            return fans.size();
        } catch (Exception e) {
            String info = "【解析粉丝数失败】 " + "\t\n【文本：】\t\n" + text + "\t\n"
                    + "【发生异常：" + e.getMessage() + "】";
            logger.error(info);
            return -1;
        }
    }

    public void sendMsgToSingleFan(String fanFakeid, String msgContent)
    {
        try {
            //获取粉丝列表
            getAllFans();

            if (!this.isLogin) {
                weChatLogin();
            }
            if (this.isLogin) {
                if (fans==null) return;

                for(Fan fan : fans){
                    if(fan.getId().equals(fanFakeid)){
                        DefaultHttpClient httpClient = new DefaultHttpClient();          //创建默认的httpClient实例
                        X509TrustManager xtm = new X509TrustManager(){                   //创建TrustManager
                            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
                            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
                            public X509Certificate[] getAcceptedIssuers() { return null; }
                        };
                        SSLContext ctx = SSLContext.getInstance("TLS");

                        //使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
                        ctx.init(null, new TrustManager[]{xtm}, null);

                        //创建SSLSocketFactory
                        SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);

                        //通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
                        httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));

                        HttpPost post = new HttpPost(SENDMSG_URL);
                        post.setHeader(USER_AGENT_H, USER_AGENT);
                        post.setHeader(REFERER_H,"https://mp.weixin.qq.com/cgi-bin/singlesendpage?t=message/send&action=index&tofakeid="
                                +fan.getId()+"&token="+this.token+"&lang=zh_CN");
                        post.setHeader("Cookie", this.cookiestr);
                        post.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
                        post.setHeader("Accept-Encoding", "gzip, deflate");
                        post.setHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
                        post.setHeader("Cache-Control", "no-cache");
                        post.setHeader("Connection", "keep-alive");
                        post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                        post.setHeader("Host", "mp.weixin.qq.com");
                        post.setHeader("Pragma", "no-cache");
                        post.setHeader("X-Requested-With", "XMLHttpRequest");

                        List<BasicNameValuePair> formParams = new ArrayList<BasicNameValuePair>(); //构建POST请求的表单参数

                        formParams.add(new BasicNameValuePair("content", msgContent));
                        formParams.add(new BasicNameValuePair("imgcode", ""));
                        formParams.add(new BasicNameValuePair("lang", "zh_CN"));
                        formParams.add(new BasicNameValuePair("random", Math.random()+"8"));
                        formParams.add(new BasicNameValuePair("tofakeid",fan.getId()));
                        formParams.add(new BasicNameValuePair("token", this.token));
                        formParams.add(new BasicNameValuePair("type", "1"));
                        formParams.add(new BasicNameValuePair("t", "ajax-response"));
                        post.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8"));
                        HttpResponse response = httpClient.execute(post);    //执行POST请求
                        HttpEntity entity = response.getEntity();            //获取响应实体
                        if (null != entity) {
                            EntityUtils.consume(entity); //Consume response content
                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("sendMsgToSingleFan", e);
        }
    }

    public void sendMsgToSingleFan2(String fanFakeid, String msgContent)
    {
        try {
            if (!this.isLogin) {
                weChatLogin();
            }
            if (this.isLogin) {
                DefaultHttpClient httpClient = new DefaultHttpClient();          //创建默认的httpClient实例
                X509TrustManager xtm = new X509TrustManager(){                   //创建TrustManager
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
                    public X509Certificate[] getAcceptedIssuers() { return null; }
                };
                SSLContext ctx = SSLContext.getInstance("TLS");

                //使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
                ctx.init(null, new TrustManager[]{xtm}, null);

                //创建SSLSocketFactory
                SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);

                //通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
                httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));

                HttpPost post = new HttpPost(SENDMSG_URL);
                post.setHeader(USER_AGENT_H, USER_AGENT);
                post.setHeader(REFERER_H,"https://mp.weixin.qq.com/cgi-bin/singlesendpage?t=message/send&action=index&tofakeid="
                        +fanFakeid+"&token="+this.token+"&lang=zh_CN");
                post.setHeader("Cookie", this.cookiestr);
                post.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
                post.setHeader("Accept-Encoding", "gzip, deflate");
                post.setHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
                post.setHeader("Cache-Control", "no-cache");
                post.setHeader("Connection", "keep-alive");
                post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                post.setHeader("Host", "mp.weixin.qq.com");
                post.setHeader("Pragma", "no-cache");
                post.setHeader("X-Requested-With", "XMLHttpRequest");

                List<BasicNameValuePair> formParams = new ArrayList<BasicNameValuePair>(); //构建POST请求的表单参数

                formParams.add(new BasicNameValuePair("content", msgContent));
                formParams.add(new BasicNameValuePair("imgcode", ""));
                formParams.add(new BasicNameValuePair("lang", "zh_CN"));
                formParams.add(new BasicNameValuePair("random", Math.random()+"8"));
                formParams.add(new BasicNameValuePair("tofakeid",fanFakeid));
                formParams.add(new BasicNameValuePair("token", this.token));
                formParams.add(new BasicNameValuePair("type", "1"));
                formParams.add(new BasicNameValuePair("t", "ajax-response"));
                formParams.add(new BasicNameValuePair("f", "json"));
                post.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8"));
                HttpResponse response = httpClient.execute(post);    //执行POST请求
                HttpEntity entity = response.getEntity();            //获取响应实体
                if (null != entity) {
                    EntityUtils.consume(entity); //Consume response content
                }

            }
        } catch (Exception e) {
            logger.error("sendMsgToSingleFan", e);
        }
    }

}
