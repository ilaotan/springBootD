package com.springBootD.wx.service;

/**
 * Created by tan on 2016/9/7.
 */
public interface WechatContents {

    public final static String WECHAT_PF_URL = "https://mp.weixin.qq.com/";
    public final static String WECHAT_SINGLESEND_PAGE = "https://mp.weixin.qq.com/cgi-bin/singlesendpage?t=message/send&action=index&tofakeid=";
    public final static String LOGIN_URL = "https://mp.weixin.qq.com/cgi-bin/login";
    public final static String HOST = "http://mp.weixin.qq.com";
    public final static String INDEX_URL = "http://mp.weixin.qq.com/cgi-bin/indexpage?t=wxm-index&lang=zh_CN";
    public final static String SENDMSG_URL ="https://mp.weixin.qq.com/cgi-bin/singlesend";
    public final static String FANS_URL = "http://mp.weixin.qq.com/cgi-bin/contactmanagepage?t=wxm-friend&lang=zh_CN&pagesize=10&pageidx=0&type=0&groupid=0";
    public final static String LOGOUT_URL = "http://mp.weixin.qq.com/cgi-bin/logout?t=wxm-logout&lang=zh_CN";
    public final static String DOWNLOAD_URL = "http://mp.weixin.qq.com/cgi-bin/downloadfile?";
    public final static String VERIFY_CODE = "http://mp.weixin.qq.com/cgi-bin/verifycode?";
    public final static String POST_MSG = "https://mp.weixin.qq.com/cgi-bin/masssend?t=ajax-response";
    public final static String VIEW_HEAD_IMG = "http://mp.weixin.qq.com/cgi-bin/viewheadimg";
    public final static String GET_IMG_DATA = "http://mp.weixin.qq.com/cgi-bin/getimgdata";
    public final static String GET_REGIONS = "http://mp.weixin.qq.com/cgi-bin/getregions";
    public final static String GET_MESSAGE = "http://mp.weixin.qq.com/cgi-bin/getmessage";
    public final static String OPER_ADVANCED_FUNC = "http://mp.weixin.qq.com/cgi-bin/operadvancedfunc";
    public final static String MASSSEND_PAGE = "http://mp.weixin.qq.com/cgi-bin/masssendpage";
    public final static String FILE_MANAGE_PAGE = "http://mp.weixin.qq.com/cgi-bin/filemanagepage";
    public final static String OPERATE_APPMSG = "https://mp.weixin.qq.com/cgi-bin/operate_appmsg?token=416919388&lang=zh_CN&sub=edit&t=wxm-appmsgs-edit-new&type=10&subtype=3&ismul=1";
    public final static String FMS_TRANSPORT = "http://mp.weixin.qq.com/cgi-bin/fmstransport";
    //public final static String CONTACT_MANAGE_PAGE = "http://mp.weixin.qq.com/cgi-bin/contactmanagepage";
    public final static String CONTACT_MANAGE_PAGE = "http://mp.weixin.qq.com/cgi-bin/contactmanage";
    public final static String OPER_SELF_MENU = "http://mp.weixin.qq.com/cgi-bin/operselfmenu";
    public final static String REPLY_RULE_PAGE = "http://mp.weixin.qq.com/cgi-bin/replyrulepage";
    public final static String SINGLE_MSG_PAGE = "http://mp.weixin.qq.com/cgi-bin/singlemsgpage";
    public final static String USER_INFO_PAGE = "http://mp.weixin.qq.com/cgi-bin/userinfopage";
    public final static String DEV_APPLY = "http://mp.weixin.qq.com/cgi-bin/devapply";
    public final static String UPLOAD_MATERIAL = "https://mp.weixin.qq.com/cgi-bin/uploadmaterial?cgi=uploadmaterial&type=2&token=416919388&t=iframe-uploadfile&lang=zh_CN&formId=1";
    public final static  String CODE_URI = "http://open.weixin.qq.com/connect/oauth2/authorize";
    public final static  String TOKEN_URI = "https://api.weixin.qq.com/sns/oauth2/access_token";
    public final static String REFRESH_TOKEN_URI = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
    public final static String USER_INFO_URI = "https://api.weixin.qq.com/sns/userinfo";
    public static final String CREATE_GROUP_URL = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=";
    public static final String GET_ALL_GROUP_URL = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token=";
    public static final String GET_USER_GROUP_URL = "https://api.weixin.qq.com/cgi-bin/groups/getid?access_token=";
    public static final String MODIFY_GROUP_URL = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token=";
    public static final String MODIFY_USER_GROUP_URL = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=";
    public static final String GET_FOLLOWERS_LIST_URL = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=";


    public final static String USER_AGENT_H = "User-Agent";
    public final static String REFERER_H = "Referer";
    public final static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.172 Safari/537.22";
    public final static String UTF_8 = "UTF-8";

}
