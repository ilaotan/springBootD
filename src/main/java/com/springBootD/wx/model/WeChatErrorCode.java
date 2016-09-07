package com.springBootD.wx.model;

import java.util.HashMap;
import java.util.Map;

public class WeChatErrorCode{
    protected static final Map<Integer, String> LOGIN_ERROR_MAP =  new HashMap<Integer, String>();
	
	static{
		LOGIN_ERROR_MAP.put(-1, "系统错误");
		LOGIN_ERROR_MAP.put(-2, "帐号或密码错误");
		LOGIN_ERROR_MAP.put(-3, "密码错误");
		LOGIN_ERROR_MAP.put(-4, "不存在该帐户");
		LOGIN_ERROR_MAP.put(-5, "访问受限");
		LOGIN_ERROR_MAP.put(-6, "需要输入验证码");
		LOGIN_ERROR_MAP.put(-7, "此帐号已绑定私人微信号，不可用于公众平台登录");
		LOGIN_ERROR_MAP.put(-8, "邮箱已存在");
		LOGIN_ERROR_MAP.put(-32, "验证码输入错误");
		LOGIN_ERROR_MAP.put(-200, "因频繁提交虚假资料，该帐号被拒绝登录");
		LOGIN_ERROR_MAP.put(-94, "请使用邮箱登陆");
		LOGIN_ERROR_MAP.put(-10, "该公众会议号已经过期，无法再登录使用");
		LOGIN_ERROR_MAP.put(65201, "成功登陆，正在跳转...");
		LOGIN_ERROR_MAP.put(65202, "成功登陆，正在跳转...");
		LOGIN_ERROR_MAP.put(0, "成功登陆，正在跳转...");
	}

	public static Map<Integer, String> getItems() {
		return LOGIN_ERROR_MAP;
	}

	public static String getValue(Integer key) {
		return LOGIN_ERROR_MAP.get(key);
	}

}