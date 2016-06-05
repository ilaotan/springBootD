package com.springBootD.framework.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 *  tanliansheng
 *  简单封装fastjson的方法
 *
 */
public class JsonUtils {
	   public static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

	/**
	 * 把JSON文本parse为JSONObject或者JSONArray
	 * @param text
	 * @return
	 */
	public static Object parse(String text){
		return JSON.parse(text);
	}

	/**
	 *  把JSON文本parse为JavaBean
	 * @param jsonString
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> T parseObject(String jsonString,Class<T> clazz){
		return JSON.parseObject(jsonString,clazz);
	}
	public static JSONObject parseObject(String text){
		return JSON.parseObject(text);
	}


	/**
	 *  把JSON文本parse成JavaBean集合
	 * @param text
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> parseArray(String text, Class<T> clazz){
		return JSON.parseArray(text,clazz);
	}
	public static JSONArray parseArray(String text){
		return JSON.parseArray(text);
	}

	/**
	 *  将JavaBean序列化为JSON文本
	 * @param object
	 * @return
	 */
	public static String toJSONString(Object object){
		return JSON.toJSONString(object);
	}

	/**
	 *  将JavaBean序列化为带格式的JSON文本
	 * @param object
	 * @param prettyFormat
	 * @return
	 */
	public static String toJSONString(Object object, boolean prettyFormat){
		return JSON.toJSONString(object,prettyFormat);
	}

	/**
	 * 将JavaBean转换为JSONObject或者JSONArray。
	 * @param javaObject
	 * @return
	 */
	public static  Object toJSON(Object javaObject){

		return JSON.toJSON(javaObject);
	}

}
