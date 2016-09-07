package com.springBootD.wx.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录返回信息Json对象
 *
 * @author Kone
 */
public class LoginJson {
   private Map<String,String> base_resp = new HashMap<String,String>();
   private String redirect_url;
   
   
   
	public String getRedirect_url() {
        return redirect_url;
	}

	public void setRedirect_url(String redirect_url) {
		this.redirect_url = redirect_url;
	}

	public Map<String, String> getBase_resp() {
		return base_resp;
	}
	
	public void setBase_resp(Map<String, String> base_resp) {
		this.base_resp = base_resp;
	}
   
   

}
